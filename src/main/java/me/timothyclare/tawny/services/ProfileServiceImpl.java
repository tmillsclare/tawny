package me.timothyclare.tawny.services;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.dao.api.ProfileDao;
import me.timothyclare.tawny.services.api.ProfileService;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

	private final List<Profile> profiles = new ArrayList<Profile>();

	private ProfileDao profileDao;
	
	@Autowired
	public ProfileServiceImpl(ProfileDao profileDao) {
		this.profileDao = profileDao;
		profiles.addAll(profileDao.getAll());
	}

	@Override
	public Profile get(String profileName) {
		Profile profile = profileDao.get(profileName);
		return profile;
	}
	
	@Override
	public List<Profile> getAll() {
		return profiles;
	}
	
	@Override
	public boolean update(Profile profile) {
		boolean ret = true;	
		try {
			profileDao.update(profile);
			
			int index = profiles.indexOf(profile);
			
			if(index > -1) {
				Profile p = profiles.get(index);
				p.setToken(profile.getToken());
			}
		} catch(HibernateException he) {
			ret = false;
		}
		
		return ret;
	}

	@Override
	public boolean save(Profile profile) {
		boolean ret = true;
		
		try {
			profileDao.save(profile);
			ret = profiles.add(profile);
		} catch(HibernateException he) {
			ret = false;
		}
		
		return ret;
	}

	@Override
	public boolean profileExists(String profileName) {
		Profile profileHolder = get(profileName);
		return profileHolder != null;
	}

	@Override
	public int count() {
		return profiles.size();
	}

	@Override
	public Profile getElementAt(int index) {
		return profiles.get(index);
	}

	@Override
	public int indexOf(Profile profile) {
		return profiles.indexOf(profile);
	}	
}
