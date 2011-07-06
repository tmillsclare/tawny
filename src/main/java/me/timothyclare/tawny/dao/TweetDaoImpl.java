package me.timothyclare.tawny.dao;

import java.util.List;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.dao.api.TweetDao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TweetDaoImpl implements TweetDao {
	
	
	SessionFactory sessionFactory;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	@Transactional
	public List<Tweet> getAll(Profile profile) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				Messages.getString("TweetDaoImpl.0"));
		
		query.setParameter(Messages.getString("TweetDaoImpl.1"), profile);

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
