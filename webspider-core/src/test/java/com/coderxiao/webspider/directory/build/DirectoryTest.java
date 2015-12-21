package com.coderxiao.webspider.directory.build;

import com.coderxiao.zookeeper.directory.build.Directory;
import com.coderxiao.zookeeper.directory.webspider.config.ConfigInfo;
import com.coderxiao.zookeeper.directory.webspider.config.mysql.MysqlInfo;
import com.coderxiao.zookeeper.directory.webspider.config.thread.ThreadInfo;
import com.coderxiao.zookeeper.directory.webspider.master.MasterInfo;
import com.coderxiao.zookeeper.directory.webspider.workers.WorkersInfo;
import org.apache.curator.utils.ZKPaths;
import org.apache.zookeeper.data.Stat;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

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
    public void testExists() {
        boolean exists = Directory.exists(MysqlInfo.MYSQL_PATH);
        System.out.println("exists = " + exists);
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

    @Test
    public void testProperties() throws IOException {
        byte[] hello = "hello=77".getBytes();
        Properties properties = new Properties();
        properties.load(new ByteArrayInputStream(hello));
        System.out.println(properties.getProperty("hello"));

        byte[] my = "hello=oo".getBytes();
        properties.load(new ByteArrayInputStream(my));
        System.out.println(properties.getProperty("hello"));
    }

}