package com.coderxiao.admin.directory.webspider.config.thread;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;

import java.util.Properties;

/**
 * Created by XiaoJian on 2015/12/20.
 */
public class ThreadInfo extends AbstractInfo{

    public static final String THREAD_PATH = ConfigInfo.CONFIG_PATH + "/thread";
    public static final String WORKER_THREADS = "worker.threads";
    public static final String SPIDER_THREAD = "spider.thread";

    public ThreadInfo(){
        values = new Properties();
        path = THREAD_PATH;
    }
}
