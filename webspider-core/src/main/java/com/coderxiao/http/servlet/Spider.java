package com.coderxiao.http.servlet;

import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import com.alibaba.fastjson.JSONObject;
import com.coderxiao.webspider.monitor.SpiderMonitor;
import com.coderxiao.webspider.monitor.SpiderStatusMXBean;

@Path("/spider")
public class Spider {
	
	private static Map<Long,SpiderStatusMXBean> spiderStatuses = SpiderMonitor.instance().getSpiderStatuses();
	private static SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@GET
	@Path("/")
	@Produces("application/json;charset=UTF-8")
	public String getAll() {
		Set<Long> ids = spiderStatuses.keySet();
		return JSONObject.toJSONString(ids);
	}
	
	@GET
	@Path("/{id}")
	@Produces("application/json;charset=UTF-8")
	public String getId(@PathParam("id") Long id) {
		JSONObject spider = new JSONObject();
		SpiderStatusMXBean spiderStatusMXBean = spiderStatuses.get(id);
		if(spiderStatusMXBean != null){
			spider.put("name", spiderStatusMXBean.getName());
			spider.put("status", spiderStatusMXBean.getStatus());
			spider.put("threadAlive", spiderStatusMXBean.getThreadAlive());
			spider.put("totalPageCount", spiderStatusMXBean.getTotalPageCount());
			spider.put("leftPageCount", spiderStatusMXBean.getLeftPageCount());
			spider.put("successPageCount", spiderStatusMXBean.getSuccessPageCount());
			spider.put("errorPageCount", spiderStatusMXBean.getErrorPageCount());
			spider.put("errorPages", spiderStatusMXBean.getErrorPages());
			spider.put("startTime", dateformat.format(spiderStatusMXBean.getStartTime()));
			spider.put("pagePerSecond", spiderStatusMXBean.getPagePerSecond());
			spider.put("schedulerName", spiderStatusMXBean.getSchedulerName());
		}
		return JSONObject.toJSONString(spider);
	}
	
	@GET
	@Path("/{id}/{methodName}")
	@Produces("application/json;charset=UTF-8")
	public String get(@PathParam("id") Long id,@PathParam("methodName") String methodName) {
		String result = "";
		SpiderStatusMXBean spiderStatusMXBean = spiderStatuses.get(id);
		if(spiderStatusMXBean != null){
			if("getName".equals(methodName)){
				return spiderStatusMXBean.getName();
			}
			if("getStatus".equals(methodName)){
				return spiderStatusMXBean.getStatus();
			}
			if("getThreadAlive".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getThreadAlive());
			}
			if("getTotalPageCount".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getTotalPageCount());
			}
			if("getLeftPageCount".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getLeftPageCount());
			}
			if("getSuccessPageCount".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getSuccessPageCount());
			}
			if("getErrorPageCount".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getErrorPageCount());
			}
			if("getErrorPages".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getErrorPages());
			}
			if("getStartTime".equals(methodName)){
				return dateformat.format(spiderStatusMXBean.getStartTime());
			}
			if("getPagePerSecond".equals(methodName)){
				return JSONObject.toJSONString(spiderStatusMXBean.getPagePerSecond());
			}
			if("getSchedulerName".equals(methodName)){
				return spiderStatusMXBean.getSchedulerName();
			}
		}
		
		return result;
	}
	
	@POST
	@Path("/{id}/{methodName}")
	@Produces("application/json;charset=UTF-8")
	public String post(@PathParam("id") Long id,@PathParam("methodName") String methodName) {
		String result = "";
		SpiderStatusMXBean spiderStatusMXBean = spiderStatuses.get(id);
		if(spiderStatusMXBean != null){
			if("start".equals(methodName)){
				spiderStatusMXBean.start();
				return JSONObject.toJSONString("start success!");
			}
			if("stop".equals(methodName)){
				spiderStatusMXBean.stop();
				return JSONObject.toJSONString("stop success!");
			}
		}
		
		return result;
	}

}
