package me.timothyclare.tawny.twitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.TweetContext;
import me.timothyclare.tawny.hibernate.TweetDao;

public class TweetServiceImpl implements TweetService {

	private final Map<Tweet, TweetContext> tweetMap = new HashMap<Tweet, TweetContext>();
	private final List<Tweet> tweets = new ArrayList<Tweet>();

	private TweetDao tweetDao;

	public void setTweetDao(TweetDao tweetDao) {
		this.tweetDao = tweetDao;
	}
	
	public List<Tweet> all() {
		return tweets;
	}

	public void add(TweetContext tweetContext) {
		tweetMap.put(tweetContext.getTweet(), tweetContext);
		tweetDao.add(tweetContext.getTweet());
		tweetContext.getCalendarModel().add(tweetContext.getTweet());
	}

	public void update(TweetContext tweetContext) {

		if (tweetContext == null) {
			throw new NullPointerException(
					"The argument tweetContext cannot be null");
		}

		Tweet tweet = tweetContext.getTweet();

		tweetDao.update(tweet);
		tweetContext.getCalendarModel().update(tweet);
	}

	public void remove(TweetContext tweetContext) {

		if (tweetContext == null) {
			throw new NullPointerException(
					"The argument tweetContext cannot be null");
		}

		Tweet tweet = tweetContext.getTweet();

		tweetDao.remove(tweet);
		tweetContext.getCalendarModel().remove(tweet);
	}
	
	public void refresh() {
		tweets.clear();
		tweets.addAll(tweetDao.findAll());
	}

	public TweetContext getTweetContext(Tweet tweet) {
		return tweetMap.get(tweet);
	}
}
