package com.coderxiao.webspider.scheduler;

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
public class DepthWorkerScheduler extends WorkerScheduler {

	@Override
	public void pushWhenNoDuplicate(Request request, Task task) {
		 Object depthObject = request.getExtra(Request.DEPTH);
	        if (depthObject == null) {
	            request.putExtra(Request.DEPTH, 0);
	        } else {
	            int depth = (Integer) depthObject;
	            if (depth > task.getSite().getDepth()) {
	                return ;
	            }
	        }
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
			getUrlLock.lock();
			request = queue.poll();
			getUrlCondition.signalAll();
		}finally{
			getUrlLock.unlock();
		}
		return request;

	}

}
