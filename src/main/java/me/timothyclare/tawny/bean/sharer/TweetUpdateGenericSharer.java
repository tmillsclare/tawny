package me.timothyclare.tawny.bean.sharer;

import me.timothyclare.tawny.Messages;
import me.timothyclare.tawny.bean.Tweet;
import me.timothyclare.tawny.bean.sharer.api.AbstractGenericSharer;
import me.timothyclare.tawny.model.TweetModel;

import org.zkoss.zkplus.spring.SpringUtil;

public class TweetUpdateGenericSharer extends AbstractGenericSharer<Tweet> {

	@Override
	public void update() {
		TweetModel model = SpringUtil.getApplicationContext().getBean(TweetModel.class);
		
		if(model == null) {
			throw new  NullPointerException(Messages.getString("TweetUpdateGenericSharer.0"));
			//TODO: Change this to a valid exception
		}
		
		model.update(getBean());
	}

}
