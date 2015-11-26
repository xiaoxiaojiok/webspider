package com.coderxiao.webspider.processor.example;

import java.util.List;
import java.util.concurrent.ExecutorService;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.pipeline.HTMLPipeline;
import com.coderxiao.webspider.processor.PageProcessor;
import com.coderxiao.webspider.processor.test.SinaPageProcessor;
import com.coderxiao.webspider.scheduler.ScheduleType;
import com.coderxiao.webspider.scheduler.Scheduler;
import com.coderxiao.webspider.scheduler.SchedulerInstance;
import com.coderxiao.webspider.thread.ExecutorServiceInstance;

public class WebMagicDocPageProcessor implements PageProcessor{
	
	private Site site = Site.me().setRetryTimes(5).setSleepTime(1).setCycleRetryTimes(3);
//	private Site site = Site.me().setRetryTimes(5).setSleepTime(1).setCycleRetryTimes(3).setPriority(30);;
//	private Site site = Site.me().setRetryTimes(5).setSleepTime(1).setCycleRetryTimes(3).setDepth(3);

	@Override
	public void process(Page page) {
		page.putField("html", page.getHtml());
		List<String> list = page.getHtml()
				.css(".book-summary")
				.links()
				.regex("http://webmagic.io/docs/zh/posts/.*")
				.all();
		page.addTargetRequests(list);
/*		for(String url:list){
			 int depth = (Integer) page.getRequest().getExtra(Request.DEPTH);
			 depth++;
			page.addTargetRequest(new Request(url).putExtra(Request.DEPTH, depth));
		}*/
	}

	@Override
	public Site getSite() {
		return this.site;
	}
	
	public static void main(String[] args) {
		
		/**
		 * 原则上一个站点就是一个Spider
		 * 
		 * 当同一个站点的时候，启动两个Spider，那么需要有一定间隔启动或者两个Spider的startUrls需要不同，负责另外一个会退出
		 * 这是由于url被另外一个获取了，这样的话另外一个Spider一开始就获取不到url并且当前的活跃线程为0，所以退出
		 * 
		 * 
		 */
		//当多个Spider时候，QueueScheduler从队列里面出一条url，很可能不是当前站点的url,所以会出现一个域名目录下的网页是其他站点组成的
//		Scheduler scheduler = new QueueScheduler();
//		Scheduler scheduler = new WorkerScheduler();
//		Scheduler scheduler = new PriorityWorkerScheduler();
//		Scheduler scheduler = new DepthWorkerScheduler();
//		Scheduler scheduler = new RedisScheduler(REDIS_HOST);
//		Scheduler scheduler = new DepthRedisScheduler(REDIS_HOST);
		Scheduler scheduler = SchedulerInstance.getInstance(ScheduleType.Redis);
		
//		ExecutorService executorService = Executors.newFixedThreadPool(15);
		ExecutorService executorService = ExecutorServiceInstance.getInstance();
/*		Spider spider1 = Spider.create(new WebMagicDocPageProcessor());
		spider1
		.setExecutorService(executorService)
		.addPipeline(new HTMLPipeline("D:\\webmagic\\"))
		.addUrl("http://webmagic.io/docs/zh/")
		.scheduler(scheduler) //should set first
		.thread(3)
		.start();*/
		/*	
		Spider spider2 = Spider.create(new OschinaBlogPageProcessor());
		spider2
		.addPipeline(new HTMLPipeline("D:\\webmagic\\"))
		.addUrl("http://my.oschina.net/flashsword/blog")
		.scheduler(scheduler)
		.setExecutorService(executorService)
		.thread(5)
		.start();*/
		
		Spider spider3= Spider.create(new SinaPageProcessor());
		spider3
		.addPipeline(new HTMLPipeline("D:\\webmagic\\"))
		.addUrl("http://www.sina.com.cn/")
		.scheduler(scheduler)
		.setExecutorService(executorService)
		.thread(5)
		.start();
		
		
		
		while(true){
			if(
//					spider1.getStatus().equals(Spider.Status.Stopped) 
//					&& spider2.getStatus().equals(Spider.Status.Stopped) && 
					spider3.getStatus().equals(Spider.Status.Stopped)
							){
//				System.out.println("-------\n********spider1*******\ngetPageCount():\t"+spider1.getPageSuccess());
//				System.out.println("---\n********spider2*******\ngetPageCount():\t"+spider2.getPageSuccess());
				System.out.println("---\n********spider3*******\ngetPageCount():\t"+spider3.getPageSuccess());
				executorService.shutdown();
				break;
			}

		}
		
	}

}
