package me.timothyclare.tawny.services.api;

import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.services.ProfileServiceImpl.ProfileHolder;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

public interface ProfileService {
	public ProfileHolder get(String profileName);
	public List<ProfileHolder> getAll();
	public void update(Profile profile);
	public void save(Profile profile);
	public void save(String name, Twitter twitter, AccessToken accessToken);
	
	public Profile generateProfile(String name, Twitter twitter, AccessToken accessToken);
	
	public boolean profileExists(String profileName);
}
