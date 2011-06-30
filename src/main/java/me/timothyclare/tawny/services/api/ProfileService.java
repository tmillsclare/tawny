package me.timothyclare.tawny.services.api;

import me.timothyclare.tawny.bean.Profile;
import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

public interface ProfileService {
	public Profile getProfile(String profileName);
	public Profile generateProfile(String account, Twitter twitter, AccessToken accessToken);
	public boolean profileExists(String profileName);
	public Profile getCurrentProfile();
}
