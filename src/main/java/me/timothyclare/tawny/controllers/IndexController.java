package me.timothyclare.tawny.controllers;

import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Tab;
import org.zkoss.zul.Tabbox;
import org.zkoss.zul.Tabpanel;

public class IndexController extends GenericForwardComposer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6801701823489091532L;
	
	Tabbox tbProfile;

	public void onSelect$tbProfile(ForwardEvent e) {
		Object o = e.getOrigin().getTarget();

		if (o instanceof Tab) {
			Tab t = (Tab)o;
			Tabpanel tp = t.getLinkedPanel();
			tp.invalidate();
		}
	}
}
