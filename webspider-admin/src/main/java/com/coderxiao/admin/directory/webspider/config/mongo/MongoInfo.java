package com.coderxiao.admin.directory.webspider.config.mongo;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;

import java.util.Properties;

/**
 * <p>
 * Mongo配置信息
 * </p>
 * Created by XiaoJian on 2015/12/29.
 */
public class MongoInfo extends AbstractInfo{

    public static final String MONGO_PATH = ConfigInfo.CONFIG_PATH + "/mongo";
    public static final String MONGO_URL_KEY = "mongo.url";
    public static final String MONGO_DB_KEY = "mongo.db";

    public MongoInfo(){
        values = new Properties();
        path = MONGO_PATH;
    }

}
