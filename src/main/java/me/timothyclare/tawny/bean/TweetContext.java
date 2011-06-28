package me.timothyclare.tawny.bean;

import me.timothyclare.tawny.schedule.TweetTask;

import org.zkoss.calendar.impl.SimpleCalendarModel;

public class TweetContext {
	
	private Tweet _tweet;
	private SimpleCalendarModel _calendarModel;
	private TweetTask _tweetTask;
	
	public TweetContext(Tweet tweet) {
		this(tweet, null, null);
	}
	
	public TweetContext(Tweet tweet, SimpleCalendarModel calendarModel) {
		this(tweet, calendarModel, null);
	}
	
	public TweetContext(Tweet tweet, SimpleCalendarModel calendarModel, TweetTask tweetTask) {
		_tweet = tweet;
		_calendarModel = calendarModel;
		_tweetTask = tweetTask;
	}
	
	public Tweet getTweet() {
		return _tweet;
	}
	
	public void setTweet(Tweet tweet) {
		this._tweet = tweet;
	}
	
	public SimpleCalendarModel getCalendarModel() {
		return _calendarModel;
	}
	
	public void setCalendarModel(SimpleCalendarModel calendarModel) {
		this._calendarModel = calendarModel;
	}
	
	public TweetTask getTweetTask() {
		return _tweetTask;
	}
	
	public void setTweetTask(TweetTask tweetTask) {
		_tweetTask = tweetTask;
	}
	
	
}
