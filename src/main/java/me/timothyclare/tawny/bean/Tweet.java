package me.timothyclare.tawny.bean;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.zkoss.calendar.api.CalendarEvent;

@Entity
public class Tweet implements CalendarEvent {

	private Long id;
	private String text;
	private Date beginDate;
	private boolean tweeted;
	
	private static final String colour = "blue";

	public Tweet() {
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTweeted(boolean tweeted) {
		this.tweeted = tweeted;
	}
	
	@NotNull
	public boolean isTweeted() {
		return tweeted;
	}
	
	@NotNull
	@Override
	public Date getBeginDate() {
		return beginDate;
	}
	
	public void setBeginDate(Date date) {
		beginDate = date;
	}
	
	@Transient
	@Override
	public Date getEndDate() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);
		cal.add(Calendar.HOUR, 1);
		return cal.getTime();
	}
	
	@Transient
	@Override
	public String getTitle() {
		return "Tweet: " + Long.toString(getId());
	}
	
	@NotNull
	@Column(length = 140)
	@Override
	public String getContent() {
		return text;
	}
	
	public void setContent(String content) {
		text = content;
	}
	
	@Transient
	@Override
	public String getHeaderColor() {
		return colour;
	}
	
	@Transient
	@Override
	public String getContentColor() {
		return colour;
	}
	
	@Transient
	@Override
	public String getZclass() {
		return "";
	}
	
	@Transient
	@Override
	public boolean isLocked() {
		return false;
	}

}
