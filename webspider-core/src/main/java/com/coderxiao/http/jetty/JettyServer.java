package com.coderxiao.http.jetty;

import org.eclipse.jetty.server.Server;

import com.coderxiao.webspider.util.ConfigUtilInstance;

/***
 * Jetty服务器
 * 
 * @author XiaoJian
 *
 */
public class JettyServer {

	public static final String IP = ConfigUtilInstance.getInstance().getJettyIP();
	public static final int PORT = ConfigUtilInstance.getInstance().getJettyPort();
	public static final String CONTEXT = ConfigUtilInstance.getInstance().getJettyContext();
	public Server server = null;

	/**
	 * 启动jetty服务
	 * @throws Exception 
	 */
	public void start() {
		if(server != null) return;
		try {
			server = JettyFactory.createServer(IP, PORT, CONTEXT);
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 停止jetty服务
	 * @throws Exception 
	 */
	public void stop(){
		try {
			if(server != null){
				server.stop();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}