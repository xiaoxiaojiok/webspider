package com.coderxiao.webspider.pipeline;

import java.util.ArrayList;
import java.util.List;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;

/**
 * @author code4crafter@gmail.com
 * @since 0.4.0
 */
public class ResultItemsCollectorPipeline implements CollectorPipeline<ResultItems> {

    private List<ResultItems> collector = new ArrayList<ResultItems>();

    @Override
    public synchronized void process(ResultItems resultItems, Task task) {
        collector.add(resultItems);
    }

    @Override
    public List<ResultItems> getCollected() {
        return collector;
    }
}
