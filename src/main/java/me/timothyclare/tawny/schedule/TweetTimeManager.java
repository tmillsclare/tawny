package me.timothyclare.tawny.schedule;

import java.util.Date;
import java.util.Timer;

import me.timothyclare.tawny.bean.TweetContext;

public enum TweetTimeManager {
	INSTANCE;
	
	private Timer _timer =  new Timer();
	
	public void scheduleTweet(TweetContext tweetContext, Date time) {
		
		if(tweetContext == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		TweetTask tweetTask = new TweetTask(tweetContext.getTweet());
		_timer.schedule(tweetTask, time);
		tweetContext.setTweetTask(tweetTask);
		
	}
	
	public void cancelAll() {
		_timer.cancel();
	}
	
	public boolean cancelTweet(TweetContext tweetContext) {
		
		boolean result = false;
		
		if (tweetContext == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		result = tweetContext.getTweetTask().cancel();
		
		return result;
	}
}
