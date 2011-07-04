package me.timothyclare.tawny.controllers;

import me.timothyclare.tawny.services.api.ProfileService;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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
	Label lblName;
	Window profile;

	Twitter twitter;
	RequestToken requestToken;
	
	private ProfileService profileService;
	
	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

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
			
			profileService.save(lblName.getValue(), twitter, accessToken);		
			profile.detach();
			
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println("Unable to get the access token.");
			} else {
				te.printStackTrace();
			}
		}
	}
}
