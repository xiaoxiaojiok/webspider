package com.coderxiao.admin.directory.webspider.config.mysql;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;

import java.util.Properties;

/**
 * <p>
 * Mysql配置信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class MysqlInfo extends AbstractInfo {

    public static final String MYSQL_PATH = ConfigInfo.CONFIG_PATH + "/mysql";
    public static final String MYSQL_URL_KEY = "jdbcUrl";
    public static final String MYSQL_USER_KEY = "username";
    public static final String MYSQL_PWD_KEY = "password";
    public static final String MYSQL_DRIVER_KEY = "driverClassName";
    public static final String MYSQL_MAX_POOL_SIZE_KEY = "maximumPoolSize";
    public static final String MYSQL_MIN_IDLE_KEY = "minimumIdle";
    public static final String MYSQL_IDLE_TIMEOUT_KEY = "idleTimeout";
    public static final String MYSQL_CONNECTION_TIMEOUT_KEY = "connectionTimeout";
    public static final String MYSQL_MAX_LIFETIME_KEY = "maxLifetime";
    public static final String MYSQL_TEST_QUERY_KEY = "connectionTestQuery";


    public MysqlInfo(){
        values = new Properties();
        path = MYSQL_PATH;
    }
}
