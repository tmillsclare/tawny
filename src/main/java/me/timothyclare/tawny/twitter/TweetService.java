package me.timothyclare.tawny.twitter;

import java.util.List;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.TweetContext;

public interface TweetService {
	List<Tweet> all();
	
	void add(TweetContext tweet);
	void update(TweetContext tweet);
	void remove(TweetContext tweet);
	TweetContext getTweetContext(Tweet tweet);
	
	void refresh();
}
