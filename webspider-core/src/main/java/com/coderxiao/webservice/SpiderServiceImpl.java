package com.coderxiao.webservice;

import javax.jws.WebService;

import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.processor.ModelPageProcessor;

/**
 * WebService接口实现类
 */
@WebService(endpointInterface="com.coderxiao.webservice.SpiderService")
public class SpiderServiceImpl implements SpiderService{

	@Override
	public boolean add(WebGatherNodeBean site) {
		if(site == null || site.getWgnAllowRule()==null || site.getWgnEntryUrl()==null){
			return false;
		}
		if(Worker.getInstance().getSpider(site.getId()) != null){
			return false;
		}
		String[] allowRules = site.getWgnAllowRule().trim().split("\\s+");
		String[] startUrls = site.getWgnEntryUrl().trim().split("\\s+");
		Spider spider = Spider.create(new ModelPageProcessor());
		spider.getSite().setDepth(site.getWgnDepthLimit()).setAllowRules(allowRules).setId(site.getId());;
		spider.addUrl(startUrls);
		Worker.getInstance().addSpider(spider);
		return true;
	}

	@Override
	public boolean delete(Long siteID) {
		Spider spider = Worker.getInstance().getSpider(siteID);
		if(spider == null){
			return false;
		}
		Worker.getInstance().delSpider(spider);
		return true;
	}

	@Override
	public boolean disable(Long siteID) {
		Spider spider = Worker.getInstance().getSpider(siteID);
		if(spider == null){
			return false;
		}
		if(spider.getStatus() == Spider.Status.Running){
			spider.stop();
		}
		return true;
	}

	@Override
	public boolean enable(Long siteID) {
		Spider spider = Worker.getInstance().getSpider(siteID);
		if(spider == null){
			return false;
		}
		if((spider.getStatus() == Spider.Status.Init) || (spider.getStatus() == Spider.Status.Stopped)){
			spider.start();
		}
		return true;
	}

	@Override
	public SpiderStatus status(Long siteID) {
		Spider spider = Worker.getInstance().getSpider(siteID);
		if(spider == null){
			return SpiderStatus.notexist;
		}
		if(spider.getStatus() == Spider.Status.Stopped){
			return SpiderStatus.disabled;
		}
		if(spider.getStatus() == Spider.Status.Running){
			return SpiderStatus.running;
		}
		return SpiderStatus.enabled;
	}

	@Override
	public boolean update(WebGatherNodeBean site) {
		if(site == null || site.getWgnAllowRule()==null || site.getWgnEntryUrl()==null){
			return false;
		}
		Spider spider = Worker.getInstance().getSpider(site.getId());
		if(spider == null){
			return false;
		}
		Worker.getInstance().updateSpider(spider);
		String[] allowRules = site.getWgnAllowRule().trim().split("\\s+");
		String[] startUrls = site.getWgnEntryUrl().trim().split("\\s+");
		spider.getSite().setDepth(site.getWgnDepthLimit()).setAllowRules(allowRules);
		spider.addUrl(startUrls);
		return true;
	}
}
