package me.timothyclare.twittwoo.controllers;

import java.util.List;

import me.timothyclare.twittwoo.bean.Tweet;
import me.timothyclare.twittwoo.constraint.DateBeforeNow;
import me.timothyclare.twittwoo.constraint.TweetLength;
import me.timothyclare.twittwoo.exceptions.token.TokenException;
import me.timothyclare.twittwoo.hibernate.TokenDAO;
import me.timothyclare.twittwoo.hibernate.TweetDAO;
import me.timothyclare.twittwoo.twitter.TweetManager;
import me.timothyclare.twittwoo.twitter.TwitterUtil;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Page;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.metainfo.ComponentInfo;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Datebox;
import org.zkoss.zul.Grid;
import org.zkoss.zul.Label;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Row;
import org.zkoss.zul.RowRenderer;
import org.zkoss.zul.Textbox;

import twitter4j.Twitter;

public class TwitterController extends GenericForwardComposer {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2167187007757661223L;
	
	Grid tweetGrid;
	Textbox txttweet;
	Datebox dbschedule;
	Button schedule;
	
	ListModelList model = new ListModelList();
	
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
		
		txttweet.setConstraint(new TweetLength());
		dbschedule.setConstraint(new DateBeforeNow());
		
		tweetGrid.setRowRenderer(new RowRenderer() {
			public void render(Row row, Object data) throws Exception {
				Tweet tweet = (Tweet)data;
				
				new Label(tweet.getText()).setParent(row);
				new Label(tweet.getSchedule().toString()).setParent(row);
			}
		});
		
		List<Tweet> tweets = TweetDAO.findAll();
		model.addAll(tweets);
		tweetGrid.setModel(model);
	}
	
	public void onClick$schedule(Event e) {
		if(!(txttweet.isValid() && dbschedule.isValid()))
			return;
		
		Tweet tweet = new Tweet(txttweet.getValue(), dbschedule.getValue());
		
		model.add(tweet);
		TweetDAO.add(tweet);
		TweetManager.getInstance().scheduleTweet(tweet, dbschedule.getValue());
	}
}
