package com.coderxiao;

import com.coderxiao.http.jetty.JettyServer;
import com.coderxiao.webspider.util.IPUtils;
import com.coderxiao.zookeeper.WorkerService;
import com.coderxiao.zookeeper.directory.build.Directory;
import com.coderxiao.zookeeper.directory.webspider.sites.SiteInfo;
import com.coderxiao.zookeeper.directory.webspider.sites.SitesInfo;
import com.coderxiao.zookeeper.directory.webspider.workers.WorkerInfo;
import com.coderxiao.zookeeper.directory.webspider.workers.WorkersInfo;
import com.coderxiao.zookeeper.monitor.Monitor;

import java.util.List;

/**
 * Worker主程序
 */
public class WorkerMain {

    private static final String BASE_PATH = SitesInfo.SITES_PATH + Directory.SEPARATOR;

    public static void main(String[] args) {

        //worker上线
        WorkerInfo workerinfo = new WorkerInfo(WorkersInfo.WORKERS_PATH + Directory.SEPARATOR + IPUtils.getRealIp() + "-");
        workerinfo.create();

        //初始化，为当前worker分配站点
        List<String> sites = Directory.getChildren(SitesInfo.SITES_PATH);
        if (sites != null) {
            for (String site : sites) {
                SiteInfo siteInfo = new SiteInfo(BASE_PATH + site);
                siteInfo.load();
                //TODO
                //默认一开始爬取所有，应该按照siteInfo的OPERATOR来决定,只有enable和update的才enable
                WorkerService.add(siteInfo, Long.parseLong(site));
                WorkerService.enable(siteInfo,Long.parseLong(site));
            }
        }

        //继续监听sites子节点
        Monitor.subscribeChildChanges(SitesInfo.SITES_PATH);

        //启动jetty
        Thread jetty = new Thread(new Runnable() {
            @Override
            public void run() {
                JettyServer jettyServer = new JettyServer();
                jettyServer.start();
                System.out.println("Worker Jetty start...");
            }


        });
        jetty.start();

    }
}
