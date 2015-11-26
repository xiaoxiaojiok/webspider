package com.coderxiao.webspider.monitor;

import java.util.Date;
import java.util.List;

public interface SpiderStatusMXBean {

	/***
	 * 获取爬虫名称
	 * @return
	 */
    public String getName();

    /***
     * 获取爬虫状态
     * @return
     */
    public String getStatus();

    /***
     * 获取爬虫线程池存活数
     * @return
     */
    public int getThreadAlive();

    /***
     * 获取爬虫总页面数
     * @return
     */
    public long getTotalPageCount();

    /***
     * 获取剩余页面数
     * @return
     */
    public long getLeftPageCount();

    /***
     * 获取爬取成功页面数
     * @return
     */
    public long getSuccessPageCount();

    /***
     * 获取爬取失败页面数
     * @return
     */
    public long getErrorPageCount();

    /***
     * 获取失败页面列表
     * @return
     */
    public List<String> getErrorPages();

    /***
     * 开启爬虫
     */
    public void start();

    /***
     * 停止爬虫
     */
    public void stop();

    /***
     * 获取爬虫开始时间
     * @return
     */
    public Date getStartTime();

    /***
     * 获取每秒爬取页面数
     * @return
     */
    public int getPagePerSecond();
    
    /***
     * 获取调度器名称
     * @return
     */
    public String getSchedulerName();
}
