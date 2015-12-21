package com.coderxiao.zookeeper.monitor.listener;

import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <P>
 *     节点监听器
 * </P>
 *
 * Created by XiaoJian on 2015/12/19.
 */
public class NodeListener implements NodeCacheListener{

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private NodeCache cache;

    public NodeListener(NodeCache nodeCache) {
        this.cache = nodeCache;
    }

    @Override
    public void nodeChanged() throws Exception {
        logger.info(cache.getCurrentData().getPath() + " update, new Data: " + new String(cache.getCurrentData().getData()));

    }
}
