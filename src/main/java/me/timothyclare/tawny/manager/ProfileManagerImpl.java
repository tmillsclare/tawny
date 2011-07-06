package me.timothyclare.tawny.manager;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.manager.api.ProfileManager;

import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;

@Component
public class ProfileManagerImpl implements ProfileManager {
	
	@Override
	public Profile getSessionProfile() {
		return (Profile) Executions.getCurrent().getSession().getAttribute(Messages.getString("ProfileManagerImpl.0"));
	}
	
	@Override
	public void setSessionProfile(Profile profile) {
		Executions.getCurrent().getSession().setAttribute(Messages.getString("ProfileManagerImpl.0"), profile);
	}

	@Override
	public boolean isAuthenticated() {
		
		Profile sessionProfile = getSessionProfile();
		
		if(sessionProfile == null) {return false;}
		if(sessionProfile.getToken() == null) {return false;}
		
		return sessionProfile.getTwitter().getAuthorization().isEnabled();
	}
}
