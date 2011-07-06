package me.timothyclare.tawny.controllers;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.manager.api.ProfileManager;
import me.timothyclare.tawny.model.ProfileListModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.Listbox;

@org.springframework.stereotype.Component
@Scope("prototype")
public class ProfileSelectController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3206646857107829401L;

	Listbox lstProfiles;
	
	private ProfileListModel profileListModel;
	private ProfileManager profileManager;
	private Profile selectedProfile;
	
	@Autowired
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}
	
	@Autowired
	public void setProfileListModel(ProfileListModel profileListModel) {
		this.profileListModel = profileListModel;
	}
	
	public BindingListModel getProfileListModel() {
		return this.profileListModel;
	}
	
	public Profile getSelectedProfile() {
		return selectedProfile;
	}
	
	public void setSelectedProfile(Profile selectedProfile) {
		this.selectedProfile = selectedProfile;
	}
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		if(profileListModel.getSize() > 0) {
			selectedProfile = (Profile) profileListModel.getElementAt(0);
			lstProfiles.setSelectedIndex(0);
		}
	}
	
	public void onClick$btnSelectProfile(Event event) {
		
		if(selectedProfile == null) {
			Clients.alert(Messages.getString("ProfileController.3"));
			return;
		}
		
		profileManager.setSessionProfile(selectedProfile);
		selectedProfile.getTwitter().setOAuthAccessToken(selectedProfile.getToken());
		Executions.sendRedirect(Messages.getString("ProfileController.4"));
	}
}
