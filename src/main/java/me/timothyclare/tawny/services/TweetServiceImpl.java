package me.timothyclare.tawny.services;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.dao.api.TweetDao;
import me.timothyclare.tawny.event.TweetEvent;
import me.timothyclare.tawny.model.EventQueueHelper;
import me.timothyclare.tawny.schedule.TweetTimeManager;
import me.timothyclare.tawny.services.api.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TweetServiceImpl implements TweetService {

	private final List<Tweet> tweets = new ArrayList<Tweet>();

	private TweetDao tweetDao;
	private TweetTimeManager tweetTimeManager;
	
	@Autowired
	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}
	
	@Autowired
	public void setTweetTimeManager(TweetTimeManager tweetTimeManager) {
		this.tweetTimeManager = tweetTimeManager;
	}
	
	public List<Tweet> all() {
		return tweets;
	}

	public void add(Tweet tweet) {
		tweetDao.add(tweet);
		tweets.add(tweet);
		tweetTimeManager.scheduleTweet(tweet);

		EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.ADDED));
	}

	public void update(Tweet tweet) {
		tweetDao.update(tweet);
		boolean result = tweetTimeManager.cancelTweet(tweet);
		
		if(result) {
			tweetTimeManager.scheduleTweet(tweet);
			EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.UPDATED));
		}
	}

	public void remove(Tweet tweet) {
		boolean result = tweetTimeManager.cancelTweet(tweet);
		
		if(result) {
			tweetDao.remove(tweet);
			tweets.remove(tweet);
			EventQueueHelper.INSTANCE.getEventQueue().publish(new TweetEvent(tweet, TweetEvent.TweetEventType.REMOVED));
		}
	}
}
