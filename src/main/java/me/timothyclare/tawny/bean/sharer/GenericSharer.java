package me.timothyclare.tawny.bean.sharer;


public interface GenericSharer<T> {
	void setBean(T p);
	T getBean();
	
	void update();
}
