package me.timothyclare.tawny.twitter;

import me.timothyclare.tawny.Messages;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

public enum TwitterUtil {
	
	INSTANCE;
	
	public static final String TWITTERSESSION = Messages.getString("TwitterService.InstanceName");
	public static final String CONSUMERKEY = Messages.getString("TwitterService.ConsumerKey");
	public static final String CONSUMERSECRET = Messages.getString("TwitterService.ConsumerSecret");
	
	public Twitter buildTwitter(AccessToken token) throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET);
		twitter.setOAuthAccessToken(token);
		
		return twitter;
	}
	
	public Twitter buildTwitter() throws Exception {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET);
		return twitter;
	}
}
