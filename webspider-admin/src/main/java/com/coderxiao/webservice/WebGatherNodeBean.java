package com.coderxiao.webservice;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * 采集点bean
 */
public class WebGatherNodeBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * 自增ID
	 */
	private Long id;
	
	/**
	 * 临时站点名称
	 */
	private String weBSiteName;
	
	/**
	 * 临时站点ID
	 */
	private Long webSiteID;
	
	/**
	 * 采集点名称
	 */
	private String wgnName;
	
	/**
	 * 唯一标识
	 */
	private String wgnUniqueId;
	
	/**
	 * 入口地址
	 */
	private String wgnEntryUrl;
	
	/**
	 * 允许链接格式
	 */
	private String wgnAllowRule;
	
	/**
	 * 禁止链接格式
	 */
	private String wgnDenyRule;
	
	/**
	 * 分页匹配规则
	 */
	private String wgnNextPage;
	
	/**
	 * 采集域
	 */
	private String wgnAllowDomain;
	
	/**
	 * 采集权重
	 */
	private Byte wgnWeight;
	
	/**
	 * 所属类别
	 */
	private Byte wgnType;
	
	/**
	 * 是否有效
	 */
	private Byte wgnSpiderEnable;
	
	/**
	 * 采集延迟时间
	 */
	private Byte wgnDelay;
	
	/**
	 * 是否开启COOKIES
	 */
	private Byte wgnCookiesEnable;
	
	/**
	 * 处理链接
	 */
	private String wgnProcessLinks;
	
	/**
	 * 代理列表
	 */
	private String wgnProxies;
	
	/**
	 * 采集字段配置
	 */
	private ArrayList<Object> wgnConfigItem;
	
	/**
	 * 是否登录
	 */
	private Byte wgnLoginEnable;
	
	/**
	 * 登录入口
	 */
	private String wgnLoginEntry;
	
	/**
	 * 登录账号
	 */
	private String wgnLoginAccount;
	
	/**
	 * 登录密码
	 */
	private String wgnLoginPassword;
	
	/**
	 * 采集深度
	 */
	private Byte wgnDepthLimit;
	
	/**
	 * 采集频率
	 */
	private Integer wgnTimeInterval;
	
	/**
	 * 采集更新时间
	 */
	private Integer wgnTimeRefresh;
	
	/**
	 * 用户代理
	 */
	private String wgnUserAgents;
	
	/**
	 * 是否繁体采集点
	 */
	private Byte wgnTraditional;
	
	/**
	 * 是否过滤掉网页
	 */
	private Byte wgnIsClean;
	
	/**
	 * 蜘蛛任务ID
	 */
	private String wgnJobId;
	
	/**
	 * 更新时间
	 */
	private Date wgnUpdateTime;
	
	/**
	 * 创建时间
	 */
	private Date wgnCreateTime;
	
	/**
	 * 是否删除
	 */
	private Byte wgnDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWeBSiteName() {
		return weBSiteName;
	}

	public void setWeBSiteName(String weBSiteName) {
		this.weBSiteName = weBSiteName;
	}

	public Long getWebSiteID() {
		return webSiteID;
	}

	public void setWebSiteID(Long webSiteID) {
		this.webSiteID = webSiteID;
	}

	public String getWgnName() {
		return wgnName;
	}

	public void setWgnName(String wgnName) {
		this.wgnName = wgnName;
	}

	public String getWgnUniqueId() {
		return wgnUniqueId;
	}

	public void setWgnUniqueId(String wgnUniqueId) {
		this.wgnUniqueId = wgnUniqueId;
	}

	public String getWgnEntryUrl() {
		return wgnEntryUrl;
	}

	public void setWgnEntryUrl(String wgnEntryUrl) {
		this.wgnEntryUrl = wgnEntryUrl;
	}

	public String getWgnAllowRule() {
		return wgnAllowRule;
	}

	public void setWgnAllowRule(String wgnAllowRule) {
		this.wgnAllowRule = wgnAllowRule;
	}

	public String getWgnDenyRule() {
		return wgnDenyRule;
	}

	public void setWgnDenyRule(String wgnDenyRule) {
		this.wgnDenyRule = wgnDenyRule;
	}

	public String getWgnNextPage() {
		return wgnNextPage;
	}

	public void setWgnNextPage(String wgnNextPage) {
		this.wgnNextPage = wgnNextPage;
	}

	public String getWgnAllowDomain() {
		return wgnAllowDomain;
	}

	public void setWgnAllowDomain(String wgnAllowDomain) {
		this.wgnAllowDomain = wgnAllowDomain;
	}

	public Byte getWgnWeight() {
		return wgnWeight;
	}

	public void setWgnWeight(Byte wgnWeight) {
		this.wgnWeight = wgnWeight;
	}

	public Byte getWgnType() {
		return wgnType;
	}

	public void setWgnType(Byte wgnType) {
		this.wgnType = wgnType;
	}

	public Byte getWgnSpiderEnable() {
		return wgnSpiderEnable;
	}

	public void setWgnSpiderEnable(Byte wgnSpiderEnable) {
		this.wgnSpiderEnable = wgnSpiderEnable;
	}

	public Byte getWgnDelay() {
		return wgnDelay;
	}

	public void setWgnDelay(Byte wgnDelay) {
		this.wgnDelay = wgnDelay;
	}

	public Byte getWgnCookiesEnable() {
		return wgnCookiesEnable;
	}

	public void setWgnCookiesEnable(Byte wgnCookiesEnable) {
		this.wgnCookiesEnable = wgnCookiesEnable;
	}

	public String getWgnProcessLinks() {
		return wgnProcessLinks;
	}

	public void setWgnProcessLinks(String wgnProcessLinks) {
		this.wgnProcessLinks = wgnProcessLinks;
	}

	public String getWgnProxies() {
		return wgnProxies;
	}

	public void setWgnProxies(String wgnProxies) {
		this.wgnProxies = wgnProxies;
	}

	public ArrayList<Object> getWgnConfigItem() {
		return wgnConfigItem;
	}

	public void setWgnConfigItem(ArrayList<Object> wgnConfigItem) {
		this.wgnConfigItem = wgnConfigItem;
	}

	public Byte getWgnLoginEnable() {
		return wgnLoginEnable;
	}

	public void setWgnLoginEnable(Byte wgnLoginEnable) {
		this.wgnLoginEnable = wgnLoginEnable;
	}

	public String getWgnLoginEntry() {
		return wgnLoginEntry;
	}

	public void setWgnLoginEntry(String wgnLoginEntry) {
		this.wgnLoginEntry = wgnLoginEntry;
	}

	public String getWgnLoginAccount() {
		return wgnLoginAccount;
	}

	public void setWgnLoginAccount(String wgnLoginAccount) {
		this.wgnLoginAccount = wgnLoginAccount;
	}

	public String getWgnLoginPassword() {
		return wgnLoginPassword;
	}

	public void setWgnLoginPassword(String wgnLoginPassword) {
		this.wgnLoginPassword = wgnLoginPassword;
	}

	public Byte getWgnDepthLimit() {
		return wgnDepthLimit;
	}

	public void setWgnDepthLimit(Byte wgnDepthLimit) {
		this.wgnDepthLimit = wgnDepthLimit;
	}

	public Integer getWgnTimeInterval() {
		return wgnTimeInterval;
	}

	public void setWgnTimeInterval(Integer wgnTimeInterval) {
		this.wgnTimeInterval = wgnTimeInterval;
	}

	public Integer getWgnTimeRefresh() {
		return wgnTimeRefresh;
	}

	public void setWgnTimeRefresh(Integer wgnTimeRefresh) {
		this.wgnTimeRefresh = wgnTimeRefresh;
	}

	public String getWgnUserAgents() {
		return wgnUserAgents;
	}

	public void setWgnUserAgents(String wgnUserAgents) {
		this.wgnUserAgents = wgnUserAgents;
	}

	public Byte getWgnTraditional() {
		return wgnTraditional;
	}

	public void setWgnTraditional(Byte wgnTraditional) {
		this.wgnTraditional = wgnTraditional;
	}

	public Byte getWgnIsClean() {
		return wgnIsClean;
	}

	public void setWgnIsClean(Byte wgnIsClean) {
		this.wgnIsClean = wgnIsClean;
	}

	public String getWgnJobId() {
		return wgnJobId;
	}

	public void setWgnJobId(String wgnJobId) {
		this.wgnJobId = wgnJobId;
	}

	public Date getWgnUpdateTime() {
		return wgnUpdateTime;
	}

	public void setWgnUpdateTime(Date wgnUpdateTime) {
		this.wgnUpdateTime = wgnUpdateTime;
	}

	public Date getWgnCreateTime() {
		return wgnCreateTime;
	}

	public void setWgnCreateTime(Date wgnCreateTime) {
		this.wgnCreateTime = wgnCreateTime;
	}

	public Byte getWgnDelete() {
		return wgnDelete;
	}

	public void setWgnDelete(Byte wgnDelete) {
		this.wgnDelete = wgnDelete;
	}

}
