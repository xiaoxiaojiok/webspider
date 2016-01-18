package com.coderxiao.service.impl;

import com.coderxiao.service.WorkerService;
import com.coderxiao.util.ZooKeeperUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by XiaoJian on 2016/1/18.
 */
@Service
public class WorkerServiceImpl implements WorkerService{

    public static final String WORKERS_PATH = "/webspider/workers";

    public List<String> getWorkerURLs() {
        List<String> list = ZooKeeperUtil.one().getChildren(WORKERS_PATH);
        if(list == null) return null;
        List<String> workerURLs = new ArrayList<String>();
        for (String path : list) {
            workerURLs.add(path.split("-")[0]);
        }
        return workerURLs;
    }
}
