package com.coderxiao.admin.directory.webspider;

import java.util.Properties;

/**
 * <p>
 *     zookeeper根节点储存信息
 * </p>
 *
 * Created by XiaoJian on 2015/12/18.
 */
public class RootInfo extends AbstractInfo{

    public static final String ROOT_PATH = "/webspider";

    public RootInfo(){
        values = new Properties();
        path = ROOT_PATH;
    }

}
