package me.timothyclare.tawny.manager;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.manager.api.ProfileManager;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.Executions;

@Component
@Scope("prototype")
public class ProfileManagerImpl implements ProfileManager {
	
	@Override
	public Profile getSessionProfile() {
		return (Profile) Executions.getCurrent().getSession().getAttribute("sessionProfile");
	}
	
	@Override
	public void setSessionProfile(Profile profile) {
		Executions.getCurrent().getSession().setAttribute("sessionProfile", profile);
	}

	@Override
	public boolean isAuthenticated() {
		
		Profile sessionProfile = getSessionProfile();
		
		if(sessionProfile == null) {return false;}
		if(sessionProfile.getToken() == null) {return false;}
		
		return sessionProfile.getTwitter().getAuthorization().isEnabled();
	}
}
