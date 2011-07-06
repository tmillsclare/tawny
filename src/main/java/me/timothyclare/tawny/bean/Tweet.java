package me.timothyclare.tawny.bean;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import me.timothyclare.tawny.Messages;

import org.zkoss.calendar.api.CalendarEvent;

@Entity
public class Tweet implements CalendarEvent, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5073488408284034265L;
	
	private Long id;
	private String text;
	private Date beginDate;
	private boolean tweeted;
	private Profile profile;

	private static final String notTweetedColour = Messages.getString("Tweet.0");
	private static final String tweetedColour = Messages.getString("Tweet.1");

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
		String sId = this.id == null ? "" : this.id.toString();
		return Messages.getString("Tweet.3") + sId;
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}
	
	@Transient
	@Override
	public String getHeaderColor() {
		return tweeted ? tweetedColour : notTweetedColour;
	}
	
	@Transient
	@Override
	public String getContentColor() {
		return tweeted ? tweetedColour : notTweetedColour;
	}
	
	@Transient
	@Override
	public String getZclass() {
		return "";
	}
	
	@Transient
	@Override
	public boolean isLocked() {
		return tweeted;
	}

	@Override
	public boolean equals(Object arg0) {
		if (arg0 == this) { return true;}
		if (arg0 instanceof Tweet) {
			Tweet tweet = (Tweet)arg0;
			
			if((this.getId() == null) || (((Tweet) arg0).getId() == null)) { return false;}
			
			return tweet.getId().equals(this.getId());
		}
		
		return false;
	}

	@Override
	public int hashCode() {
		
		final int hashCode;
		
		if(getId() != null) {
			hashCode = getId().hashCode();
		} else {
			hashCode = 0;
		}
		
		return hashCode;
	}
	
	

}
