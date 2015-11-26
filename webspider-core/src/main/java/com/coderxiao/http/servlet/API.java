package com.coderxiao.http.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/***
 * <p>
 * 监控API<br/>
 * </p>
 * 
 * @author XiaoJian
 *
 */
@Path("/api")
public class API {
	
	private static final String SPIDER="spider";
	private static final String WORDER="worker";
	private static final String SPIDER_API="<h3>获取所有爬虫：<a href=\"../../spider\">GET /spider</a></h3>"
			+ "<h3>获取爬虫信息：<a href=\"../../spider/1\">GET /spider/{id}</a></h3>"
			+ "<h3>获取爬虫名称：<a href=\"../../spider/1/getName\">GET /spider/{id}/getName</a></h3>"
			+ "<h3>获取爬虫状态：<a href=\"../../spider/1/getStatus\">GET /spider/{id}/getStatus</a></h3>"
			+ "<h3>获取爬虫线程池存活数：<a href=\"../../spider/1/getThreadAlive\">GET /spider/{id}/getThreadAlive</a></h3>"
			+ "<h3>获取爬虫总页面数：<a href=\"../../spider/1/getTotalPageCount\">GET /spider/{id}/getTotalPageCount</a></h3>"
			+ "<h3>获取剩余页面数：<a href=\"../../spider/1/getLeftPageCount\">GET /spider/{id}/getLeftPageCount</a></h3>"
			+ "<h3>获取爬取成功页面数：<a href=\"../../spider/1/getSuccessPageCount\">GET /spider/{id}/getSuccessPageCount</a></h3>"
			+ "<h3>获取爬取失败页面数：<a href=\"../../spider/1/getErrorPageCount\">GET /spider/{id}/getErrorPageCount</a></h3>"
			+ "<h3>获取失败页面列表：<a href=\"../../spider/1/getErrorPages\">GET /spider/{id}/getErrorPages</a></h3>"
			+ "<h3>获取爬虫开始时间：<a href=\"../../spider/1/getStartTime\">GET /spider/{id}/getStartTime</a></h3>"
			+ "<h3>获取每秒爬取页面数：<a href=\"../../spider/1/getPagePerSecond\">GET /spider/{id}/getPagePerSecond</a></h3>"
			+ "<h3>获取调度器名称：<a href=\"../../spider/1/getSchedulerName\">GET /spider/{id}/getSchedulerName</a></h3>"
			+ "<h3>停止爬虫：POST /spider/{id}/stop</h3>"
			+ "<h3>启动爬虫：POST /spider/{id}/start</h3>";
	private static final String WORDER_API="<h3>获取当前Worker信息：<a href=\"../../worker\">GET /worker</a></h3>"
			+ "<h3>获取当前Worker的webservice地址：<a href=\"../../worker/getWebServiceURL\">GET /worker/getWebServiceURL</a></h3>"
			+ "<h3>获取所有Worker的webservice地址列表：<a href=\"../../worker/getAllWebServiceURLs\">GET /worker/getAllWebServiceURLs</a></h3>"
			+ "<h3>获取所有爬取的站点列表：<a href=\"../../worker/getAllSites\">GET /worker/getAllSites</a></h3>"
			+ "<h3>获取所有Worker的webservice地址数目：<a href=\"../../worker/getAllWebServiceURLsCount\">GET /worker/getAllWebServiceURLsCount</a></h3>"
			+ "<h3>获取所有爬取的站点数目：<a href=\"../../worker/getAllSitesCount\">GET /worker/getAllSitesCount</a></h3>";

	@GET
	@Path("/")
	@Produces("text/html;charset=UTF-8")
	public String api() {
		String result = "<h3><a href=\"/api/spider\">/api/spider</a></h3><h3><a href=\"/api/worker\">/api/worker</a></h3>";
		return result;
	}
	
	@GET
	@Path("/{name}")
	@Produces("text/html;charset=UTF-8")
	public String api(@PathParam("name") String name) {
		if(SPIDER.equalsIgnoreCase(name)){
			return SPIDER_API;
		}
		if(WORDER.equalsIgnoreCase(name)){
			return WORDER_API;
		}
		return "";
	}
}
