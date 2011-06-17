package me.timothyclare.twittwoo.constraint;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class TweetLength implements Constraint {

	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		
		String item = (String)arg1;
		
		if((item == null) || ("".equals(item))) {
			throw new WrongValueException(arg0, "The value cannot be null or empty");
		}
		
		if(item.length() > 140) {
			throw new WrongValueException(arg0, "Tweet must be 140 characters or less");
		}
	}

}
