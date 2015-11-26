package com.coderxiao.webspider.monitor;

import java.util.concurrent.ExecutorService;

import javax.management.JMException;

import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.pipeline.FilePipeline;
import com.coderxiao.webspider.processor.example.GithubRepoPageProcessor;
import com.coderxiao.webspider.processor.example.OschinaBlogPageProcessor;
import com.coderxiao.webspider.scheduler.ScheduleType;
import com.coderxiao.webspider.scheduler.Scheduler;
import com.coderxiao.webspider.scheduler.SchedulerInstance;
import com.coderxiao.webspider.thread.ExecutorServiceInstance;

public class SpiderMonitorTest {

    public static void main(String[] args) throws JMException {
    	Scheduler scheduler = SchedulerInstance.getInstance(ScheduleType.Redis);
		ExecutorService executorService = ExecutorServiceInstance.getInstance();
    	Spider oschinaSpider = Spider.create(new OschinaBlogPageProcessor())
                .addUrl("http://my.oschina.net/flashsword/blog")
                .addPipeline(new FilePipeline("E:\\webmagic\\"))
                .scheduler(scheduler)
                .setExecutorService(executorService)
                .thread(5);
        Spider githubSpider = Spider.create(new GithubRepoPageProcessor())
                .addUrl("https://github.com/code4craft")
                .addPipeline(new FilePipeline("E:\\webmagic\\"))
                .scheduler(scheduler)
        		.setExecutorService(executorService)
        		.thread(10);

        SpiderMonitor.instance().register(oschinaSpider);
        SpiderMonitor.instance().register(githubSpider);
        oschinaSpider.start();
        githubSpider.start();
	}
}
