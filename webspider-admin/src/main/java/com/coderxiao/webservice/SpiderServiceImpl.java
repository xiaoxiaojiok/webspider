package com.coderxiao.webservice;

import com.coderxiao.admin.directory.build.Directory;
import com.coderxiao.admin.directory.webspider.sites.SiteInfo;
import com.coderxiao.admin.directory.webspider.sites.SitesInfo;

import javax.jws.WebService;

/**
 * WebService接口实现类
 */
@WebService(endpointInterface="com.coderxiao.webservice.SpiderService")
public class SpiderServiceImpl implements SpiderService{

	private static final String BASE_PATH = SitesInfo.SITES_PATH + Directory.SEPARATOR ;

	@Override
	public boolean add(WebGatherNodeBean site) {
		if(site == null || site.getWgnAllowRule()==null || site.getWgnEntryUrl()==null){
			return false;
		}
		if(Directory.exists( BASE_PATH +site.getId())){
			return false;
		}
		SiteInfo siteInfo = new SiteInfo(BASE_PATH + site.getId());
		siteInfo.create();
		siteInfo.setProperty(SiteInfo.SITE_ALLOW_URL,site.getWgnAllowRule());
		siteInfo.setProperty(SiteInfo.SITE_START_URL,site.getWgnEntryUrl());
		siteInfo.setProperty(SiteInfo.SITE_DEPTH_LIMIT,""+site.getWgnDepthLimit());
		siteInfo.setProperty(SiteInfo.SITE_OPERATOR,SiteInfo.OP_ADD);
		siteInfo.save();

		return true;
	}

	@Override
	public boolean delete(Long siteID) {
		if(!Directory.exists( BASE_PATH + siteID)){
			return false;
		}
		Directory.delete(BASE_PATH + siteID);
		return true;
	}

	@Override
	public boolean disable(Long siteID) {
		SiteInfo siteInfo = new SiteInfo(BASE_PATH + siteID);
		if (!siteInfo.exists()) {
			return false;
		}
		siteInfo.load();
		siteInfo.setProperty(SiteInfo.SITE_OPERATOR,SiteInfo.OP_DISABLE);
		siteInfo.save();
		return true;
	}

	@Override
	public boolean enable(Long siteID) {
		SiteInfo siteInfo = new SiteInfo(BASE_PATH + siteID);
		if (!siteInfo.exists()) {
			return false;
		}
		siteInfo.load();
		siteInfo.setProperty(SiteInfo.SITE_OPERATOR,SiteInfo.OP_ENABLE);
		siteInfo.save();
		return true;
	}

	@Override
	public SpiderStatus status(Long siteID) {
		SiteInfo siteInfo = new SiteInfo(BASE_PATH + siteID);
		if (!siteInfo.exists()) {
			return SpiderStatus.notexist;
		}
		//TODO
		//应该检测sites下的节点operator字段
		return SpiderStatus.running;
	}

	@Override
	public boolean update(WebGatherNodeBean site) {
		if(site == null || site.getWgnAllowRule()==null || site.getWgnEntryUrl()==null){
			return false;
		}
		if(!Directory.exists( BASE_PATH +site.getId())){
			return false;
		}
		SiteInfo siteInfo = new SiteInfo(BASE_PATH + site.getId());
		siteInfo.load();
		siteInfo.setProperty(SiteInfo.SITE_ALLOW_URL,site.getWgnAllowRule());
		siteInfo.setProperty(SiteInfo.SITE_DEPTH_LIMIT,""+site.getWgnDepthLimit());
		siteInfo.setProperty(SiteInfo.SITE_START_URL,site.getWgnEntryUrl());
		siteInfo.setProperty(SiteInfo.SITE_OPERATOR,SiteInfo.OP_UPDATE);
		siteInfo.save();

		return true;
	}
}
