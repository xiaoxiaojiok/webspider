package com.coderxiao.webspider.scheduler;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 获得调度器实例（线性安全的单例模式）
 * 
 * @author XiaoJian
 *
 */
// 第一种,Effective Java推荐
public class SchedulerInstance {

	// 采用静态工厂方法（和设计模式中的工厂方法有区别）
	private static Map<String, Scheduler> schedulers = new HashMap<String, Scheduler>();

	static {
		schedulers.put(ScheduleType.Worker.value(), WorkerGet.instance);
		schedulers.put(ScheduleType.DepthWorker.value(), DepthWorkerGet.instance);
		schedulers.put(ScheduleType.Priority.value(), PriorityGet.instance);
		schedulers.put(ScheduleType.PriorityWorker.value(), PriorityWorkerGet.instance);
		schedulers.put(ScheduleType.Queue.value(), QueueGet.instance);
		schedulers.put(ScheduleType.Redis.value(), RedisGet.instance);
	}

	private static class WorkerGet {
		private static final Scheduler instance = new WorkerScheduler();
	}

	private static class DepthWorkerGet {
		private static final Scheduler instance = new DepthWorkerScheduler();
	}

	private static class PriorityGet {
		private static final Scheduler instance = new PriorityScheduler();
	}

	private static class PriorityWorkerGet {
		private static final Scheduler instance = new PriorityWorkerScheduler();
	}

	private static class QueueGet {
		private static final Scheduler instance = new QueueScheduler();
	}

	private static class RedisGet {
		private static final Scheduler instance = new DepthRedisScheduler();
	}

	private SchedulerInstance() {
	}

	public static Scheduler getInstance(ScheduleType scheduleType) {
		return schedulers.get(scheduleType.value());
	}
}

// 第二种

/*
 * public class SchedulerInstance {
 * 
 * private volatile static Scheduler scheduler;
 * 
 * private SchedulerInstance(){}
 * 
 * public static Scheduler getInstance(){ if(scheduler == null){ synchronized
 * (ExecutorServiceInstance.class) { if(scheduler == null){ scheduler = new
 * DepthRedisScheduler(); } } } return scheduler; } }
 */
