package me.timothyclare.tawny.model;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

@Component
public class ProfileListModel extends AbstractListModel implements BindingListModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1374112543675548299L;
	
	private ProfileService profileService;
	
	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	public void add(Profile profile) {
		int size = profileService.count();
		
		if(profileService.save(profile)) {
			fireEvent(ListDataEvent.INTERVAL_ADDED, size, size);
		}
	}
	
	public void update(Profile profile) {
		profileService.update(profile);
	}

	@Override
	public Object getElementAt(int index) {
		return profileService.getElementAt(index);
	}

	@Override
	public int getSize() {
		return profileService.count();
	}

	@Override
	public int indexOf(Object obj) {
		
		if(!(obj instanceof Profile)) {
			throw new IllegalArgumentException("Argument must be a profile");
		}
		
		return profileService.indexOf((Profile)obj);
	}
	
}
