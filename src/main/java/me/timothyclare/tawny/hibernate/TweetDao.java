
package me.timothyclare.tawny.hibernate;

import java.util.List;

import me.timothyclare.tawny.bean.Tweet;

public interface TweetDao {
	List<Tweet> findAll();
	void add(Tweet tweet);
	void update(Tweet tweet);
	void remove(Tweet tweet);
}