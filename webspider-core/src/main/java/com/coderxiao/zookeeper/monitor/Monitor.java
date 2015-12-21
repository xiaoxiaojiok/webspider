package com.coderxiao.zookeeper.monitor;

import com.coderxiao.webspider.thread.ExecutorServiceInstance;
import com.coderxiao.zookeeper.monitor.listener.NodeListener;
import com.coderxiao.zookeeper.monitor.listener.PathChildrenListener;
import com.coderxiao.zookeeper.util.ZooKeeperUtil;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     zookeeper节点监控类
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class Monitor {

    protected static Logger logger = LoggerFactory.getLogger(Monitor.class);

    /**
     * 监听节点
     * @param path 绝对路径
     */
    public static void subscribeDataChanges(final String path) {
        final NodeCache cache = new NodeCache(ZooKeeperUtil.one().getClient(), path, false);
        try {
            cache.start(true);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //统一线程处理事件
        cache.getListenable().addListener(new NodeListener(cache), ExecutorServiceInstance.getInstance());
    }

    /**
     * 监听子节点
     * @param path 绝对路径
     */
    public static void subscribeChildChanges(final String path) {
        PathChildrenCache cache = new PathChildrenCache(ZooKeeperUtil.one().getClient(), path, true);
        try {
            cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //统一线程处理事件
        cache.getListenable().addListener(new PathChildrenListener(cache), ExecutorServiceInstance.getInstance());
    }


}
