package com.coderxiao.webspider.pipeline;

import java.util.UUID;

import org.junit.BeforeClass;
import org.junit.Test;

import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Site;
import com.coderxiao.webspider.Task;

/**
 * Created by ywooer on 2014/5/6 0006.
 */
public class FilePipelineTest {

    private static ResultItems resultItems;
    private static Task task;

    @BeforeClass
    public static void before() {
        resultItems = new ResultItems();
        resultItems.put("content", "webmagic 爬虫工具");
        Request request = new Request("http://www.baidu.com");
        resultItems.setRequest(request);

        task = new Task() {
            @Override
            public String getUUID() {
                return UUID.randomUUID().toString();
            }

            @Override
            public Site getSite() {
                return null;
            }
        };
    }
    @Test
    public void testProcess() {
        FilePipeline filePipeline = new FilePipeline();
        filePipeline.process(resultItems, task);
    }
}
