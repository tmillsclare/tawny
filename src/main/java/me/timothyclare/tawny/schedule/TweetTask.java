package me.timothyclare.tawny.schedule;

import java.util.TimerTask;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.services.api.TweetService;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends TimerTask {

	Tweet tweet;

	public TweetTask() {
		
	}
	
	public void setTweet(Tweet tweet) {
		if(tweet == null) {
			throw new NullPointerException(Messages.getString("TweetTask.0"));
		}
		
		this.tweet = tweet;
	}
	
	private TweetService tweetService;
	
	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	

	@Override
	public void run() {
		Twitter twitter = tweet.getProfile().getTwitter();

		if (twitter == null) {
			throw new RuntimeException(Messages.getString("TweetTask.1"));
		}

		try {
			twitter.updateStatus(tweet.getContent());
			tweet.setTweeted(true);
			tweetService.update(tweet);
		} catch (TwitterException e) {
			tweet.setTweeted(false);
		}
	}

}