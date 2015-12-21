package com.coderxiao.admin.election;


import com.coderxiao.admin.directory.build.Directory;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;
import com.coderxiao.admin.directory.webspider.master.MasterInfo;
import com.coderxiao.admin.directory.webspider.workers.WorkersInfo;
import com.coderxiao.admin.http.jetty.JettyServer;
import com.coderxiao.admin.monitor.Monitor;
import com.coderxiao.admin.util.ConfigUtil;
import com.coderxiao.admin.util.ExecutorServiceUtil;
import com.coderxiao.admin.util.RedisUtil;
import com.coderxiao.admin.util.ZooKeeperUtil;
import com.coderxiao.webservice.SpiderServer;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.apache.log4j.Logger;

import java.io.Closeable;
import java.io.IOException;

/**
 * Master控制器
 *
 * Created by XiaoJian on 2015/12/15.
 */
public class Master extends LeaderSelectorListenerAdapter implements Closeable{
    private final String name;
    private Logger logger = Logger.getLogger(getClass());
    private final LeaderSelector leaderSelector;

    public Master(final String path, String name) {
        this.name = name;
        leaderSelector = new LeaderSelector(ZooKeeperUtil.one().getClient(), path, ExecutorServiceUtil.one(), this);
        leaderSelector.autoRequeue();
    }

    /**
     * 当前Leader退出
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        logger.info("[Master] " + name + " Exited!");
        leaderSelector.close();
    }

    /**
     * Leader选举开始
     */
    public void start() {
        leaderSelector.start();
    }

    /**
     * 成为Master后主逻辑
     * @param curatorFramework
     * @throws Exception
     */
    @Override
    public void takeLeadership(CuratorFramework curatorFramework) throws Exception {
       logger.info("[Master] " + name + " Starting!");
        if (!Directory.exists(ConfigInfo.CONFIG_PATH)) {
            //创建目录并默认初始化
            Directory.create();
        }

        //设置当前webservice地址
        MasterInfo masterInfo = new MasterInfo();
        masterInfo.setProperty(MasterInfo.MASTER_WEBSERVICE_ADDRESS_KEY, ConfigUtil.one().getWebserviceAddress());
        masterInfo.save();

        //更新Redis的webservice地址
        RedisUtil.one().del(RedisUtil.WEBSERVICE_KEY);
        RedisUtil.one().zadd(RedisUtil.WEBSERVICE_KEY,ConfigUtil.one().getWebserviceAddress());


        //监听workers子节点
        Monitor.subscribeChildChanges(WorkersInfo.WORKERS_PATH);

        //启动webservice和jetty服务
        Thread webservice = new Thread(new Runnable() {
            @Override
            public void run() {
                SpiderServer spiderServer = new SpiderServer();
                spiderServer.start();
                System.out.println("Admin WebService start...");
            }
        });

        Thread jetty = new Thread(new Runnable() {
            @Override
            public void run() {
                JettyServer jettyServer = new JettyServer();
                jettyServer.start();
                System.out.println("Admin Jetty start...");
            }


        });

        webservice.start();
        jetty.start();

        while (true) {

            Thread.sleep(5000);
        }
    }
}
