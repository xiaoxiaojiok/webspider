package com.coderxiao.webspider.monitor;

import java.util.Set;

public interface WorkerStatusMXBean {

	/***
	 * 获取当前Worker的webservice地址
	 * @return
	 */
    public String getWebServiceURL();
    
    /***
     * 获取所有Worker的webservice地址列表
     * @return
     */
    public Set<String> getAllWebServiceURLs();
    
    /***
     * 获取所有爬取的站点列表
     * @return
     */
    public Set<String> getAllSites();
    
    /***
     * 获取所有Worker的webservice地址数目
     * @return
     */
    public long getAllWebServiceURLsCount();
    
    /***
     * 获取所有爬取的站点数目
     * @return
     */
    public long getAllSitesCount();

}
