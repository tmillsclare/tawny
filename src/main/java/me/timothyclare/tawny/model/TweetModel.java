package me.timothyclare.tawny.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.TawnyApp;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.event.TweetEvent;
import me.timothyclare.tawny.model.api.AbstractCalendarModelExt;
import me.timothyclare.tawny.services.api.TweetService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.api.RenderContext;
import org.zkoss.calendar.event.CalendarDataEvent;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;

@Component
@Scope("desktop")
public class TweetModel extends AbstractCalendarModelExt<Tweet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6946213744355873044L;
	
	private TweetService tweetService;
	
	@Autowired
	public void setTweetService(TweetService tweetService) {
		this.tweetService = tweetService;
	}
	
	public TweetModel() {
		TawnyApp.getTawnyApp().getTweetEventQueue().subscribe(new EventListener() {
			@Override
			public void onEvent(Event event) throws Exception {
				if(!(event instanceof TweetEvent)) {
					throw new IllegalArgumentException(Messages.getString("TweetModel.0"));
				}
				
				TweetEvent evt = (TweetEvent)event;
				fireEvent(evt.getType(), evt.getTweet());
			}
		});
	}

	@Override
	public List<CalendarEvent> get(Date beginDate, Date endDate,
			RenderContext rc) {
		List<Tweet> tweets = tweetService.all();
		CalendarEvent[] calendarEvents = new CalendarEvent[tweets.size()];
		tweets.toArray(calendarEvents);
		return new ArrayList<CalendarEvent>(tweets);
	}
	
	public List<Tweet> getAll() {
		return tweetService.all();
	}

	@Override
	public void add(Tweet t) {
		tweetService.add(t);
		fireEvent(CalendarDataEvent.INTERVAL_ADDED, t);
	}

	@Override
	public void remove(Tweet t) {
		tweetService.remove(t);
		fireEvent(CalendarDataEvent.INTERVAL_REMOVED, t);
	}

	@Override
	public void update(Tweet t) {
		tweetService.update(t);
		fireEvent(CalendarDataEvent.CONTENTS_CHANGED, t);
	}

}
