package me.timothyclare.tawny.controllers;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
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
	
	private ProfileService profileService;
	
	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
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
	}
	
	public void onClick$btnSelectProfile(Event event) {
		
	}
}
