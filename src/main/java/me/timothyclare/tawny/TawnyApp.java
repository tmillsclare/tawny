package me.timothyclare.tawny;

import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.http.SimpleWebApp;

public class TawnyApp extends SimpleWebApp {
	
	private EventQueue eventQueue;
	private static TawnyApp tawnyApp;
	
	public static void setTawnyApp(TawnyApp tawnyApp) {
		if(TawnyApp.tawnyApp != null) {
			throw new IllegalStateException(Messages.getString("TawnyApp.0"));
		}
		
		if(tawnyApp == null) {
			throw new NullPointerException(Messages.getString("TawnyApp.1"));
		}
		
		TawnyApp.tawnyApp = tawnyApp;
	}
	
	public static TawnyApp getTawnyApp() {
		return TawnyApp.tawnyApp;
	}
	
	public TawnyApp() {
		super();
	}
	
	public EventQueue getTweetEventQueue() {
		
		if(eventQueue == null) {
			eventQueue = EventQueues.lookup(Messages.getString("TawnyApp.2"), this, true);
		}
		
		return eventQueue;
	}
	
	public EventQueue getProfileEventQueue() {
		if(eventQueue == null) {
			eventQueue = EventQueues.lookup("profile", this, true);
		}
		
		return eventQueue;
	}
}
