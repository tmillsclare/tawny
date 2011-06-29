package me.timothyclare.tawny.model;


import org.zkoss.zk.ui.event.EventQueue;
import org.zkoss.zk.ui.event.EventQueues;

public enum EventQueueHelper {
	INSTANCE;
	
	public EventQueue getEventQueue() {
		return EventQueues.lookup("tweets", EventQueues.APPLICATION, true);
	}
}
