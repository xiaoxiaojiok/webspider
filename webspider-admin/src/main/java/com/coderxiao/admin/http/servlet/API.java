package com.coderxiao.admin.http.servlet;

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
	
	private static final String SITE="site";
	private static final String ADMIN="admin";
	private static final String SITE_API="<h3>获取所有站点：<a href=\"../../site\">GET /site</a></h3>"
			+ "<h3>获取站点信息：<a href=\"../../site/1\">GET /site/{id}</a></h3>"
			+ "<h3>获取站点名称：<a href=\"../../site/1/getName\">GET /site/{id}/getName</a></h3>"
			+ "<h3>停止站点：POST /site/{id}/stop</h3>"
			+ "<h3>启动站点：POST /site/{id}/start</h3>";
	private static final String ADMIN_API ="<h3>获取当前Admin信息：<a href=\"../../admin\">GET /admin</a></h3>"
			+ "<h3>获取当前admin的webservice地址：<a href=\"../../admin/getWebServiceURL\">GET /admin/getWebServiceURL</a></h3>"
			+ "<h3>获取所有admin的webservice地址列表：<a href=\"../../admin/getAllWebServiceURLs\">GET /admin/getAllWebServiceURLs</a></h3>"
			+ "<h3>获取所有爬取的站点列表：<a href=\"../../admin/getAllSites\">GET /admin/getAllSites</a></h3>"
			+ "<h3>获取所有admin的webservice地址数目：<a href=\"../../admin/getAllWebServiceURLsCount\">GET /admin/getAllWebServiceURLsCount</a></h3>"
			+ "<h3>获取所有爬取的站点数目：<a href=\"../../admin/getAllSitesCount\">GET /admin/getAllSitesCount</a></h3>";

	@GET
	@Path("/")
	@Produces("text/html;charset=UTF-8")
	public String api() {
		String result = "<h3><a href=\"/api/site\">/api/site</a></h3><h3><a href=\"/api/admin\">/api/admin</a></h3>";
		return result;
	}
	
	@GET
	@Path("/{name}")
	@Produces("text/html;charset=UTF-8")
	public String api(@PathParam("name") String name) {
		if(SITE.equalsIgnoreCase(name)){
			return SITE_API;
		}
		if(ADMIN.equalsIgnoreCase(name)){
			return ADMIN_API;
		}
		return "";
	}
}
