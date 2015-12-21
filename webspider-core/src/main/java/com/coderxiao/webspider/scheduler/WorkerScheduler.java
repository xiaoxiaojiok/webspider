package com.coderxiao.webspider.scheduler;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.annotation.ThreadSafe;

import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;

/**
 * 基于内存队列的URL队列调度器<br>
 * Store urls to fetch in LinkedBlockingQueue and remove duplicate urls by
 * HashMap.
 *
 * @author XiaoJian <br>
 * @since 0.1.0
 */
@ThreadSafe
public class WorkerScheduler extends DuplicateRemovedScheduler implements
		MonitorableScheduler {

	protected static final String UUID ="uuid";
	
	protected BlockingQueue<Request> queue = new LinkedBlockingQueue<Request>();
	
	protected ReentrantLock getUrlLock = new ReentrantLock();

	protected Condition getUrlCondition = getUrlLock.newCondition();

	@Override
	public void pushWhenNoDuplicate(Request request, Task task) {
		request.putExtra(UUID, task.getUUID());
		queue.add(request);
	}
	

	@Override
	public Request poll(Task task) {
		if(queue.peek()!=null && !queue.peek().getExtra(UUID).equals(task.getUUID())){
			try {
				getUrlLock.lock();
				while(queue.peek()!=null && !queue.peek().getExtra(UUID).equals(task.getUUID())){
					getUrlCondition.await();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}finally{
				getUrlLock.unlock();
			}
		}
		Request request = null;
		try{
			//TODO
			// 默认每一个Task都只有一个实例，若同一个Task有多个实例，那么这里将存在竞态条件
			getUrlLock.lock();
			request = queue.poll();
			getUrlCondition.signalAll();
		}finally{
			getUrlLock.unlock();
		}
		return request;

	}

	@Override
	public long getLeftRequestsCount(Task task) {
		//如果要做监控，需要改变这个方法，可以在通过另外一个类封装BlockingQueue，在类里面对不同的Task进行原子统计
		return queue.size();
	}

	@Override
	public long getTotalRequestsCount(Task task) {
		//如果要做监控，需要改变这个方法，可以在通过另外一个类封装BlockingQueue，在类里面对不同的Task进行原子统计
		return getDuplicateRemover().getTotalRequestsCount(task);
	}
}
