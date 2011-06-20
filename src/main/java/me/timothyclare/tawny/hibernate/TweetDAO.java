package me.timothyclare.tawny.hibernate;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import me.timothyclare.tawny.bean.Tweet;

public class TweetDAO {

	public static List<Tweet> findAll() {
		Session session = TwitterHibernateUtil.openSession();
		Query query = session.createQuery("from Tweet");
		@SuppressWarnings("unchecked")
		List<Tweet> tweets = query.list();

		session.close();
		return tweets;
	}

	public static void add(Tweet tweet) {
		Session session = TwitterHibernateUtil.openSession();
		Transaction t = session.beginTransaction();

		session.persist(tweet);
		t.commit();

		session.close();
	}
	
	public static void update(Tweet tweet) {
		Session session = TwitterHibernateUtil.openSession();
		Transaction t = session.beginTransaction();
		
		session.update(tweet);
		t.commit();
		
		session.close();
	}

}
