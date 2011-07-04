package me.timothyclare.tawny.services;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.dao.api.ProfileDao;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

@Service
@Scope("desktop")
public class ProfileServiceImpl implements ProfileService {

	private final List<ProfileHolder> profiles = new ArrayList<ProfileHolder>();
	
	public static class ProfileHolder {
		private Profile profile;
		private AccessToken token;
		
		public ProfileHolder(Profile profile, AccessToken token) {
			this.profile = profile;
			this.token = token;
		}

		public Profile getProfile() {
			return profile;
		}

		public AccessToken getToken() {
			return token;
		}

		@Override
		public int hashCode() {
			return profile.hashCode();
		}

		@Override
		public boolean equals(Object obj) {
			
			if(obj == this) {return true;}
			if((obj instanceof ProfileHolder)) { return profile.equals(((ProfileHolder)obj).getProfile());}
			if((obj instanceof Profile)) { return profile.equals(obj);}
			
			return false;
		}
	}

	private ProfileDao profileDao;

	@Autowired
	public void setTokenDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	@Override
	public ProfileHolder get(String profileName) {
		
		Profile profile = profileDao.get(profileName);
		AccessToken accessToken = readAccessToken(profile.getToken());
		ProfileHolder holder = null;
		
		if (accessToken != null) {
			holder = new ProfileHolder(profile, accessToken);
			profiles.add(holder);
		}

		return holder;
	}
	
	@Override
	public List<ProfileHolder> getAll() {
		List<Profile> profiles = profileDao.getAll();
		List<ProfileHolder> holders = new ArrayList<ProfileHolder>();
		
		for(Profile profile : profiles) {
			AccessToken at = readAccessToken(profile.getToken());
			
			if(at != null) {
				holders.add(new ProfileHolder(profile, at));
			}
		}
		
		return holders;
	}
	
	@Override
	public void update(Profile profile) {
		profileDao.update(profile);
	}

	@Override
	public void save(Profile profile) {
		profileDao.save(profile);
	}
	
	@Override
	public void save(String account, Twitter twitter,
			AccessToken accessToken) {
		profileDao.save(generateProfile(account, twitter, accessToken));
	}

	@Override
	public boolean profileExists(String profileName) {
		ProfileHolder profileHolder = get(profileName);
		return profileHolder != null;
	}

	@Override
	public Profile generateProfile(String account, Twitter twitter,
			AccessToken accessToken) {
		twitter.setOAuthAccessToken(accessToken);
		
		byte[] buffer = writeAccessToken(accessToken);
		Profile profile = null;
		
		if(buffer != null) {
			profile = new Profile();
			profile.setName(account);
			profile.setTwitter(twitter);
			profile.setToken(buffer);
		}

		return profile;
	}

	/*@Override
	public boolean isAuthenticated(Profile profile) {
		boolean authenticated = false;
		
		if (getCurrentProfile() == null && getCurrentProfile().getTwitter().getAuthorization().isEnabled()) {
			authenticated = true;
		}
		
		return authenticated;
	}*/
	
	private AccessToken readAccessToken(byte[] accessToken) {
		try {
			ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(accessToken));
			AccessToken aToken = (AccessToken)in.readObject();
			return aToken;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	private byte[] writeAccessToken(AccessToken at) {
		
		byte[] buffer = null;
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutput out = null;
		
		try {
			out = new ObjectOutputStream(bos);
			out.writeObject(at);
			out.close();
			
			buffer = bos.toByteArray();
		} catch (IOException e) {
			//return false; TODO: log
		} finally {
			if(out != null) {
				try {
					out.close();
				} catch (IOException e) {
					//TODO: log
				}
			}
		}
		
		return buffer;
		
	}
}
