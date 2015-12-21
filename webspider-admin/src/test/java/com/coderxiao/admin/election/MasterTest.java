package com.coderxiao.admin.election;

import com.coderxiao.admin.directory.webspider.master.MasterInfo;
import com.coderxiao.admin.util.IPUtils;
import com.sun.org.apache.xpath.internal.SourceTree;
import org.junit.Test;

import java.io.IOException;
import java.lang.management.ManagementFactory;

import static org.junit.Assert.*;

/**
 * <p>
 *     Master选举测试
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class MasterTest {

    @Test
    public void testSelector() throws IOException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        Master master = new Master(MasterInfo.MASTER_PATH, IPUtils.getRealIp() + "#pid=" + pid);
        master.start();
        System.in.read();
    }

}