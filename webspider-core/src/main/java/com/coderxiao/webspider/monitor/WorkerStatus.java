package com.coderxiao.webspider.monitor;

import java.util.Set;

import com.coderxiao.webspider.scheduler.RedisScheduler;
import com.coderxiao.webspider.scheduler.ScheduleType;
import com.coderxiao.webspider.scheduler.SchedulerInstance;
import com.coderxiao.webspider.util.ConfigUtilInstance;

public class WorkerStatus implements WorkerStatusMXBean{
	
    private static RedisScheduler redis = (RedisScheduler)SchedulerInstance.getInstance(ScheduleType.Redis);

	@Override
	public String getWebServiceURL() {
		return ConfigUtilInstance.getInstance().getWebserviceAddress();
	}

	@Override
	public Set<String> getAllWebServiceURLs() {
		return redis.zrange(RedisScheduler.WEBSERVICE_KEY,0,-1);
	}

	@Override
	public Set<String> getAllSites() {
		return redis.zrange(RedisScheduler.SITE_KEY,0,-1);
	}

	@Override
	public long getAllWebServiceURLsCount() {
		return redis.zcard(RedisScheduler.WEBSERVICE_KEY);
	}

	@Override
	public long getAllSitesCount() {
		return redis.zcard(RedisScheduler.SITE_KEY);
	}

}
