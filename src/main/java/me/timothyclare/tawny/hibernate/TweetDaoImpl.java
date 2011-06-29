package me.timothyclare.tawny.hibernate;

import java.util.List;

import me.timothyclare.tawny.bean.Tweet;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

public class TweetDaoImpl implements TweetDao {
	
	@Autowired
	SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public List<Tweet> findAll() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from Tweet");

		@SuppressWarnings("unchecked")
		List<Tweet> tweets = query.list();

		return tweets;
	}
	
	@Transactional
	public void add(Tweet tweet) {
		sessionFactory.getCurrentSession().persist(tweet);
	}

	@Transactional
	public void update(Tweet tweet) {
		sessionFactory.getCurrentSession().update(tweet);
	}

	@Transactional
	public void remove(Tweet tweet) {
		sessionFactory.getCurrentSession().delete(tweet);
	}

}
