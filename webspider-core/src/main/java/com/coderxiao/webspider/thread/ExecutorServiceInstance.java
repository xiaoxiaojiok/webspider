package com.coderxiao.webspider.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.coderxiao.webspider.util.ConfigUtilInstance;

/**
 * 
 * 获得线程池实例（线性安全的单例模式）
 * 
 * @author XiaoJian <br>
 * @since 0.1.0
 *
 */
//第一种,Effective Java推荐
public class ExecutorServiceInstance {
	
	private static int num = ConfigUtilInstance.getInstance().getWorkerThread();
	
	private static class ExecutorServiceInstanceGet{
		private static final ExecutorService instance = Executors.newFixedThreadPool(num);
	}
	
	private ExecutorServiceInstance(){}
	
	public static ExecutorService getInstance(){
		return ExecutorServiceInstanceGet.instance;
	}
	
}

//第二种
/*public class ExecutorServiceInstance {
	
	private volatile static ExecutorService executorService;
	
	private static int num = ConfigUtilInstance.getInstance().getWorkerThread();
	
	private ExecutorServiceInstance(){}
	
	public static ExecutorService getInstance(){
		if(executorService == null){
			synchronized (ExecutorServiceInstance.class) {
				if(executorService == null){
					executorService = Executors.newFixedThreadPool(num);
				}
			}
		}
		return executorService;
	}
}*/