package com.coderxiao.http.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alibaba.fastjson.JSONObject;
import com.coderxiao.webspider.monitor.SpiderMonitor;
import com.coderxiao.webspider.monitor.WorkerStatusMXBean;

@Path("/worker")
public class Worker {
	
	private static WorkerStatusMXBean workerStatus = SpiderMonitor.instance().getWorkerStatus();

	@GET
	@Path("/")
	@Produces("application/json;charset=UTF-8")
	public String getAll() {
		JSONObject worker = new JSONObject();
		worker.put("webservice", workerStatus.getWebServiceURL());
		worker.put("allwebservices", workerStatus.getAllWebServiceURLs());
		worker.put("allsites", workerStatus.getAllSites());
		worker.put("allwebservicesCount", workerStatus.getAllWebServiceURLsCount());
		worker.put("allsitesCount", workerStatus.getAllSitesCount());
		return JSONObject.toJSONString(worker);
	}
	
	@GET
	@Path("/{methodName}")
	@Produces("application/json;charset=UTF-8")
	public String get(@PathParam("methodName") String methodName) {
		String result = "";
		if("getWebServiceURL".equals(methodName)){
			return workerStatus.getWebServiceURL();
		}
		if("getAllWebServiceURLs".equals(methodName)){
			return JSONObject.toJSONString(workerStatus.getAllWebServiceURLs());
		}
		if("getAllSites".equals(methodName)){
			return JSONObject.toJSONString(workerStatus.getAllSites());
		}
		if("getAllWebServiceURLsCount".equals(methodName)){
			return JSONObject.toJSONString(workerStatus.getAllWebServiceURLsCount());
		}
		if("getAllSitesCount".equals(methodName)){
			return JSONObject.toJSONString(workerStatus.getAllSitesCount());
		}
		return result;
	}

}
