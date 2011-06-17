package me.timothyclare.twittwoo.bean;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Tweet {

	private Long id;
	private String text;
	private Date schedule;
	private boolean tweeted;

	public Tweet() {
	}

	public Tweet(String text, Date schedule) {
		this.text = text;
		this.schedule = schedule;
		this.tweeted = false;
	}

	@Id
	@GeneratedValue
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@NotNull
	@Column(length = 140)
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@NotNull
	public Date getSchedule() {
		return schedule;
	}

	public void setSchedule(Date schedule) {
		this.schedule = schedule;
	}

	public void setTweeted(boolean tweeted) {
		this.tweeted = tweeted;
	}
	
	@NotNull
	public boolean isTweeted() {
		return tweeted;
	}

}
