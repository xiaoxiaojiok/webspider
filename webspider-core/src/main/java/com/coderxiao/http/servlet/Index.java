package com.coderxiao.http.servlet;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/***
 * <p>
 * 首页 </br>
 * </p>
 * 
 * GET /
 * GET /api/spider 
 * GET /api/worker 
 * GET /spider/{id}/XXX
 * GET /worker/XXX
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
		String result = "<h3><a href=\"/api/spider\">/api/spider</a></h3><h3><a href=\"/api/worker\">/api/worker</a></h3>";
		return result;
	}

}
