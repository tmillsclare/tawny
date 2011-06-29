package me.timothyclare.tawny.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import me.timothyclare.tawny.bean.Tweet;

public enum TweetTimeManager {
	
	INSTANCE;
	
	private Map<Tweet, TweetTask> scheduledTasks = new HashMap<Tweet, TweetTask>();
	private Timer _timer =  new Timer();
	
	public void scheduleTweet(Tweet tweet) {
		
		if(tweet == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		TweetTask tweetTask = new TweetTask(tweet);
		_timer.schedule(tweetTask, tweet.getBeginDate());
		scheduledTasks.put(tweet, tweetTask);
		
	}
	
	public boolean cancelTweet(Tweet tweet) {
		
		boolean result = false;
		
		if (tweet == null) {
			throw new NullPointerException("The argument tweetContext cannot be null");
		}
		
		TweetTask tt = scheduledTasks.remove(tweet);
		
		if(tt != null) {
			result = tt.cancel();
		}
		
		return result;
	}
}
