package me.timothyclare.tawny.bean;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

public class Profile {
	
	private String name;
	private Twitter twitter;
	private AccessToken token;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Twitter getTwitter() {
		return twitter;
	}
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}
	public AccessToken getToken() {
		return token;
	}
	public void setToken(AccessToken token) {
		this.token = token;
	}
	
	
}
