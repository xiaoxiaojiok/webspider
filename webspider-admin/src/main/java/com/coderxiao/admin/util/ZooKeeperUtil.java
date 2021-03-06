package com.coderxiao.admin.util;


import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * <p>
 * zookeeper工具类 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 1.0.0
 */
public class ZooKeeperUtil {

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

}
