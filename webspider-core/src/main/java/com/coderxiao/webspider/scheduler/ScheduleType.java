
package com.coderxiao.webspider.scheduler;

/***
 * 
 * @author XiaoJian
 *
 */
public enum ScheduleType {
	Worker("Worker"), 
	DepthWorker("DepthWorker"),
	Priority("Priority"), 
	PriorityWorker("PriorityWorker"), 
	Queue("Queue"),
	Redis("Redis");

	private final String value;

	private ScheduleType(final String value) {
		this.value = value;
	}

	public String value() {
		return this.value;
	}

}
