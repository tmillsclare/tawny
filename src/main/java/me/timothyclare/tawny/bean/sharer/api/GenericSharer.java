package me.timothyclare.tawny.bean.sharer.api;


public interface GenericSharer<T> {
	void setBean(T p);
	T getBean();
	
	void update();
}
