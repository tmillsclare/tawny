package me.timothyclare.tawny;

import me.timothyclare.tawny.dao.TokenDAO;
import me.timothyclare.tawny.exceptions.token.TokenException;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;

import twitter4j.Twitter;
import twitter4j.auth.AccessToken;

public class TwitterService implements SessionInit, SessionCleanup{
	
	public static final String TWITTERSESSION = Messages.getString("TwitterService.InstanceName");
	public static final String CONSUMERKEY = Messages.getString("TwitterService.ConsumerKey");
	public static final String CONSUMERSECRET = Messages.getString("TwitterService.ConsumerSecret");
	
	public void cleanup(Session sess) throws Exception {
		
	}

	public void init(Session sess, Object request) throws Exception {
		Twitter twitter = null;
		
		AccessToken token = null;
		
		try {
			token = TokenDAO.getToken();
			twitter = TwitterUtil.INSTANCE.buildTwitter(CONSUMERKEY, CONSUMERSECRET, token);
		} catch (TokenException e) {
			//authorization failed, let's create an intermediate instance without
			//authorization
			if(twitter == null) {
				twitter = TwitterUtil.INSTANCE.buildTwitter(CONSUMERKEY, CONSUMERSECRET);
			}
		}
		
		TwitterUtil.INSTANCE.setTwitter(twitter);
	}	
}
