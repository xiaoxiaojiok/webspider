package com.coderxiao.util;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 * zookeeper工具类 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 1.0.0
 */
public class ZooKeeperUtil {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * Zookeeper连接
     */
    private static CuratorFramework client;

    /**
     * zookeeper地址
     */
    private static String connectString;

    /**
     * 会话超时时间
     */
    private static int sessionTimeoutMs;

    /**
     * 连接超时时间
     */
    private static int connectionTimeouotMs = 10000;

    /**
     * 重试策略
     */
    private static RetryPolicy retryPolicy;

    /**
     * 初始sleep时间
     */
    private static final int baseSleepTimeMs = 1000;

    /**
     * 最大重试次数
     */
    private static final int maxRetries = 5;

    static{
        connectString = ConfigUtil.one().getZkAddress();
        sessionTimeoutMs = ConfigUtil.one().getZkTimeout();
        retryPolicy = new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries);
        client = CuratorFrameworkFactory.newClient(connectString, sessionTimeoutMs, connectionTimeouotMs, retryPolicy);
        client.start();
    }

    private ZooKeeperUtil() {

    }

    private static class ZooKeeperUtilGet{
        private static final ZooKeeperUtil instance= new ZooKeeperUtil();
    }

    public static ZooKeeperUtil one() {
        return ZooKeeperUtilGet.instance;
    }

    public CuratorFramework getClient(){
        return client;
    }

    /**
     * 得到所有子节点
     * @param path 绝对路径
     * @return 返回子节点的相对路径
     */
    public List<String> getChildren(final String path) {
        try {
            List<String> strings = ZooKeeperUtil.one().getClient().getChildren().forPath(path);
            return strings;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
