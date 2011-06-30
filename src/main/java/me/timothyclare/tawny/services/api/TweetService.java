package me.timothyclare.tawny.services.api;

import java.util.List;

import me.timothyclare.tawny.bean.Tweet;

public interface TweetService {
	List<Tweet> all();
	
	void add(Tweet tweet);
	void update(Tweet tweet);
	void remove(Tweet tweet);
}
