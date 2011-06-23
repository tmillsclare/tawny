package me.timothyclare.tawny.twitter;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.hibernate.TweetDAO;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public enum TweetManager {
	INSTANCE;
	
	private Timer _timer =  new Timer();
	private Map<Tweet, TweetTask> _scheduledTasks = new HashMap<Tweet, TweetTask>();
	
	public void scheduleTweet(Tweet tweet, Date time) {
		if(tweet != null) {
			TweetTask tweetTask = new TweetTask(tweet);
			_timer.schedule(tweetTask, time);
			_scheduledTasks.put(tweet, tweetTask);
		}
	}
	
	public void cancelAll() {
		_timer.cancel();
	}
	
	public boolean cancelTweet(Tweet tweet) {
		
		boolean result = false;
		
		if (tweet == null) {
			throw new NullPointerException("The argument tweet cannot be null");
		}
		
		Object objTweet = _scheduledTasks.get(tweet);
		
		if(objTweet instanceof TweetTask) {
			TweetTask tweetTask = (TweetTask)objTweet;
			result = tweetTask.cancel();
		}
		
		if(result) {
			_scheduledTasks.remove(tweet);
		}
		
		return result;
	}
	
	public static class TweetTask extends TimerTask {
		
		Tweet tweet;
		
		public TweetTask(Tweet tweet) {
			this.tweet = tweet;
		}
		
		@Override
		public void run() {
			Twitter twitter = TwitterUtil.INSTANCE.getTwitter();
			
			if(twitter == null) {
				throw new RuntimeException("Twitter does not exist");
			}
			
			try {
				twitter.updateStatus(tweet.getContent());
				tweet.setTweeted(true);
				TweetDAO.update(tweet);
			} catch (TwitterException e) {
				tweet.setTweeted(false);
			}
		}
		
	}
}
