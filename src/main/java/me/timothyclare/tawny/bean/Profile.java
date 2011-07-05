package me.timothyclare.tawny.bean;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

import me.timothyclare.tawny.twitter.TwitterUtil;

import org.hibernate.annotations.Type;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

@Entity
public class Profile {
	
	private String name;
	private Twitter twitter;
	
	@Type(type="me.timothyclare.tawny.dao.types.TwitterAccessTokenType")
	private AccessToken token;
	
	@Id
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	@Transient
	public Twitter getTwitter() {
		if(twitter == null) {
			twitter = TwitterUtil.INSTANCE.buildTwitter();
		}
		
		return twitter;
	}
	
	public AccessToken getToken() {
		return token;
	}
	
	public void setToken(AccessToken token) {
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
		
		boolean result = false;
		
		if((obj instanceof Profile)) {
			Profile profile = (Profile)obj;
			result = this.getName().equals(profile.getName());
		}
		
		if(obj instanceof String) {
			result = this.getName().equals(obj);
		}
		
		return result;
	}
	
	
}
