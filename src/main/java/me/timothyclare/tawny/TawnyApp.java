package me.timothyclare.tawny;

import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;
import org.zkoss.zk.ui.http.SimpleWebApp;

public class TawnyApp extends SimpleWebApp {
	
	private EventQueue eventQueue;
	private static TawnyApp tawnyApp;
	
	public static void setTawnyApp(TawnyApp tawnyApp) {
		if(TawnyApp.tawnyApp != null) {
			throw new IllegalStateException("Tawny app cannot be set more than once");
		}
		
		if(tawnyApp == null) {
			throw new NullPointerException("tawnyApp cannot be null");
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
			eventQueue = EventQueues.lookup("tweet", this, true);
		}
		
		return eventQueue;
	}
}
