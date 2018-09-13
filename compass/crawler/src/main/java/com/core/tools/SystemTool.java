package com.core.tools;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SystemTool {

	private final static Logger log = LoggerFactory.getLogger(SystemTool.class);
	
	//关机
	public static void shutdown(){
		try {
			Runtime.getRuntime().exec("shutdown -s -t 10");
		} catch (IOException e) {
			log.error("关机失败,{}",e.getMessage());
		}
	}
}
