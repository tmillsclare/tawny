
package me.timothyclare.tawny.dao.api;

import java.util.List;

import me.timothyclare.tawny.bean.Tweet;

public interface TweetDao {
	List<Tweet> findAll();
	void add(Tweet tweet);
	void update(Tweet tweet);
	void remove(Tweet tweet);
}