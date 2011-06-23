package me.timothyclare.tawny.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public enum TwitterUtil {
	
	INSTANCE;
	
	private Twitter twitter;
	
	public Twitter buildTwitter(String consumerKey, String consumerSecret, AccessToken token) throws Exception {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		twitter.setOAuthAccessToken(token);
		
		return twitter;
	}
	
	public Twitter buildTwitter(String consumerKey, String consumerSecret) throws Exception {
		twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
		return twitter;
	}
	
	public Twitter getTwitter() {		
		return twitter;
	}
	
	public void setTwitter(Twitter twitter) {
		this.twitter = twitter;
	}
}
