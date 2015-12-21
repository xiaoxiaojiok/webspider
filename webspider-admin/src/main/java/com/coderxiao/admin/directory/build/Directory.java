package com.coderxiao.admin.directory.build;


import com.coderxiao.admin.directory.webspider.RootInfo;
import com.coderxiao.admin.directory.webspider.config.mysql.MysqlInfo;
import com.coderxiao.admin.directory.webspider.config.redis.RedisInfo;
import com.coderxiao.admin.directory.webspider.config.thread.ThreadInfo;
import com.coderxiao.admin.directory.webspider.sites.SitesInfo;
import com.coderxiao.admin.directory.webspider.workers.WorkersInfo;
import com.coderxiao.admin.util.ConfigUtil;
import com.coderxiao.admin.util.ZooKeeperUtil;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * <p>
 *     zookeeper树目录结构
 * </p>
 *
 * Created by XiaoJian on 2015/12/18.
 */
public class Directory {

    protected static Logger logger = LoggerFactory.getLogger(Directory.class);

    public static final String SEPARATOR = "/";

    public static class Builder{

        public Builder create(final String path) {
            try {
                ZooKeeperUtil.one().getClient().create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            return this;
        }

        public Builder createConfig() {
            create(MysqlInfo.MYSQL_PATH);
            create(RedisInfo.REDIS_PATH);
            create(ThreadInfo.THREAD_PATH);
            return this;
        }

        public Builder createWorkers() {
            create(WorkersInfo.WORKERS_PATH);
            return this;
        }

        public Builder createSites() {
            create(SitesInfo.SITES_PATH);
            return this;
        }

        public Directory build() {
            return new Directory();
        }
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * 创建某个节点
     * @param path 绝对路径
     * @param mode 节点类型
     */
    public static void create(final String path, CreateMode mode) {
        try {
            ZooKeeperUtil.one().getClient().create().creatingParentsIfNeeded().withMode(mode).forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 删除某个节点
     * @param path 绝对路径
     */
    public static void delete(final String path) {
        try {
            ZooKeeperUtil.one().getClient().delete().guaranteed().forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 创建树形结构并初始化配置信息
     */
    public static void create() {
        newBuilder().createConfig().createSites().createWorkers().build();
        init();
    }

    /**
     *初始化配置信息
     */
    private static void init() {
        MysqlInfo mysqlInfo = new MysqlInfo();
        mysqlInfo.setProperty(MysqlInfo.MYSQL_URL_KEY, ConfigUtil.one().getMysqlUrl());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_USER_KEY,ConfigUtil.one().getMysqlUser());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_PWD_KEY,ConfigUtil.one().getMysqlPwd());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_DRIVER_KEY,ConfigUtil.one().getMysqlDriver());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_TEST_QUERY_KEY,ConfigUtil.one().getPoolTestQuery());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_CONNECTION_TIMEOUT_KEY,ConfigUtil.one().getPoolConnectionTimeout());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_IDLE_TIMEOUT_KEY,ConfigUtil.one().getPoolIdleTimeout());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_MAX_LIFETIME_KEY,ConfigUtil.one().getPoolMaxLifeTime());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_MIN_IDLE_KEY,ConfigUtil.one().getPoolMinIdle());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_MAX_POOL_SIZE_KEY,ConfigUtil.one().getPoolMaxPoolSize());
        mysqlInfo.save();

        RedisInfo redisInfo = new RedisInfo();
        redisInfo.setProperty(RedisInfo.REDIS_IP_KEY,ConfigUtil.one().getRedisIP());
        redisInfo.setProperty(RedisInfo.REDIS_MAX_TOTAL_KEY,""+ConfigUtil.one().getRedisMaxTotal());
        redisInfo.setProperty(RedisInfo.REDIS_MAX_IDLE_KEY,""+ConfigUtil.one().getRedisMaxIdel());
        redisInfo.setProperty(RedisInfo.REDIS_MAX_WAIT_KEY,""+ConfigUtil.one().getRedisMaxWait());
        redisInfo.save();

        ThreadInfo threadInfo = new ThreadInfo();
        threadInfo.setProperty(ThreadInfo.WORKER_THREADS,""+ConfigUtil.one().getWorkerThread());
        threadInfo.setProperty(ThreadInfo.SPIDER_THREAD,""+ConfigUtil.one().getSpiderThread());
        threadInfo.save();
    }

    /**
     * 删除所有节点
     */
    public static void delete() {
        try {
            ZooKeeperUtil.one().getClient().delete().deletingChildrenIfNeeded().forPath(RootInfo.ROOT_PATH);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 判断某个节点是否存在
     * @param path 绝对路径
     * @return
     */
    public static boolean exists(final String path) {
        try {
            Stat stat = ZooKeeperUtil.one().getClient().checkExists().forPath(path);
            return (stat == null) ? false : true;

        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     *设置节点数据
     * @param path 绝对路径
     * @param data 数据
     * @return 返回Stat信息
     */
    public static Stat setData(final String path, byte[] data) {
        Stat stat = null;
        try {
            stat = ZooKeeperUtil.one().getClient().setData().forPath(path, data);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return stat;
    }

    /**
     * 得到节点数据
     * @param path 绝对路径
     * @param stat Stat信息
     * @return 返回数据
     */
    public static byte[] getData(final String path,Stat stat) {
        byte[] data = null;
        try {
            data = ZooKeeperUtil.one().getClient().getData().storingStatIn(stat).forPath(path);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return data;
    }

    /**
     * 得到所有子节点
     * @param path 绝对路径
     * @return 返回子节点的相对路径
     */
    public static List<String> getChildren(final String path) {
        try {
            List<String> strings = ZooKeeperUtil.one().getClient().getChildren().forPath(path);
            return strings;
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return null;
    }

}
