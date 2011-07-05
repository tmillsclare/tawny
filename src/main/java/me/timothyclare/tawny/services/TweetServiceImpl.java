package me.timothyclare.tawny.services;

import java.util.ArrayList;
import java.util.List;

import me.timothyclare.tawny.TawnyApp;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.dao.api.TweetDao;
import me.timothyclare.tawny.event.TweetEvent;
import me.timothyclare.tawny.manager.api.ProfileManager;
import me.timothyclare.tawny.schedule.TweetTimeManager;
import me.timothyclare.tawny.services.api.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.zkoss.calendar.event.CalendarDataEvent;

@Service
public class TweetServiceImpl implements TweetService {

	private final List<Tweet> tweets = new ArrayList<Tweet>();

	private TweetDao tweetDao;
	private TweetTimeManager tweetTimeManager;
	private ProfileManager profileManager;
	
	@Autowired
	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}
	
	@Autowired
	public void setTweetTimeManager(TweetTimeManager tweetTimeManager) {
		this.tweetTimeManager = tweetTimeManager;
	}
	
	@Autowired
	public void setProfileManager(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}
	
	public List<Tweet> all() {
		return tweetDao.getAll(profileManager.getSessionProfile());
	}

	public void add(Tweet tweet) {
		tweetDao.add(tweet);
		tweets.add(tweet);
		tweetTimeManager.scheduleTweet(tweet);
		
		TawnyApp.getTawnyApp().getTweetEventQueue().publish(new TweetEvent(tweet, CalendarDataEvent.INTERVAL_ADDED));
	}

	public void update(Tweet tweet) {
		
		boolean publish = tweet.isTweeted();
		
		if(!tweet.isTweeted()) {
			publish = tweetTimeManager.cancelTweet(tweet);
			
			if(publish) {
				tweetDao.update(tweet);
				tweetTimeManager.scheduleTweet(tweet);
			}
		}
		
		if(publish) {
			TawnyApp.getTawnyApp().getTweetEventQueue().publish(new TweetEvent(tweet, CalendarDataEvent.CONTENTS_CHANGED));
		}
	}

	public void remove(Tweet tweet) {
		boolean result = tweetTimeManager.cancelTweet(tweet);
		
		if(result) {
			tweetDao.remove(tweet);
			tweets.remove(tweet);
			TawnyApp.getTawnyApp().getTweetEventQueue().publish(new TweetEvent(tweet, CalendarDataEvent.INTERVAL_REMOVED));
		}
	}
}
