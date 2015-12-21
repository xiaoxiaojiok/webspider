package com.coderxiao.webservice;

import com.coderxiao.admin.util.ConfigUtil;

import javax.xml.ws.Endpoint;

/**
 * 
 * Webservice服务器类
 *
 */
public class SpiderServer {
	
	//得到WebService地址
	private static final String ADDRESS = ConfigUtil.one().getWebserviceAddress();
	
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
    	}
    }
}
