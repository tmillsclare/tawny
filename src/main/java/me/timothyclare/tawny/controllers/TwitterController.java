package me.timothyclare.tawny.controllers;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.bean.Profile;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.sharer.GenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetAddGenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetUpdateGenericSharer;
import me.timothyclare.tawny.model.TweetModelExtListener;
import me.timothyclare.tawny.services.api.ProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
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
	private Calendars cal;
	
	private static final TweetAddGenericSharer addTweetSharer = new TweetAddGenericSharer();
	private static final TweetUpdateGenericSharer updateTweetSharer = new TweetUpdateGenericSharer();
	
	private TweetModelExtListener model;
	private ProfileService profileService;
	
	@Autowired
	public void setTweetModelExtListener(TweetModelExtListener model) {
		this.model = model;
	}
	
	@Autowired
	public void setProfileService(ProfileService profileService) {
		this.profileService = profileService;
	}
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		
		if(profileService.profileExists("official")) {
			
			Profile profile = profileService.getProfile("official");
			
			if(profile == null) {
				Executions.sendRedirect("token.zul");
			} else {
				if(!profile.getTwitter().getAuthorization().isEnabled()) {
					Executions.sendRedirect("token.zul");
				}
			}
		} else {
			Executions.sendRedirect("token.zul");
		}
		
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		cal.setModel(model);
	}
	
	public void onEventCreate$cal(CalendarsEvent event){     
	    
		Tweet tweet = new Tweet();
	    tweet.setBeginDate(event.getBeginDate());
	    
	    bookEventWin = Executions.createComponents("macro/book.zul", win, generateArguments(tweet, addTweetSharer));
	    
	    AnnotateDataBinder adb = new AnnotateDataBinder(bookEventWin);
	    adb.loadAll();
	    
	    bookEventWin.setVisible(true);
	}
	
	public void onEventEdit$cal(CalendarsEvent event) {
		Tweet tweet = (Tweet)event.getCalendarEvent();
		
		if(tweet.isTweeted()) {
			alert("You cannot edit this tweet it has already been tweeted");
			return;
		}
		
		editEventWin = Executions.createComponents("macro/bookEdit.zul", win, generateArguments(tweet, updateTweetSharer));
		
		AnnotateDataBinder adb = new AnnotateDataBinder(editEventWin);
	    adb.loadAll();
	    
	    editEventWin.setVisible(true);
	}
	
	public void onEventUpdate$cal(CalendarsEvent event) {
		Tweet tweet = (Tweet)event.getCalendarEvent();
		
		if(tweet.isTweeted()) {
			alert("You cannot update this tweet it has already been tweeted");
			return;
		}
		
		if(event.getBeginDate().equals(tweet.getBeginDate())) {
			event.clearGhost();
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
}
