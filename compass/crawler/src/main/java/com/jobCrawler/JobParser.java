package com.jobCrawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.bean.Page;
import com.core.parser.Parser;

public class JobParser implements Parser{
	
	private final static Logger log = LoggerFactory.getLogger(JobParser.class);
	
	@Override
	public Page parseDetailPage(String html) {
		return new Page();
	}

	@Override
	public Page parseListPage(String html) {
		return new Page();
	}

	@Override
	public Logger getLog() {
		return log;
	}

}
