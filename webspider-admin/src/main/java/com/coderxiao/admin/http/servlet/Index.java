package com.coderxiao.admin.http.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/***
 * <p>
 * 首页 </br>
 * </p>
 * 
 * GET /
 * GET /api/site
 * GET /api/admin
 * GET /site/{id}/XXX
 * GET /admin/XXX
 * 
 * @author XiaoJian
 *
 */
@Path("/")
public class Index {

	@GET
	@Path("/")
	@Produces("text/html;charset=UTF-8")
	public String index() {
		String result = "<h3><a href=\"/api/site\">/api/site</a></h3><h3><a href=\"/api/admin\">/api/admin</a></h3>";
		return result;
	}

}
