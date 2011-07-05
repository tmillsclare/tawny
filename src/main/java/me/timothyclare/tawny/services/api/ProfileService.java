package me.timothyclare.tawny.services.api;

import java.util.List;

import me.timothyclare.tawny.bean.Profile;

public interface ProfileService {
	public Profile get(String profileName);
	public List<Profile> getAll();
	public void update(Profile profile);
	public void save(Profile profile);
	
	public boolean profileExists(String profileName);
}
