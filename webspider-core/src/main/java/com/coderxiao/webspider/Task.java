package com.coderxiao.webspider;

/**
 * Interface for identifying different tasks.<br>
 *
 * @since 0.1.0
 */
public interface Task {

    /**
     * unique id for a task.
     *
     * @return uuid
     */
    public String getUUID();

    /**
     * site of a task
     *
     * @return site
     */
    public Site getSite();

}
