package me.timothyclare.tawny.schedule;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.services.api.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetTimeManager {
		
	private Map<Tweet, TweetTask> scheduledTasks = new HashMap<Tweet, TweetTask>();
	private Timer _timer =  new Timer();
	
	private TweetService tweetService;	
	
	@Autowired
	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	public void scheduleTweet(Tweet tweet) {
		
		if(tweet == null) {
			throw new NullPointerException(Messages.getString("TweetTimeManager.0"));
		}
		
		TweetTask tweetTask = new TweetTask();
		tweetTask.setTweet(tweet);
		tweetTask.setTweetService(tweetService);
		
		_timer.schedule(tweetTask, tweet.getBeginDate());
		scheduledTasks.put(tweet, tweetTask);
		
	}
	
	public boolean cancelTweet(Tweet tweet) {
		
		boolean result = false;
		
		if (tweet == null) {
			throw new NullPointerException(Messages.getString("TweetTimeManager.1"));
		}
		
		TweetTask tt = scheduledTasks.remove(tweet);
		
		if(tt != null) {
			result = tt.cancel();
		}
		
		return result;
	}
}
