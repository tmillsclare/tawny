package me.timothyclare.tawny.controllers;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.hibernate.TweetDAO;

import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Textbox;

public class BookingController extends GenericForwardComposer {
	
	Textbox tbTitle, tbNote;
	
	public void onClick$btnAdd(ForwardEvent fe) {
		Tweet tweet = new Tweet();
		tweet.setContent(tbNote.getText());
		tweet.setTweeted(false);
		TweetDAO.add(tweet);
	}
}
