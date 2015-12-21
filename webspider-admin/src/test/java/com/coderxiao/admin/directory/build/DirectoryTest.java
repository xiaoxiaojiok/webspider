package com.coderxiao.admin.directory.build;

import com.coderxiao.admin.directory.webspider.config.ConfigInfo;
import com.coderxiao.admin.directory.webspider.config.mysql.MysqlInfo;
import com.coderxiao.admin.directory.webspider.config.thread.ThreadInfo;
import com.coderxiao.admin.directory.webspider.master.MasterInfo;
import com.coderxiao.admin.directory.webspider.workers.WorkersInfo;
import com.coderxiao.admin.util.ConfigUtil;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * <p>
 *     zookeeper目录测试类
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class DirectoryTest {

    @Before
    public void init() throws Exception {

    }

    @After
    public void destroy() throws Exception {

    }

    @Test
    public void testCreate() {
        Directory.create();
    }

    @Test
    public void testDelete() {
        Directory.delete();
    }

    @Test
    public void testExists() {
        boolean exists = Directory.exists(MysqlInfo.MYSQL_PATH);
        System.out.println("exists = " + exists);
    }

    @Test
    public void testSetData() {
        MysqlInfo mysqlInfo = new MysqlInfo();
        mysqlInfo.setProperty(MysqlInfo.MYSQL_PWD_KEY, ConfigUtil.one().getMysqlPwd());
        mysqlInfo.setProperty(MysqlInfo.MYSQL_URL_KEY,ConfigUtil.one().getMysqlUrl());
        Stat stat = mysqlInfo.save();
        System.out.println("stat = " + stat);
    }

    @Test
    public void testGetData(){
        MysqlInfo mysqlInfo = new MysqlInfo();
        Stat stat = mysqlInfo.load();
        String url = mysqlInfo.getProperty(MysqlInfo.MYSQL_URL_KEY);
        System.out.println("jdbcUrl = " + url);
        String pwd = mysqlInfo.getProperty(MysqlInfo.MYSQL_PWD_KEY);
        System.out.println("pwd = " + pwd);
        System.out.println("stat = " + stat);
        System.out.println("--------------");
        mysqlInfo.print();
    }

    @Test
    public void testGetMaster() {
        List<String> children = Directory.getChildren(MasterInfo.MASTER_PATH);
        System.out.println("children = " + children);
        MasterInfo masterInfo = new MasterInfo();
        masterInfo.load();
        masterInfo.print();
    }

    @Test
    public void testChildren() {
        List<String> children = Directory.getChildren(ConfigInfo.CONFIG_PATH);
        System.out.println("children = " + children);
    }

    @Test
    public void testCreate1() {
        Directory.delete(WorkersInfo.WORKERS_PATH + "/host-2");
//        Directory.setData(WorkersInfo.WORKERS_PATH + "/host-2","admin=123456".getBytes());

    }

    @Test
    public void testZKPaths() {
        System.out.println(ZKPaths.getNodeFromPath("/root/config/mysql"));
        ZKPaths.PathAndNode pathAndNode = ZKPaths.getPathAndNode("/root/config/mysql");
        System.out.println(pathAndNode.getPath());
        System.out.println(pathAndNode.getNode());
    }

    @Test
    public void testGetWorkerThread(){
        ThreadInfo threadInfo = new ThreadInfo();
        threadInfo.load();
        String spiderThread = threadInfo.getProperty(ThreadInfo.SPIDER_THREAD);
        int a =Integer.parseInt(spiderThread);
        System.out.println(a);

    }


}