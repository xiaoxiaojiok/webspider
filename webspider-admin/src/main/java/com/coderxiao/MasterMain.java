package com.coderxiao;

import com.coderxiao.admin.directory.webspider.master.MasterInfo;
import com.coderxiao.admin.election.Master;
import com.coderxiao.admin.util.IPUtils;

import java.lang.management.ManagementFactory;

/**
 * 主函数
 *
 * Created by XiaoJian on 2015/12/18.
 */
public class MasterMain {

    public static void main(String[] args) {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        String pid = name.split("@")[0];
        Master master = new Master(MasterInfo.MASTER_PATH, IPUtils.getRealIp() + "#pid=" + pid);
        master.start();
    }
}
