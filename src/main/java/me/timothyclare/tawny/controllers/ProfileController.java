package me.timothyclare.tawny.controllers;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.manager.api.ProfileManager;
import me.timothyclare.tawny.model.ProfileListModel;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

@org.springframework.stereotype.Component
@Scope("prototype")
public class ProfileController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4878730217995190106L;
	
	Component tokenWindow;
	Textbox txtName;
	Window win;
	Listbox lstProfiles;
	
	private ProfileService profileService;
	private ProfileListModel profileListModel;
	private ProfileManager profileManager;
	private Profile selectedProfile;
	
	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		if(profileListModel.getSize() > 0) {
			selectedProfile = (Profile) profileListModel.getElementAt(0);
			lstProfiles.setSelectedIndex(0);
		}
	}
	
	@Autowired
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}

	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
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
	
	public void onClick$btnCreateProfile(Event event) {
		
		if(profileService.profileExists(txtName.getText())) {
			Clients.alert("profile already exists, please select a new name for it");
			return;
		}
		
		Map<String, String> arguments = new HashMap<String,String>();
		arguments.put("name", txtName.getText());
		tokenWindow = Executions.createComponents("macro/profileModal.zul", win, arguments);
		
		AnnotateDataBinder adb = new AnnotateDataBinder(tokenWindow);
	    adb.loadAll();
	    
	    tokenWindow.setVisible(true);
	}
	
	public void onClick$btnSelectProfile(Event event) {
		
		if(selectedProfile == null) {
			Clients.alert("You must select a profile before continuing");
			return;
		}
		
		profileManager.setSessionProfile(selectedProfile);
		selectedProfile.getTwitter().setOAuthAccessToken(selectedProfile.getToken());
		Executions.sendRedirect("calendar.zul");	
	}
}
