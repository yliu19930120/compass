package com.core.bean;

import java.util.List;
import java.util.Map;

public class Page extends Paging{
	
	 private String source;
	 
     private String next;
     
     private List<Page> child;
     
     private Map<String,Object> item; //抓取数据
     
     private String last;
     
     private boolean nextCatch;
     
     private boolean auth;
     
     
	public String getLast() {
		return last;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public boolean isAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public boolean isNextCatch() {
		return nextCatch;
	}

	public void setNextCatch(boolean nextCatch) {
		this.nextCatch = nextCatch;
	}

	public String getSource() {
		return source;
	}

	public String getNext() {
		return next;
	}

	public List<Page> getChild() {
		return child;
	}

	public Map<String, Object> getItem() {
		return item;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public void setNext(String next) {
		this.next = next;
	}

	public void setChild(List<Page> child) {
		this.child = child;
	}

	public void setItem(Map<String, Object> item) {
		this.item = item;
	}
     
     
}
