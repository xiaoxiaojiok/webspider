package com.coderxiao.webspider;

import com.coderxiao.webspider.scheduler.RedisScheduler;
import com.coderxiao.webspider.util.ConfigUtil;
import com.coderxiao.webspider.util.MongoUtil;
import com.coderxiao.zookeeper.directory.build.Directory;
import com.coderxiao.zookeeper.directory.webspider.RootInfo;
import org.junit.Test;

/**
 * <p>
 *     清理zookeeper、redis和数据库
 * </p>
 * Created by XiaoJian on 2015/12/29.
 */
public class CleanTest {

    @Test
    public void testClean() {
        new RedisScheduler().flushDB();
        MongoUtil.one().dropDB(ConfigUtil.getInstance().getMongoDB());
        Directory.delete();
    }
}
