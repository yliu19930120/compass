package com.core.queue;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * java队列
 * 
 * @author Yonghua Liu
 * @email yliu19930120@163.com
 * @date 2018年5月1日下午2:44:20
 */
public class JavaQueue implements BufferQueue{
	
    private Map<String,Queue<String>> queueMap;
	
	public JavaQueue() {
		super();
		this.queueMap = new HashMap<String, Queue<String>>();
	}

	@Override
	public int push(String key, String data) {
		Queue<String> queue = queueMap.get(key);
		if(queue==null){
			queue = new ConcurrentLinkedQueue<String>(); 
			queueMap.put(key, queue);
		}
		queue.add(data);
		return 1;
	}

	@Override
	public String poll(String key) {
		Queue<String> queue = queueMap.get(key);
		return queue.poll();
	}

	@Override
	public int len(String key) {
		Queue<String> queue = queueMap.get(key);
		if(queue==null){
			return 0;
		}
		return queue.size();
	}

}
