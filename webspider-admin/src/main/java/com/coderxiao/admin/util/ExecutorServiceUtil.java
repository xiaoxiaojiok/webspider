package com.coderxiao.admin.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * 获得线程池实例（线性安全的单例模式）
 * 
 * @author XiaoJian <br>
 * @since 0.1.0
 *
 */
//第一种,Effective Java推荐
public class ExecutorServiceUtil {

	private static int num = ConfigUtil.one().getWorkerThread();

	private static class ExecutorServiceInstanceGet{
		private static final ExecutorService instance = Executors.newFixedThreadPool(num);
	}

	private ExecutorServiceUtil(){}
	
	public static ExecutorService one(){
		return ExecutorServiceInstanceGet.instance;
	}
	
}

//第二种
/*public class ExecutorServiceInstance {
	
	private volatile static ExecutorService executorService;
	
	private static int num = ConfigUtil.getInstance().getWorkerThread();
	
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