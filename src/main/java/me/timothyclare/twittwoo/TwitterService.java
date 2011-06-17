package org.zkoss.zktwitterservice;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;
import org.zkoss.zktwitterservice.exceptions.token.TokenException;
import org.zkoss.zktwitterservice.hibernate.TokenDAO;
import org.zkoss.zktwitterservice.hibernate.TwitterHibernateUtil;
import org.zkoss.zktwitterservice.twitter.TweetManager;
import org.zkoss.zktwitterservice.twitter.TwitterUtil;

import twitter4j.Twitter;
import twitter4j.http.AccessToken;

public class TwitterService implements SessionInit, SessionCleanup{
	
	public static final String TWITTERSESSION = Messages.getString("TwitterService.InstanceName");
	public static final String CONSUMERKEY = Messages.getString("TwitterService.ConsumerKey");
	public static final String CONSUMERSECRET = Messages.getString("TwitterService.ConsumerSecret");
	
	public void cleanup(Session sess) throws Exception {
		
		TwitterHibernateUtil.getSessionFactory().close();
		TweetManager.getInstance().cancel();
	}

	public void init(Session sess, Object request) throws Exception {
		
		TwitterHibernateUtil.getSessionFactory();
		Twitter twitter = null;
		
		AccessToken token = null;
		
		try {
			token = TokenDAO.getToken();
			twitter = TwitterUtil.getInstance().buildTwitter(CONSUMERKEY, CONSUMERSECRET, token);
		} catch (TokenException e) {
			//authorization failed, let's create an intermediate instance without
			//authorization
			if(twitter == null) {
				twitter = TwitterUtil.getInstance().buildTwitter(CONSUMERKEY, CONSUMERSECRET);
			}
		}
		
		TwitterUtil.getInstance().setTwitter(twitter);
	}	
}
