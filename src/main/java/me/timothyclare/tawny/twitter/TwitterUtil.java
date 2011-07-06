package me.timothyclare.tawny.twitter;

import me.timothyclare.tawny.Messages;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;

public enum TwitterUtil {
	
	INSTANCE;
	
	public static final String TWITTERSESSION = Messages.getString(Messages.getString("TwitterUtil.0"));
	public static final String CONSUMERKEY = Messages.getString(Messages.getString("TwitterUtil.1"));
	public static final String CONSUMERSECRET = Messages.getString(Messages.getString("TwitterUtil.2"));
	
	public Twitter buildTwitter() throws RuntimeException {
		Twitter twitter = new TwitterFactory().getInstance();
		twitter.setOAuthConsumer(CONSUMERKEY, CONSUMERSECRET);
		return twitter;
	}
}
