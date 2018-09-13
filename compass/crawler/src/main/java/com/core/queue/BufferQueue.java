package com.core.queue;

/**
 * 缓存队列
 * 
 * @author Yonghua Liu
 * @email liuyonghua@bly100.com
 * @date 2018年4月21日下午4:44:02
 */
public interface BufferQueue {

	int push(String key,String data);
	
	String poll(String key);
	
	int len(String key);
}
