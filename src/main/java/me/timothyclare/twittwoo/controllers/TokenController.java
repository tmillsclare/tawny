package me.timothyclare.twittwoo.controllers;

import me.timothyclare.twittwoo.TwitterService;
import me.timothyclare.twittwoo.exceptions.token.TokenNotAccessible;
import me.timothyclare.twittwoo.hibernate.TokenDAO;
import me.timothyclare.twittwoo.twitter.TwitterUtil;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

public class TokenController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8956417168524636340L;

	A authlink;
	Textbox token;
	Button submit;

	Twitter twitter;
	RequestToken requestToken;

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		twitter = TwitterUtil.getInstance().getTwitter();

		if (twitter == null)
			throw new RuntimeException(
					"No twitter factory defined, please restart the application");

		
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch(TwitterException te) {
			//this didn't work let's rebuild and try again
			twitter = TwitterUtil.getInstance().buildTwitter(TwitterService.CONSUMERKEY, TwitterService.CONSUMERSECRET);
			requestToken = twitter.getOAuthRequestToken();
		}
		
		authlink.setHref(requestToken.getAuthorizationURL());
		authlink.setLabel(requestToken.getAuthorizationURL());
		authlink.setTarget("new");
	}

	public void onClick$submit(Event e) {
		AccessToken accessToken = null;

		try {
			if (token.getText().length() > 0) {
				accessToken = twitter.getOAuthAccessToken(requestToken, token.getText());
			} else {
				accessToken = twitter.getOAuthAccessToken();
			}
			
			try {
				TokenDAO.setToken(accessToken);
			} catch (TokenNotAccessible e1) {
				e1.printStackTrace();
			}
			
			Executions.sendRedirect("index.zul");
			
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println("Unable to get the access token.");
			} else {
				te.printStackTrace();
			}
		}
	}
}
