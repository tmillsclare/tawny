package me.timothyclare.tawny.bean.sharer;

import me.timothyclare.tawny.bean.TweetContext;
import me.timothyclare.tawny.twitter.TweetManager;

public class TweetAddGenericSharer extends AbstractGenericSharer<TweetContext> {

	@Override
	public void update() {
		TweetManager.INSTANCE.addTweet(getBean());		
	}

}
