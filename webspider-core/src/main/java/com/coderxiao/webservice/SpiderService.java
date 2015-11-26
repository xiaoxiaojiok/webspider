package com.coderxiao.webservice;

import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 * WebService接口
 */
@WebService()
public interface SpiderService {
	/**
	 * 增加一个采集点
	 * 
	 * @param site
	 * @return
	 */
	@WebMethod
	public boolean add(WebGatherNodeBean site);

	/**
	 * 删除某个采集点
	 * 
	 * @param siteID
	 * @return
	 */
	@WebMethod
	public boolean delete(Long siteID);

	/**
	 * 停止某个采集点
	 * 
	 * @param siteID
	 * @return
	 */
	@WebMethod
	public boolean disable(Long siteID);

	/**
	 * 启动某个采集点
	 * 
	 * @param siteID
	 * @return
	 */
	@WebMethod
	public boolean enable(Long siteID);

	/**
	 * 获取某个采集点的信息
	 * 
	 * @param siteID
	 * @return
	 */
	@WebMethod
	public SpiderStatus status(Long siteID);

	/**
	 * 更新某个采集点中的配置信息
	 * 
	 * @param site
	 * @return
	 */
	@WebMethod
	public boolean update(WebGatherNodeBean site);
}