package com.coderxiao.admin.directory.webspider.sites;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.RootInfo;

import java.util.Properties;

/**
 * <p>
 *     Sites配置信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class SitesInfo extends AbstractInfo{

    public static final String SITES_PATH = RootInfo.ROOT_PATH + "/sites";

    public SitesInfo(){
        values = new Properties();
        path = SITES_PATH;
    }
}
