package com.coderxiao.admin.directory.webspider.config.redis;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;

import java.util.Properties;

/**
 * <p>
 * Redis配置信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class RedisInfo extends AbstractInfo{

    public static final String REDIS_PATH = ConfigInfo.CONFIG_PATH + "/redis";
    public static final String REDIS_IP_KEY = "redis.ip";
    public static final String REDIS_MAX_TOTAL_KEY = "redis.pool.maxTotal";
    public static final String REDIS_MAX_IDLE_KEY = "redis.pool.maxIdle";
    public static final String REDIS_MAX_WAIT_KEY = "redis.pool.maxWait";

    public RedisInfo(){
        values = new Properties();
        path = REDIS_PATH;
    }
}
