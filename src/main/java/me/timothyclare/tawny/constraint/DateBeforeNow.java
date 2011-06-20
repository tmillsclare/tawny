package me.timothyclare.tawny.constraint;

import java.util.Date;

import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zul.Constraint;

public class DateBeforeNow implements Constraint {

	public void validate(Component arg0, Object arg1)
			throws WrongValueException {
		
		Date date = (Date)arg1;
		Date now = new Date();
		
		if(date.before(now)) {
			throw new WrongValueException("Tweet cannot be scheduled retrospectively!");
		}
	}

}
