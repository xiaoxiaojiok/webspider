package com.coderxiao.zookeeper.directory.build;

import com.coderxiao.zookeeper.directory.webspider.RootInfo;
import com.coderxiao.zookeeper.util.ZooKeeperUtil;
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
     * 得到节点数据
     * @param path 绝对路径
     * @return 返回数据
     */
    public static byte[] getData(final String path) {
        byte[] data = null;
        try {
            data = ZooKeeperUtil.one().getClient().getData().forPath(path);
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

}
