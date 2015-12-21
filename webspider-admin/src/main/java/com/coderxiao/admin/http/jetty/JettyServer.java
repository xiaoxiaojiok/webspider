package com.coderxiao.admin.http.jetty;


import com.coderxiao.admin.util.ConfigUtil;
import org.eclipse.jetty.server.Server;

/***
 * Jetty服务器
 * 
 * @author XiaoJian
 *
 */
public class JettyServer {

	public static final String IP = ConfigUtil.one().getJettyIP();
	public static final int PORT = ConfigUtil.one().getJettyPort();
	public static final String CONTEXT = ConfigUtil.one().getJettyContext();
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