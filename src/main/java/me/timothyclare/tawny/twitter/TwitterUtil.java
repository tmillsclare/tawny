package me.timothyclare.tawny.twitter;

import me.timothyclare.tawny.Messages;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public enum TwitterUtil {
	
	INSTANCE;
	
	public static final String TWITTERSESSION = Messages.getString("TwitterService.InstanceName");
	public static final String CONSUMERKEY = Messages.getString("TwitterService.ConsumerKey");
	public static final String CONSUMERSECRET = Messages.getString("TwitterService.ConsumerSecret");
	
	public Twitter buildTwitter() throws RuntimeException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET);
		return twitter;
	}
}
