package com.coderxiao.admin.monitor;

import com.coderxiao.admin.directory.webspider.config.mysql.MysqlInfo;
import com.coderxiao.admin.directory.webspider.workers.WorkersInfo;
import com.coderxiao.admin.monitor.listener.NodeListener;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * <p>
 *     监听测试
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class MonitorTest {

    @Before
    public void init() throws Exception {

    }

    @After
    public void destroy() throws Exception {

    }

    @Test
    public void testDataChange() {
        Monitor.subscribeDataChanges(MysqlInfo.MYSQL_PATH);
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testChildrenChange() {
        Monitor.subscribeChildChanges(WorkersInfo.WORKERS_PATH);
        while (true) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testChange() {

    }

}