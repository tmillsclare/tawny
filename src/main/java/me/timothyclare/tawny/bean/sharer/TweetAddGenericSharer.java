package me.timothyclare.tawny.bean.sharer;

import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.model.TweetModelExtListener;

import org.zkoss.zkplus.spring.SpringUtil;

public class TweetAddGenericSharer extends AbstractGenericSharer<Tweet> {

	@Override
	public void update() {
		Object object = SpringUtil.getBean("myTweetModelExtListener");
		
		if(!(object instanceof TweetModelExtListener)) {
			throw new  RuntimeException("Unexpected object");
			//TODO: Change this to a valid exception
		}
		
		TweetModelExtListener tweetModel = (TweetModelExtListener)object;
		tweetModel.add(getBean());
	}

}
