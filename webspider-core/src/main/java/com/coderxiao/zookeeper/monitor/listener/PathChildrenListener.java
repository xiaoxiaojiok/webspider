package com.coderxiao.zookeeper.monitor.listener;

import com.coderxiao.zookeeper.WorkerService;
import com.coderxiao.zookeeper.directory.webspider.sites.SiteInfo;
import com.coderxiao.zookeeper.directory.webspider.sites.SitesInfo;
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

        if (path.equals(SitesInfo.SITES_PATH)) {
            SiteInfo siteInfo = new SiteInfo(event.getData().getPath());
            switch (event.getType()) {
                case CHILD_ADDED:
                    logger.info("CHILD_ADDED#[ "+ node +" ]");
                    siteInfo.load();
                    WorkerService.add(siteInfo,Long.parseLong(node));
                    WorkerService.enable(siteInfo,Long.parseLong(node));
                    break;
                case CHILD_UPDATED:
                    siteInfo.load();
                    if (siteInfo.getProperty(SiteInfo.SITE_OPERATOR).equals(SiteInfo.OP_ENABLE)) {
                        logger.info("CHILD_UPDATED#[ "+ node +" ]" + SiteInfo.OP_ENABLE);
                        WorkerService.enable(siteInfo,Long.parseLong(node));
                    }
                    if (siteInfo.getProperty(SiteInfo.SITE_OPERATOR).equals(SiteInfo.OP_DISABLE)) {
                        logger.info("CHILD_UPDATED#[ "+ node +" ]" + SiteInfo.OP_DISABLE);
                        WorkerService.disable(Long.parseLong(node));
                    }
                    if (siteInfo.getProperty(SiteInfo.SITE_OPERATOR).equals(SiteInfo.OP_UPDATE)) {
                        logger.info("CHILD_UPDATED#[ "+ node +" ]" + SiteInfo.OP_UPDATE);
                        WorkerService.update(siteInfo,Long.parseLong(node));
                    }
                    break;
                case CHILD_REMOVED:
                    logger.info("CHILD_REMOVED[ "+ node +" ]");
                    WorkerService.delete(Long.parseLong(node));
                    break;
                default:
                    break;

            }
        }

//        logger.info("CurrentData: "+cache.getCurrentData());

    }
}
