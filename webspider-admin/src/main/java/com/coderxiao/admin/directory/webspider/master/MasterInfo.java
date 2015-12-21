package com.coderxiao.admin.directory.webspider.master;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.RootInfo;
import com.coderxiao.admin.directory.webspider.config.ConfigInfo;
import com.coderxiao.admin.util.ConfigUtil;

import java.util.Properties;

/**
 * <p>
 *     Master节点存储信息
 * </p>
 *
 * Created by XiaoJian on 2015/12/18.
 */
public class MasterInfo extends AbstractInfo{

    public static final String MASTER_PATH = RootInfo.ROOT_PATH + "/master";

    public static final String MASTER_WEBSERVICE_ADDRESS_KEY = "webservice.address";

    public MasterInfo(){
        values = new Properties();
        path =  MASTER_PATH;
    }

}
