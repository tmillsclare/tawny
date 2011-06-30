package me.timothyclare.tawny;

import org.zkoss.zk.ui.Session;
import org.zkoss.zk.ui.util.SessionCleanup;
import org.zkoss.zk.ui.util.SessionInit;

public class TwitterService implements SessionInit, SessionCleanup{
	
	public static final String TWITTERSESSION = Messages.getString("TwitterService.InstanceName");
	public static final String CONSUMERKEY = Messages.getString("TwitterService.ConsumerKey");
	public static final String CONSUMERSECRET = Messages.getString("TwitterService.ConsumerSecret");
	
	public void cleanup(Session sess) throws Exception {
		
	}

	public void init(Session sess, Object request) throws Exception {
		
	}	
}
