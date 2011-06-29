package me.timothyclare.tawny.model.api;

import java.util.List;

import org.zkoss.calendar.impl.AbstractCalendarModel;

public abstract class AbstractCalendarModelExt<T> extends AbstractCalendarModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2289167618236802251L;
	
	public abstract List<T> getAll();
	
	public abstract void add(T t);
	public abstract void remove(T t);
	public abstract void update(T t);
}
