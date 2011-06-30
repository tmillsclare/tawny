package me.timothyclare.tawny.schedule;

import java.util.TimerTask;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.services.api.TweetService;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends TimerTask {

	Tweet tweet;
	Profile profile;

	public TweetTask() {
		
	}
	
	public void setTweet(Tweet tweet) {
		if(tweet == null) {
			throw new NullPointerException("The argument tweet cannot be null");
		}
		
		this.tweet = tweet;
	}
	
	public void setProfile(Profile profile) {
		if(profile == null) {
			throw new NullPointerException("The argument profile cannot be null");
		}
		
		this.profile = profile;
	}
	
	private TweetService tweetService;
	
	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	

	@Override
	public void run() {
		Twitter twitter = profile.getTwitter();

		if (twitter == null) {
			throw new RuntimeException("Twitter does not exist");
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