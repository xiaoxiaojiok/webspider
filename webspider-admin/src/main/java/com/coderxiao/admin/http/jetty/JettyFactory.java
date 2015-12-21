package com.coderxiao.admin.http.jetty;

import com.sun.jersey.spi.container.servlet.ServletContainer;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class JettyFactory {

	private static final String PACKAGE = "com.coderxiao.admin.http.servlet";

	/**
	 * 创建Jetty Server
	 * 
	 * @throws Exception
	 */
	public static Server createServer(String ip, int port, String contextPath) throws Exception {
		Server server = new Server();
		
        ServerConnector http = new ServerConnector(server);
        http.setHost(ip);
        http.setPort(port);
        http.setIdleTimeout(30000);
        
        server.addConnector(http);
        
		ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
		context.setContextPath(contextPath);
		ServletHolder sh = new ServletHolder(ServletContainer.class);
		sh.setInitParameter("com.sun.jersey.config.property.resourceConfigClass","com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages", PACKAGE);
		context.addServlet(sh, "/*");

		server.setHandler(context);

		return server;
	}
}