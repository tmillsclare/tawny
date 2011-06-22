package me.timothyclare.tawny.controllers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.sharer.AbstractGenericSharer;
import me.timothyclare.tawny.bean.sharer.GenericSharer;
import me.timothyclare.tawny.exceptions.token.TokenException;
import me.timothyclare.tawny.hibernate.TokenDAO;
import me.timothyclare.tawny.hibernate.TweetDAO;
import me.timothyclare.tawny.twitter.TwitterUtil;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.event.CalendarsEvent;
import org.zkoss.calendar.impl.SimpleCalendarModel;
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
	
	//private final Calendar myCalendar = Cal
	
	private Component bookEventWin, editEventWin;
	private Window win;
	private Calendars cal;
	
	//ListModelList model = new ListModelList();
	SimpleCalendarModel model = null;
	CalendarEvent currentEvent = null;
	
	public ComponentInfo doBeforeCompose(Page page, Component parent,
			ComponentInfo compInfo) {
		
		try {
			TokenDAO.getToken(); //test to see whether a token exists
			Twitter twitter = TwitterUtil.getInstance().getTwitter();

			if(twitter != null) {
				if(!twitter.getAuthorization().isEnabled()) { //if not authorized it we need a new token
					Executions.sendRedirect("token.zul");
				}
			} else {
				throw new RuntimeException("Could not retrieve twitter");
			}
		} catch (TokenException e) {
			e.printStackTrace();
			Executions.sendRedirect("token.zul");			
		} 
		
		return super.doBeforeCompose(page, parent, compInfo);
	}

	@Override
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		List<Tweet> tweets = TweetDAO.findAll();
		CalendarEvent[] calendarEvents = new CalendarEvent[tweets.size()];
		tweets.toArray(calendarEvents);
			
		if (tweets.isEmpty()) {
			model = new SimpleCalendarModel();
		} else {
			model = new SimpleCalendarModel(Arrays.asList(calendarEvents), false);
		}
		
		cal.setModel(model);
	}
	
	public void onEventCreate$cal(CalendarsEvent event){     
	    
		Tweet tweet = new Tweet();
	    tweet.setBeginDate(event.getBeginDate());
	    
	    bookEventWin = Executions.createComponents("macro/book.zul", win, generateArguments(tweet));
	    
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
		
		editEventWin = Executions.createComponents("macro/bookEdit.zul", win, generateArguments(tweet));
		
		AnnotateDataBinder adb = new AnnotateDataBinder(editEventWin);
	    adb.loadAll();
	    
	    editEventWin.setVisible(true);
	}
	
	private Map<String, GenericSharer<Tweet>> generateArguments(Tweet tweet) {
		Map<String, GenericSharer<Tweet>> map = new HashMap<String, GenericSharer<Tweet>>();
	    
	    GenericSharer<Tweet> tweetsharer = new AbstractGenericSharer<Tweet>() {

			@Override
			public void update() {
				TweetDAO.add(getBean());
				model.add(getBean());
			}
	    	
	    };
	        
	    tweetsharer.setBean(tweet);
	    map.put("tweet", tweetsharer);
	    
	    return map;
	}
}
