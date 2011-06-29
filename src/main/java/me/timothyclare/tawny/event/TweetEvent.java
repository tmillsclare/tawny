package me.timothyclare.tawny.event;

import me.timothyclare.tawny.bean.Tweet;

import org.zkoss.zk.ui.event.Event;

public class TweetEvent extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7580601302707655766L;
	
	private static final String eventName = "TweetEvent";
	
	public enum TweetEventType { ADDED, REMOVED, UPDATED }
	
	private TweetEventType type;
	private Tweet tweet;

	public TweetEvent(Tweet tweet, TweetEventType eventType) {
		super(eventName);
		
		this.tweet = tweet;
		this.type = eventType;
	}

	public TweetEventType getType() {
		return type;
	}

	public void setType(TweetEventType type) {
		this.type = type;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

}
