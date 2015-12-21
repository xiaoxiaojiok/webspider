package com.coderxiao.admin.directory.webspider.workers;

import com.coderxiao.admin.directory.build.Directory;
import com.coderxiao.admin.directory.webspider.AbstractInfo;
import org.apache.zookeeper.CreateMode;

import java.util.Properties;

/**
 * <p>
 *     每一台Worker节点存储信息
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public class WorkerInfo extends AbstractInfo{

    public WorkerInfo(final String workerPath){
        values = new Properties();
        path = workerPath;
    }

    public void create(){
        Directory.create(path, CreateMode.EPHEMERAL_SEQUENTIAL);
    }

    public boolean exists() {
        return Directory.exists(path);
    }

}
