package me.timothyclare.tawny.twitter;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.dao.api.TweetDao;
import me.timothyclare.tawny.event.TweetEvent;
import me.timothyclare.tawny.model.EventQueueHelper;
import me.timothyclare.tawny.schedule.TweetTimeManager;

public class TweetServiceImpl implements TweetService {

	private final List<Tweet> tweets = new ArrayList<Tweet>();

	private TweetDao tweetDao;
	
	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}
	
	
	public List<Tweet> all() {
		return tweets;
	}

	public void add(Tweet tweet) {
		tweetDao.add(tweet);
		tweets.add(tweet);
		TweetTimeManager.INSTANCE.scheduleTweet(tweet);

		EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.ADDED));
	}

	public void update(Tweet tweet) {
		tweetDao.update(tweet);
		boolean result = TweetTimeManager.INSTANCE.cancelTweet(tweet);
		
		if(result) {
			TweetTimeManager.INSTANCE.scheduleTweet(tweet);
			EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.UPDATED));
		}
	}

	public void remove(Tweet tweet) {
		boolean result = TweetTimeManager.INSTANCE.cancelTweet(tweet);
		
		if(result) {
			tweetDao.remove(tweet);
			tweets.remove(tweet);
			EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.REMOVED));
		}
	}
}
