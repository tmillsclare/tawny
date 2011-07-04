package me.timothyclare.tawny.bean;

import javax.persistence.Id;
import javax.persistence.Transient;

import twitter4j.Twitter;

public class Profile {
	
	private String name;
	private Twitter twitter;
	private byte[] token;
	
	@Id
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public Twitter getTwitter() {
		return twitter;
	}
	
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}
	
	public byte[] getToken() {
		return token;
	}
	
	public void setToken(byte[] token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return name.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		
		if(obj == this) {
			return true;
		}
		
		if(!(obj instanceof Profile)) {
			return false;
		}
		
		Profile profile = (Profile)obj;
		return this.getName().equals(profile.getName());
	}
	
	
}
