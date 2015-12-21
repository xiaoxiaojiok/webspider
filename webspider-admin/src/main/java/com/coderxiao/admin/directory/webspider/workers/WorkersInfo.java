package com.coderxiao.admin.directory.webspider.workers;

import com.coderxiao.admin.directory.webspider.AbstractInfo;
import com.coderxiao.admin.directory.webspider.RootInfo;

import java.util.Properties;

/**
 * <p>
 *     Workers节点存储信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class WorkersInfo extends AbstractInfo{

    public static final String WORKERS_PATH = RootInfo.ROOT_PATH + "/workers";

    public WorkersInfo(){
        values = new Properties();
        path = WORKERS_PATH;
    }
}
