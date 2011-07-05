package me.timothyclare.tawny.model;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.AbstractListModel;

@Component
@Scope("desktop")
public class ProfileListModel extends AbstractListModel implements BindingListModel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1374112543675548299L;
	
	private transient final List<Profile> profiles;
	
	@Autowired
	public ProfileListModel(ProfileService profileService) {
		profiles = new ArrayList<Profile>();
		profiles.addAll(profileService.getAll());
	}
	
	@Override
	public Object getElementAt(int index) {
		return profiles.get(index);
	}

	@Override
	public int getSize() {
		return profiles.size();
	}

	@Override
	public int indexOf(Object obj) {
		return profiles.indexOf(obj);
	}

}
