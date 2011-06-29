package me.timothyclare.tawny.controllers;

import java.util.HashMap;
import java.util.Map;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.sharer.GenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetAddGenericSharer;
import me.timothyclare.tawny.bean.sharer.TweetUpdateGenericSharer;
import me.timothyclare.tawny.dao.TokenDAO;
import me.timothyclare.tawny.exceptions.token.TokenException;
import me.timothyclare.tawny.model.TweetModelExtListener;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zkplus.databind.AnnotateDataBinder;
import org.zkoss.zul.Window;

import twitter4j.Twitter;

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
	
	public void setTweetModelExtListener(TweetModelExtListener model) {
		this.model = model;
	}
	
	@Override
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		
		try {
			TokenDAO.getToken(); //test to see whether a token exists
			Twitter twitter = TwitterUtil.INSTANCE.getTwitter();

			if(twitter != null) {
				if(!twitter.getAuthorization().isEnabled()) { //if not authorized it we need a new token
					Executions.sendRedirect("token.zul");
				}
			} else {
				throw new RuntimeException("Could not retrieve twitter");
			}
		} catch (TokenException e) {
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
