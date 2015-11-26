package com.coderxiao.webspider.scheduler.component;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;
import com.google.common.collect.Sets;

/**
 * @author code4crafer@gmail.com
 */
public class HashSetDuplicateRemover implements DuplicateRemover {

    private Set<String> urls = Sets.newSetFromMap(new ConcurrentHashMap<String, Boolean>());

    @Override
    public boolean isDuplicate(Request request, Task task) {
        return !urls.add(getUrl(request));
    }

    protected String getUrl(Request request) {
        return request.getUrl();
    }

    @Override
    public void resetDuplicateCheck(Task task) {
        urls.clear();
    }

    @Override
    public long getTotalRequestsCount(Task task) {
        return urls.size();
    }
}
