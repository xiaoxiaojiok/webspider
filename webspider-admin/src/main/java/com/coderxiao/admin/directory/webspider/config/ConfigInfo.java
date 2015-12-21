package com.coderxiao.admin.directory.webspider.config;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.RootInfo;

import java.util.Properties;

/**
 * <p>
 *     配置节点信息
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class ConfigInfo extends AbstractInfo{

    public static final String CONFIG_PATH = RootInfo.ROOT_PATH + "/config";

    public ConfigInfo(){
        values = new Properties();
        path = CONFIG_PATH;
    }
}
