package me.timothyclare.tawny.twitter;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.hibernate.TweetDAO;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetManager {
	private TweetManager() {}
	
	private static TweetManager tweetManager;
	
	public synchronized static TweetManager getInstance() {
		if(tweetManager == null) {
			tweetManager = new TweetManager();
		}
		
		return tweetManager;
	}
	
	private Timer timer =  new Timer();
	
	public void scheduleTweet(Tweet tweet, Date time) {
		if(tweet != null) {
			timer.schedule(new TweetTask(tweet), time);
		}
	}
	
	public void cancel() {
		timer.cancel();
	}
	
	class TweetTask extends TimerTask {
		
		Tweet tweet;
		
		public TweetTask(Tweet tweet) {
			this.tweet = tweet;
		}
		
		@Override
		public void run() {
			Twitter twitter = TwitterUtil.getInstance().getTwitter();
			
			if(twitter == null) {
				throw new RuntimeException("Twitter does not exist");
			}
			
			try {
				Status status = twitter.updateStatus(tweet.getContent());
				System.out.println(status);
				tweet.setTweeted(true);
				TweetDAO.update(tweet);
			} catch (TwitterException e) {
				e.printStackTrace();
				tweet.setTweeted(false);
			}
		}
		
	}
}
