package com.coderxiao.webspider.scheduler;

import java.util.Comparator;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.http.annotation.ThreadSafe;

import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;
import com.coderxiao.webspider.util.NumberUtils;

/**
 * 基于内存队列的权重优先URL队列调度器<br>
 * Store urls to fetch in LinkedBlockingQueue and remove duplicate urls by
 * HashMap.
 *
 * @author xiaoxiaojiok@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class PriorityWorkerScheduler extends DuplicateRemovedScheduler implements
		MonitorableScheduler {

	private static final String UUID ="uuid";
	
    public static final int INITIAL_CAPACITY = 5;

    private BlockingQueue<Request> noPriorityQueue = new LinkedBlockingQueue<Request>();

    private PriorityBlockingQueue<Request> priorityQueuePlus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -NumberUtils.compareLong(o1.getPriority(), o2.getPriority());
        }
    });

    private PriorityBlockingQueue<Request> priorityQueueMinus = new PriorityBlockingQueue<Request>(INITIAL_CAPACITY, new Comparator<Request>() {
        @Override
        public int compare(Request o1, Request o2) {
            return -NumberUtils.compareLong(o1.getPriority(), o2.getPriority());
        }
    });
	
    private ReentrantLock getUrlLock = new ReentrantLock();

    private Condition getUrlCondition = getUrlLock.newCondition();

	@Override
	public void pushWhenNoDuplicate(Request request, Task task) {
		request.putExtra(UUID, task.getUUID());
		  if (task.getSite().getPriority() == 0) {
	          noPriorityQueue.add(request);
	      } else if (task.getSite().getPriority() > 0) {
	          priorityQueuePlus.put(request);
	      } else {
	          priorityQueueMinus.put(request);
	      }
	}
	

	@Override
	public Request poll(Task task) {
		BlockingQueue<Request> queue;
		if (task.getSite().getPriority() == 0) {
			queue = noPriorityQueue;
	      } else if (task.getSite().getPriority() > 0) {
	    	  queue = priorityQueuePlus;
	      } else {
	    	  queue = priorityQueueMinus;
	      }
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
		Request poll = null;
		try{
			getUrlLock.lock();
			poll = queue.poll();
	        /*if (poll == null) {
	        	 poll = noPriorityQueue.poll();
	        	 if(poll == null){
	        		 poll = priorityQueueMinus.poll();
	        	 }
	        }*/
			getUrlCondition.signalAll();
		}finally{
			getUrlLock.unlock();
		}
		return poll;

	}

	@Override
	public long getLeftRequestsCount(Task task) {
		//如果要做监控，需要改变这个方法，可以在通过另外一个类封装BlockingQueue，在类里面对不同的Task进行原子统计
		return noPriorityQueue.size();
	}

	@Override
	public long getTotalRequestsCount(Task task) {
		//如果要做监控，需要改变这个方法，可以在通过另外一个类封装BlockingQueue，在类里面对不同的Task进行原子统计
		return getDuplicateRemover().getTotalRequestsCount(task);
	}
}
