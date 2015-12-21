package com.coderxiao.admin.monitor.listener;

import com.coderxiao.admin.directory.webspider.workers.WorkersInfo;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.utils.ZKPaths;
import org.apache.curator.utils.ZKPaths.PathAndNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *     子节点监听器
 * </p>
 * Created by XiaoJian on 2015/12/19.
 */
public class PathChildrenListener implements PathChildrenCacheListener{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private PathChildrenCache cache;

    public PathChildrenListener(PathChildrenCache pathChildrenCache) {
        this.cache = pathChildrenCache;
    }

    @Override
    public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
        if (event.getData() == null || event.getType() == null) {
            return;
        }
        String data = new String(event.getData().getData());
        PathAndNode pathAndNode = ZKPaths.getPathAndNode(event.getData().getPath());
        String path = pathAndNode.getPath();
        String node = pathAndNode.getNode();

        //TODO
        //目前站点的分配调度全部交给Worker自己完成，默认Worker会监听sites节点，即一个site在所有Worker中运行
        if (path.equals(WorkersInfo.WORKERS_PATH)) {
            switch (event.getType()) {
                case CHILD_ADDED:
                    logger.info("CHILD_ADDED#"+ node);
                    break;
                case CHILD_UPDATED:
                    logger.info("CHILD_UPDATED#"+ node);
                    break;
                case CHILD_REMOVED:
                    logger.info("CHILD_REMOVED#"+ node);
                    break;
                default:
                    break;

            }
        }

//        logger.info("CurrentData: "+cache.getCurrentData());

    }
}
