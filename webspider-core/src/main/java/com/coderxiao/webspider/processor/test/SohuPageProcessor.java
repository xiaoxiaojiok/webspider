package com.coderxiao.webspider.processor.test;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.coderxiao.webspider.Page;
import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.pipeline.HTMLPipeline;
import com.coderxiao.webspider.processor.PageProcessor;
import com.coderxiao.webspider.scheduler.DepthRedisScheduler;
import com.coderxiao.webspider.scheduler.Scheduler;

public class SohuPageProcessor implements PageProcessor {

	private Site site = Site.me().setRetryTimes(3).setSleepTime(1)
			.setCycleRetryTimes(3).setDepth(4);

	@Override
	public void process(Page page) {
		page.putField("html", page.getHtml());
		Pattern pattern=Pattern.compile("<meta[^>]*?charset[\\s]*=[\\s]*['|\"]?([a-z|A-Z|0-9]*[\\-]*[0-9]*)[\\s|\\S]*['|\"]?");
        Matcher matcher=pattern.matcher(page.getRawText());
        if(matcher.find()){
            page.putField("charset", matcher.group(1));
        }
		List<String> list = page.getHtml()
				.links()
				.regex("http://news.sohu.com/201504[0-9]+/n[0-9]+.shtml")
				.all();
		for (String url : list) {
			int depth = (Integer) page.getRequest().getExtra(Request.DEPTH);
			depth++;
			page.addTargetRequest(new Request(url).putExtra(Request.DEPTH,
					depth));
		}
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
		// 当多个Spider时候，QueueScheduler从队列里面出一条url，很可能不是当前站点的url,所以会出现一个域名目录下的网页是其他站点组成的
		// Scheduler scheduler = new QueueScheduler();
		Scheduler scheduler = new DepthRedisScheduler();
		ExecutorService executorService = Executors.newFixedThreadPool(15);

		Spider spider = Spider.create(new SohuPageProcessor());
		spider.addPipeline(new HTMLPipeline("D:\\webmagic\\"))
				.addUrl("http://www.sohu.com/")
				.addUrl("http://news.sohu.com/")
				.scheduler(scheduler)
				.setExecutorService(executorService).thread(5).start();

		while (true) {
			if (spider.getStatus().equals(Spider.Status.Stopped)) {
				System.out
						.println("---\n********spider*******\ngetPageCount():\t"
								+ spider.getPageSuccess());
				executorService.shutdown();
				break;
			}

		}

	}

}
