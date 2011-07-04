package me.timothyclare.tawny.services;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.dao.api.TokenDao;
import me.timothyclare.tawny.services.api.ProfileService;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

@Service
public class ProfileServiceImpl implements ProfileService {
	
	private Map<String, Profile> loadedProfiles = new HashMap<String,Profile>();
	
	private Profile currentProfile;
	private TokenDao tokenDao;
	
	@Autowired
	public void setTokenDao(TokenDao tokenDao) {
		this.tokenDao = tokenDao;
	}
		
	@Override
	public Profile getProfile(String profileName) {
		
		if(loadedProfiles.containsKey(profileName)) {
			return loadedProfiles.get(profileName);
		}
		
		AccessToken accessToken = tokenDao.get(profileName);
		Profile profile = null;
		
		if(accessToken != null) {
			profile = new Profile();
			profile.setToken(accessToken);
			
			Twitter twitter;
			try {
				twitter = TwitterUtil.INSTANCE.buildTwitter(accessToken);
			} catch (Exception e) {
				throw new RuntimeException("Twitter could not be built");
			}
			
			profile.setTwitter(twitter);
			profile.setName(profileName);
			
			currentProfile = profile;
			loadedProfiles.put(profileName, profile);
		}
		
		return profile;
	}

	@Override
	public boolean profileExists(String profileName) {
		if(loadedProfiles.containsKey(profileName)) {
			return true;
		}
		
		AccessToken accessToken = tokenDao.get(profileName);
		
		if(accessToken == null) return false;
		else {
			//let's cache it, if people are looking for it then
			//it will be needed
			
			try {
				generateProfile(profileName, TwitterUtil.INSTANCE.buildTwitter(), accessToken);
			} catch (Exception e) {
				throw new RuntimeException("Twitter messed up");
			}
			
			return true;
		}
	}

	@Override
	public Profile generateProfile(String account, Twitter twitter,
			AccessToken accessToken) {
		twitter.setOAuthAccessToken(accessToken);
		
		Profile profile = new Profile();
		profile.setName(account);
		profile.setTwitter(twitter);
		profile.setToken(accessToken);
		
		loadedProfiles.put(account, profile);
		currentProfile=profile;
		
		return profile;
	}

	@Override
	public Profile getCurrentProfile() {
		return currentProfile;
	}

}
