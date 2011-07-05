package me.timothyclare.tawny.manager.api;

import me.timothyclare.tawny.bean.Profile;

public interface ProfileManager {
	Profile getSessionProfile();
	void setSessionProfile(Profile profile);
	boolean isAuthenticated();
}
