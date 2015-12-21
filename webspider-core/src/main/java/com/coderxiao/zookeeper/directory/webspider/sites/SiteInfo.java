package com.coderxiao.zookeeper.directory.webspider.sites;

import com.coderxiao.zookeeper.directory.build.Directory;
import com.coderxiao.zookeeper.directory.webspider.AbstractInfo;
import org.apache.zookeeper.CreateMode;

import java.util.Properties;

/**
 * <p>
 *     每个站点配置信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class SiteInfo extends AbstractInfo {

    public static final String OP_ADD = "add";

    public static final String OP_UPDATE = "update";

    public static final String OP_DISABLE = "disable";

    public static final String OP_ENABLE = "enable";

    public static final String SITE_ALLOW_URL = "site.allowURL";

    public static final String SITE_START_URL = "site.startURL";

    public static final String SITE_OPERATOR = "site.operator";

    public static final String SITE_DEPTH_LIMIT = "site.depthLimit";

    public SiteInfo(String sitePath){
        values = new Properties();
        path = sitePath;
    }

    public void create(){
        Directory.create(path, CreateMode.PERSISTENT);
    }

    public boolean exists() {
        return Directory.exists(path);
    }

}
