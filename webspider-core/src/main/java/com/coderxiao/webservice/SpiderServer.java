package com.coderxiao.webservice;

import com.coderxiao.webspider.util.ConfigUtil;

import javax.xml.ws.Endpoint;

/**
 * 
 * Spider服务器类
 *
 */
public class SpiderServer {
	
	//得到WebService地址
	private static final String ADDRESS = ConfigUtil.getInstance().getWebserviceAddress();
	
	private Endpoint spiderService;
    
	/**
	 * 发布WebService
	 */
    public void start(){
    	spiderService = Endpoint.publish(ADDRESS, new SpiderServiceImpl());
    }
    
    /**
     * 停止WebService
     */
    public void stop(){
    	if(spiderService != null){
    		spiderService.stop();
    		Worker.getInstance().shutdown();
    	}
    }
}
