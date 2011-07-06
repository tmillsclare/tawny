package me.timothyclare.tawny.controllers;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.A;
import org.zkoss.zul.Button;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import org.zkoss.zul.Window;

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
	Profile myProfile = new Profile();
	
	RequestToken requestToken;
	
	private ProfileService profileService;
	
	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		

		if (myProfile.getTwitter() == null)
			throw new RuntimeException(
					Messages.getString("TokenController.0"));

		
		try {
			requestToken = myProfile.getTwitter().getOAuthRequestToken();
		} catch(TwitterException te) {
			//this didn't work let's rebuild and try again
			throw new RuntimeException(Messages.getString("TokenController.1"));
		}
		
		authlink.setHref(requestToken.getAuthorizationURL());
		authlink.setLabel(requestToken.getAuthorizationURL());
		authlink.setTarget(Messages.getString("TokenController.2"));
	}

	public void onClick$submit(Event e) {
		AccessToken accessToken = null;
		
		if(token.getText().length() == 0) {
			Clients.alert(Messages.getString("TokenController.3"));
			return;
		}
		
		try {
			accessToken = myProfile.getTwitter().getOAuthAccessToken(requestToken, token.getText());
			
			Object obj = this.arg.get("name");
			myProfile.setName((String)obj);
			myProfile.getTwitter().setOAuthAccessToken(accessToken);
			myProfile.setToken(accessToken);
			
			profileService.save(myProfile);		
			profile.detach();
			
		} catch (TwitterException te) {
			if (401 == te.getStatusCode()) {
				System.out.println(Messages.getString("TokenController.5"));
			} else {
				te.printStackTrace();
			}
		}
	}
}
