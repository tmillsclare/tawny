package org.zkoss.zktwitterservice.twitter;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.http.AccessToken;

public class TwitterUtil {
	
	private static TwitterUtil twitterUtil=null;
	
	private TwitterUtil(){};
	
	public synchronized static TwitterUtil getInstance() {
		if(twitterUtil == null) {
			twitterUtil = new TwitterUtil();
		}
		
		return twitterUtil;
	}
	
	private Twitter twitter;
	
	public Twitter buildTwitter(String consumerKey, String consumerSecret, AccessToken token) throws Exception {
		return new TwitterFactory().getOAuthAuthorizedInstance(consumerKey, consumerSecret, token);
	}
	
	public Twitter buildTwitter(String consumerKey, String consumerSecret) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
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
