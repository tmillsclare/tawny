package me.timothyclare.tawny.event;

import me.timothyclare.tawny.bean.Profile;

import org.zkoss.zk.ui.event.Event;

public class ProfileEvent extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8661931850704775324L;
	
	private static final String eventName = "ProfileEvent";
	
	private Profile profile;
	private int eventType; 
	
	public ProfileEvent(Profile profile, int eventType) {
		super(eventName);
		
		this.setProfile(profile);
		this.setEventType(eventType);
	}

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public int getEventType() {
		return eventType;
	}

	public void setEventType(int eventType) {
		this.eventType = eventType;
	}
}
