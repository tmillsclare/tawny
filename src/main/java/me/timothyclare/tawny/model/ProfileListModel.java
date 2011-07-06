package me.timothyclare.tawny.model;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.TawnyApp;
import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.event.ProfileEvent;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zkplus.databind.BindingListModel;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.event.ListDataEvent;

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
		
		TawnyApp.getTawnyApp().getProfileEventQueue().subscribe(new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(event instanceof ProfileEvent)) {
					throw new IllegalArgumentException("Argument must be a TweetEvent");
				}
				
				ProfileEvent pe = (ProfileEvent)event;
				if(pe.getEventType() == ListDataEvent.INTERVAL_ADDED) {
					profiles.add(pe.getProfile());
				} else {
					
					int index = profiles.indexOf(pe.getProfile());
					
					if(index > -1) {
						Profile p = profiles.get(index);
						p.setToken(pe.getProfile().getToken());
					} else {
						profiles.add(pe.getProfile());
					}
				}
			}
		});
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
