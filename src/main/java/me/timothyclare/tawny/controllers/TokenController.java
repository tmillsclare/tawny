package me.timothyclare.tawny.controllers;

import me.timothyclare.tawny.dao.api.TokenDao;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.spring.SpringUtil;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Textbox;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

@org.springframework.stereotype.Component
@Scope("prototype")
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
		
		twitter = TwitterUtil.INSTANCE.buildTwitter();

		if (twitter == null)
			throw new RuntimeException(
					"No twitter factory defined, please restart the application");

		
		try {
			requestToken = twitter.getOAuthRequestToken();
		} catch(TwitterException te) {
			//this didn't work let's rebuild and try again
			throw new RuntimeException("Cannot create twitter instance");
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
			
			
			TokenDao tokenDao = SpringUtil.getApplicationContext().getBean(TokenDao.class);
			if(!tokenDao.save("official", accessToken)) {
				throw new RuntimeException("Couldn't persiste the token!");
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
