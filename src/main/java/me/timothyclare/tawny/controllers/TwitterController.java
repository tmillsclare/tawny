package me.timothyclare.tawny.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.sharer.GenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetAddGenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetUpdateGenericSharer;
import me.timothyclare.tawny.manager.api.ProfileManager;
import me.timothyclare.tawny.model.TweetModel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

@org.springframework.stereotype.Component
@Scope("prototype")
public class TwitterController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2167187007757661223L;
		
	private Component bookEventWin, editEventWin;
	private Window win;
	
	private static final TweetAddGenericSharer addTweetSharer = new TweetAddGenericSharer();
	private static final TweetUpdateGenericSharer updateTweetSharer = new TweetUpdateGenericSharer();
	
	private TweetModel model;
	private ProfileManager profileManager;
	
	@Autowired
	public void setTweetModelExtListener(TweetModel model) {
		this.model = model;
	}
	
	public TweetModel getCalendarModel() {
		return this.model;
	}
	
	@Autowired
	public void setProfileService(ProfileManager profileManager) {
		this.profileManager = profileManager;
	}
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
				
		HttpServletResponse response = (HttpServletResponse)Executions.getCurrent().getNativeResponse();
		
		if(!profileManager.isAuthenticated()) {
			try {
				response.sendRedirect(response.encodeRedirectURL(Messages.getString("TwitterController.0")));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Executions.getCurrent().setVoided(true);
		}
		
		return super.doBeforeCompose(page, parent, compInfo);
	}

	public void onEventCreate$cal(CalendarsEvent event){     
	    
		if(validDate(event.getBeginDate())) {
			Clients.alert(Messages.getString("TwitterController.1"));
			return;
		}
		
		Tweet tweet = new Tweet();
	    tweet.setBeginDate(event.getBeginDate());
	    tweet.setProfile(profileManager.getSessionProfile());
	    
	    bookEventWin = Executions.createComponents(Messages.getString("TwitterController.2"), win, generateArguments(tweet, addTweetSharer));
	    
	    AnnotateDataBinder adb = new AnnotateDataBinder(bookEventWin);
	    adb.loadAll();
	    
	    bookEventWin.setVisible(true);
	}
	
	public void onEventEdit$cal(CalendarsEvent event) {
		Tweet tweet = (Tweet)event.getCalendarEvent();
		
		if(tweet.isTweeted()) {
			Clients.alert(Messages.getString("TwitterController.3"));
			return;
		}
		
		editEventWin = Executions.createComponents(Messages.getString("TwitterController.4"), win, generateArguments(tweet, updateTweetSharer));
		
		AnnotateDataBinder adb = new AnnotateDataBinder(editEventWin);
	    adb.loadAll();
	    
	    editEventWin.setVisible(true);
	}
	
	public void onEventUpdate$cal(CalendarsEvent event) {
		Tweet tweet = (Tweet)event.getCalendarEvent();
		
		if(validDate(event.getBeginDate())) {
			Clients.alert(Messages.getString("TwitterController.5"));
			event.clearGhost();
			return;
		}
		
		if(tweet.isTweeted()) {
			alert(Messages.getString("TwitterController.6"));
			return;
		}
		
		if(event.getBeginDate().equals(tweet.getBeginDate())) {
			return;
		}
		
		tweet.setBeginDate(event.getBeginDate());
		model.update(tweet);		
	}
	
	private Map<String, GenericSharer<Tweet>> generateArguments(Tweet tweet, GenericSharer<Tweet> genericSharer) {
		    
		Map<String, GenericSharer<Tweet>> map = new HashMap<String, GenericSharer<Tweet>>();
	        
		genericSharer.setBean(tweet);
	    map.put("tweet", genericSharer);
	    
	    return map;
	}
	
	private boolean validDate(Date test) {
		
		if(test == null) {
			throw new NullPointerException(Messages.getString("TwitterController.8"));
		}
		
		final Date now = new Date();
		return test.before(now);
	}
}
