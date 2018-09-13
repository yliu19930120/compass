package com.jobCrawler;

import com.core.crawler.Crawler;
import com.core.parser.Parser;
import com.core.persistence.Storer;

public class JobCrawler {
		public static void main(String[] args) {
			Parser parse = new JobParser();
			Storer storer = new MongoStorer();
			Crawler jobCrawler = new Crawler(parse, storer, "jobCrawler");
			String url = "https://nba.hupu.com/";
			jobCrawler.run(url);
		}
}
