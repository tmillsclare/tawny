package me.timothyclare.tawny.event;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Tweet;

import org.zkoss.zk.ui.event.Event;

public class TweetEvent extends Event {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7580601302707655766L;
	
	private static final String eventName = Messages.getString("TweetEvent.0");
	
	private int type;
	private Tweet tweet;

	public TweetEvent(Tweet tweet, int eventType) {
		super(eventName);
		
		this.tweet = tweet;
		this.type = eventType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Tweet getTweet() {
		return tweet;
	}

	public void setTweet(Tweet tweet) {
		this.tweet = tweet;
	}

}
