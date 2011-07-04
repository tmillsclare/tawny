package me.timothyclare.tawny.model;



import org.zkoss.zk.ui.WebApp;
import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;

public enum EventQueueHelper {
	INSTANCE;
	
	WebApp webApp;
	
	public void initialize(WebApp webApp) {
		this.webApp = webApp;
	}
	
	public EventQueue getEventQueue() {
		if(webApp == null) {
			throw new IllegalStateException("The helper has not been initialized yet");
		}
		
		return EventQueues.lookup("tweets", webApp, true);
	}
}
