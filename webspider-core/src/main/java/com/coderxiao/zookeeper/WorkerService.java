package com.coderxiao.zookeeper;

import com.coderxiao.webservice.Worker;
import com.coderxiao.webspider.Spider;
import com.coderxiao.webspider.processor.ModelPageProcessor;
import com.coderxiao.zookeeper.directory.webspider.sites.SiteInfo;

/**
 * <p>
 *     Worker服务类
 * </p>
 * Created by XiaoJian on 2015/12/20.
 */
public class WorkerService {

    public static boolean add(SiteInfo site, Long siteID) {
        if(site == null || site.getProperty(SiteInfo.SITE_ALLOW_URL)==null || site.getProperty(SiteInfo.SITE_START_URL)==null){
            return false;
        }
        if(Worker.getInstance().getSpider(siteID) != null){
            return false;
        }
        String[] allowRules = site.getProperty(SiteInfo.SITE_ALLOW_URL).trim().split("\\s+");
        String[] startUrls = site.getProperty(SiteInfo.SITE_START_URL).trim().split("\\s+");
        Spider spider = Spider.create(new ModelPageProcessor());
        spider.getSite().setDepth(Integer.parseInt(site.getProperty(SiteInfo.SITE_DEPTH_LIMIT)))
                .setAllowRules(allowRules).setId(siteID);;
        spider.addUrl(startUrls);
        Worker.getInstance().addSpider(spider);
        return true;
    }

    public static boolean delete(Long siteID) {
        Spider spider = Worker.getInstance().getSpider(siteID);
        if(spider == null){
            return false;
        }
        Worker.getInstance().delSpider(spider);
        return true;
    }

    public static boolean disable(Long siteID) {
        Spider spider = Worker.getInstance().getSpider(siteID);
        if(spider == null){
            return false;
        }
        if(spider.getStatus() == Spider.Status.Running){
            spider.stop();
        }
        return true;
    }

    public static boolean enable(SiteInfo site,Long siteID) {
        Spider spider = Worker.getInstance().getSpider(siteID);
        if(spider == null){
            add(site, siteID);
        }
        if((spider.getStatus() == Spider.Status.Init) || (spider.getStatus() == Spider.Status.Stopped)){
            spider.start();
        }
        return true;
    }

    public static boolean update(SiteInfo site,Long siteID) {
        if(site == null || site.getProperty(SiteInfo.SITE_START_URL)==null || site.getProperty(SiteInfo.SITE_ALLOW_URL)==null){
            return false;
        }
        Spider spider = Worker.getInstance().getSpider(siteID);
        if(spider == null){
            add(site, siteID);
        }
        Worker.getInstance().updateSpider(spider);
        String[] allowRules = site.getProperty(SiteInfo.SITE_ALLOW_URL).trim().split("\\s+");
        String[] startUrls = site.getProperty(SiteInfo.SITE_START_URL).trim().split("\\s+");
        spider.getSite().setDepth(Integer.parseInt(site.getProperty(SiteInfo.SITE_DEPTH_LIMIT))).setAllowRules(allowRules);
        spider.addUrl(startUrls);
        return true;
    }


}
