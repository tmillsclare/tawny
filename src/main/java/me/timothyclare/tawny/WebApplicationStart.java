package me.timothyclare.tawny;

import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.util.WebAppInit;

public class WebApplicationStart implements WebAppInit {

	@Override
	public void init(WebApp wapp) throws Exception {
		TawnyApp.setTawnyApp((TawnyApp)wapp);
	}

}
