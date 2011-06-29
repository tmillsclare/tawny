package me.timothyclare.tawny.schedule;

import java.util.TimerTask;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.hibernate.TweetDao;
import me.timothyclare.tawny.twitter.TwitterUtil;
import twitter4j.Twitter;
import twitter4j.TwitterException;

public class TweetTask extends TimerTask {

	Tweet tweet;

	public TweetTask(Tweet tweet) {
		this.tweet = tweet;
	}
	
	private TweetDao tweetDao;
	
	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}

	@Override
	public void run() {
		Twitter twitter = TwitterUtil.INSTANCE.getTwitter();

		if (twitter == null) {
			throw new RuntimeException("Twitter does not exist");
		}

		try {
			twitter.updateStatus(tweet.getContent());
			tweet.setTweeted(true);
			tweetDao.update(tweet);
		} catch (TwitterException e) {
			tweet.setTweeted(false);
		}
	}

}