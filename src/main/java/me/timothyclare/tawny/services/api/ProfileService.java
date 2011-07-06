package me.timothyclare.tawny.services.api;

import java.util.List;

import me.timothyclare.tawny.bean.Profile;

public interface ProfileService {
	Profile get(String profileName);
	List<Profile> getAll();
	boolean update(Profile profile);
	boolean save(Profile profile);
	
	int count();
	Profile getElementAt(int index);
	int indexOf(Profile profile);
		
	boolean profileExists(String profileName);
}
