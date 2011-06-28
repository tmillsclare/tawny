package me.timothyclare.tawny.twitter;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.TweetContext;
import me.timothyclare.tawny.hibernate.TweetDAO;

public enum TweetManager {
	INSTANCE;
	
	private Map<Tweet, TweetContext> tweetMap = new HashMap<Tweet, TweetContext>();
	
	public void addTweet(TweetContext tweetContext) {
		tweetMap.put(tweetContext.getTweet(), tweetContext);
		TweetDAO.add(tweetContext.getTweet());
		tweetContext.getCalendarModel().add(tweetContext.getTweet());
	}
	
	public void updateTweet(TweetContext tweetContext) {
		
		if(tweetContext == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		Tweet tweet = tweetContext.getTweet();
		
		TweetDAO.update(tweet);
		tweetContext.getCalendarModel().update(tweet);
	}
	
	public void removeTweet(TweetContext tweetContext) {
		
		if(tweetContext == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		Tweet tweet = tweetContext.getTweet();
		
		TweetDAO.remove(tweet);
		tweetContext.getCalendarModel().remove(tweet);
	}
	
	public TweetContext getTweet(Tweet tweet) {
		return tweetMap.get(tweet);
	}
}
