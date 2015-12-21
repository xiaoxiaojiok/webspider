package com.coderxiao.webservice;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;

import javax.management.JMException;

import com.coderxiao.webspider.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.monitor.SpiderMonitor;
import com.coderxiao.webspider.pipeline.MySQL;
import com.coderxiao.webspider.pipeline.PipelineFactory;
import com.coderxiao.webspider.pipeline.StorageType;
import com.coderxiao.webspider.scheduler.RedisScheduler;
import com.coderxiao.webspider.scheduler.ScheduleType;
import com.coderxiao.webspider.scheduler.SchedulerInstance;
import com.coderxiao.webspider.thread.ExecutorServiceInstance;

/***
 * 
 * 管理多个Spider，一台机器对应一个Worker
 * 
 * @author XiaoJian
 *
 */
public class Worker {

	/***
	 * 线程池服务
	 */
	private static ExecutorService executorService = ExecutorServiceInstance.getInstance();

	/***
	 * 调度器服务
	 */
	private static RedisScheduler redis = (RedisScheduler) SchedulerInstance.getInstance(ScheduleType.Redis);

	/***
	 * 目前的Spider表
	 */
	private static Map<Long, Spider> spiderMap = new ConcurrentHashMap<Long, Spider>();

	/***
	 * 每个Spider的最大线程数
	 * 
	 * 
	 */
	private static final int THREAD_NUM_PER_SPIDER = ConfigUtil.getInstance().getSpiderThread();

	/***
	 * 存储方式
	 */
	private static final String STORAGE_TYPE = ConfigUtil.getInstance().getStorageType();

	private Logger logger = LoggerFactory.getLogger(getClass());

	private static class WorkerInstanceGet {
		private static final Worker instance = new Worker();
	}

	private Worker() {
		//将每个Worker提供的WebService地址放到全局队列中
//		redis.zadd(RedisScheduler.WEBSERVICE_KEY, ConfigUtil.getInstance().getWebserviceAddress());
	}

	public static Worker getInstance() {
		return WorkerInstanceGet.instance;
	}

	/***
	 * <pre>
	 * 增加Spider
	 * </pre>
	 * @param spider
	 * @return
	 */
	public boolean addSpider(Spider spider) {
		spider.addPipeline(PipelineFactory.createPipeline(STORAGE_TYPE))
				.setExecutorService(executorService).setScheduler(redis)
				.thread(THREAD_NUM_PER_SPIDER);
		try {
			SpiderMonitor.instance().register(spider);
		} catch (JMException e) {
			logger.info(e.getMessage());
		}
		redis.zadd(RedisScheduler.SITE_KEY, spider.getSite().getId().toString());
		if (StorageType.Mysql.toString().equals(STORAGE_TYPE)) {
			MySQL.create(MySQL.TABLE_PREFIX + spider.getSite().getId());
		}
		spiderMap.put(spider.getSite().getId(), spider);
		return true;
	}

	/***
	 * <pre>
	 * 通过id从Spider表中取出对应的Spider
	 * </pre>
	 * @param id
	 * @return
	 */
	public Spider getSpider(Long id) {
		return spiderMap.get(id);
	}

	/***
	 * <pre>
	 * 删除Spider
	 * </pre>
	 * @param spider
	 * @return
	 */
	public boolean delSpider(Spider spider) {
		spider.getSite().setAllowRules(null);
		spider.stop();
		try {
			SpiderMonitor.instance().unregister(spider);
		} catch (JMException e) {
			logger.info(e.getMessage());
		}
		redis.del(RedisScheduler.QUEUE_PREFIX + spider.getUUID());
		redis.del(RedisScheduler.SET_PREFIX + spider.getUUID());
		redis.zrem(RedisScheduler.SITE_KEY, spider.getSite().getId().toString());
		if (StorageType.Mysql.toString().equals(STORAGE_TYPE)) {
			MySQL.drop(MySQL.TABLE_PREFIX + spider.getSite().getId());
		}
		spiderMap.remove(spider.getSite().getId());
		return true;
	}

	/***
	 * <pre>
	 * 更新Spider
	 * </pre>
	 * @param spider
	 * @return
	 */
	public boolean updateSpider(Spider spider) {
		spider.stop();
		redis.del(RedisScheduler.QUEUE_PREFIX + spider.getUUID());
		redis.del(RedisScheduler.SET_PREFIX + spider.getUUID());
		redis.zadd(RedisScheduler.SITE_KEY, spider.getSite().getId().toString());
		if (StorageType.Mysql.toString().equals(STORAGE_TYPE)) {
			MySQL.delete(MySQL.TABLE_PREFIX + spider.getSite().getId(), null);
		}
		return true;
	}

	/***
	 * 关闭爬虫
	 */
	public void shutdown() {
		executorService.shutdown();
	}

}
