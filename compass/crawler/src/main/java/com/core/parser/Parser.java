package com.core.parser;

import org.slf4j.Logger;

import com.core.bean.Page;

public interface Parser {
	/**
	 * 解析详情
	 * @param html
	 * @return
	 */
	 Page parseDetailPage(String html);
	 /**
	  * 解析列表页
	  * @param html
	  * @return
	  */
	 Page parseListPage(String html);
	 
	 Logger getLog();
}
