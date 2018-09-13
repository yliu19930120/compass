package com.core.crawler;



import java.util.List;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.core.bean.Page;
import com.core.exception.NoTagException;
import com.core.parser.Parser;
import com.core.persistence.Storer;
import com.core.queue.BufferQueue;
import com.core.queue.JavaQueue;
import com.core.request.Request;
import com.core.thread.CrawlerThreadFactory;
import com.core.tools.Timer;


/**
 * 爬虫
 * 
 * @author Yonghua Liu
 * @email yliu19930120@163.com
 * @date 2018年4月21日下午5:10:58
 */
public class Crawler {
	
    private static Logger logger = LoggerFactory.getLogger(Crawler.class);  
	
	private Parser parser;//解析工具
    
	private String firstPage;//抓取起始页
	
	private Storer storer;//数据持久化
	
	private static ThreadFactory factory;//线程工厂
	
    private static long startTime;//启动时间戳
	
    private BufferQueue queue; //缓存队列，用于多线程爬取

    private static boolean htmlThread = false;
    
    private static boolean saveThread = false;
    
    private static long lastHtmlNum = 0;
    
    private static long lastLinkNum = 0;
    
	public Crawler(Parser parser,Storer storer,BufferQueue queue) {
		super();
		this.parser = parser;
		this.storer = storer;
		this.queue = queue;
	}
	
	public Crawler(Parser parse,Storer storer,String threadName) {
		super();
		this.parser = parse;
		this.storer = storer;
		this.queue = new JavaQueue();
		Crawler.factory =new CrawlerThreadFactory(threadName);
	}

	public Crawler() {
		super();
	}

	//启动抓取程序
	public void run(String url){
		this.firstPage = url;
		startTime = System.currentTimeMillis();
		startTh(getLinks(),1);
		startTh(listen(),1);
	}
		
	/* 递归翻页抓取 */
	private void recursion(String last,String url) throws NoTagException {
		
//		logger.info("当前页:{}",url);
       
		String body = Request.get(url);
		Page page = parser.parseListPage(body);

		List<Page> pageList = page.getChild();
		if(pageList==null){
			throw new NoTagException("无子页面可抓取");
		}
		String[] links = new String[pageList.size()];
		for (int i = 0; i < links.length; i++) {
			links[i] = pageList.get(i).getSource();
			queue.push("links",links[i]);
		}
		String next = page.getNext();
		if (page.isNextCatch()){
			if(next==null){
				throw new NoTagException("无翻页字段,请赋值翻页标识");
			}
			recursion(url,next);// 存在下一页，用递归抓取
		} else {
			parser.getLog().info("无下一页");
			logger.info("全部页码抓取完毕");

		}
	}
	

	/*获取详情页链接线程*/
	private Runnable getLinks() {

		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				logger.info("启动获取链接线程");
				try {
					recursion(firstPage,firstPage);
				} catch (NoTagException e) {
					logger.error("{}",e);
					e.printStackTrace();
				}
			}
		};

		return runnable;
	}

	/* 获取详情页html线程 */
	private Runnable getHtml() {

		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				logger.info("启动获取HTML线程");
				while (true) {
					try {
						TimeUnit.SECONDS.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String link = queue.poll("links");
					String html = Request.get(link);
					queue.push("htmls",html);

				}
			}
		};

		return runnable;

	}

	/*存储线程*/
	 Runnable save() {
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				logger.info("启动存储线程");
				while (true) {
					String html = queue.poll("htmls");
					Page page = new Page();
					try {
						page = parser.parseDetailPage(html);
					} catch (Exception e) {
						continue;
						// TODO: handle exception
					}
					storer.save(page);
				}

			}
		};
		return runnable;
	}

	//启动线程
	private static void startTh(Runnable runable,int Num){
		for (int i = 0; i < Num; i++) {
			Thread threand = factory.newThread(runable);
			threand.start();
		}
	}
	
	/*监听线程*/
	private Runnable listen() {
		
		Runnable runnable = new Runnable() {

			@Override
			public void run() {
				logger.info("启动监听线程");
				while (true) {

					try {
						TimeUnit.SECONDS.sleep(5);
					} catch (InterruptedException e) {
						logger.error(e.getMessage());
					}
					long linkNum = queue.len("links");
					long htmlNum = queue.len("htmls");
					double speed = (linkNum - lastLinkNum)/(5+0.00);
					double speed2 = (htmlNum - lastHtmlNum)/(5+0.00);
					logger.info("队列中链接数量：{},速度：{}条/秒",linkNum,speed);
					logger.info("队列中HTML数量：{},速度：{}条/秒",htmlNum,speed2);
					logger.info("耗时：{}",Timer.getTimeFormat(startTime));
					lastLinkNum = linkNum;
					lastHtmlNum = htmlNum;
					//链接数超过1000开始请求详情页
					if(!htmlThread&&linkNum>1000){
						startTh(getHtml(),5);
						htmlThread = true;
					}
					if(!saveThread&&htmlNum>1000){
						startTh(save(),2);
						saveThread = true;
					}
					if(linkNum == 0){
						logger.info("抓取完毕，退出爬虫");
						System.exit(0);
					}
				}
			}
		};

		return runnable;
	}
		

	
}
