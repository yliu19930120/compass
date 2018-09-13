package com.core.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

/**
 * 爬虫线程工厂
 * 
 * @author Yonghua Liu
 * @email liuyonghua@bly100.com
 * @date 2018年1月19日下午4:37:07
 */
public class CrawlerThreadFactory implements ThreadFactory {

	private int counter;

	private String name;

	private List<String> stats;

	public CrawlerThreadFactory(String name) {

		counter = 0;

		this.name = name;

		stats = new ArrayList<String>();

	}

	@Override
	public Thread newThread(Runnable r) {
		// TODO Auto-generated method stub
		Thread t = new Thread(r, name + "-Thread_" + counter);

		counter++;

		stats.add(String.format("created thread  with name  on ", t.getId(), t.getName(), new Date()));

		return t;
	}

	
	
	public String getStats() {

		StringBuffer buffer = new StringBuffer();

		Iterator<String> it = stats.iterator();

		while (it.hasNext()) {

			buffer.append(it.next());

			buffer.append("\n");

		}

		return buffer.toString();

	}

}
