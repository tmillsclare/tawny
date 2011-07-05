package me.timothyclare.tawny.services;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.dao.api.ProfileDao;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

	private final List<Profile> profiles = new ArrayList<Profile>();

	private ProfileDao profileDao;

	@Autowired
	public void setTokenDao(ProfileDao profileDao) {
		this.profileDao = profileDao;
	}

	@Override
	public Profile get(String profileName) {
			
		Profile profile = profileDao.get(profileName);
		profiles.add(profile);

		return profile;
	}
	
	@Override
	public List<Profile> getAll() {
		List<Profile> profiles = profileDao.getAll();
		return profiles;
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
	public boolean profileExists(String profileName) {
		Profile profileHolder = get(profileName);
		return profileHolder != null;
	}	
}
