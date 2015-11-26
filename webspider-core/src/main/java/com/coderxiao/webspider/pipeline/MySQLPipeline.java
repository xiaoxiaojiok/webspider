package com.coderxiao.webspider.pipeline;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;

/**
 * Store results in Mysql.<br>
 *
 * @since 0.1.0
 */
@ThreadSafe
public class MySQLPipeline implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());
    
    public MySQLPipeline() {
    	
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String tableName = MySQL.TABLE_PREFIX + task.getSite().getId();
        String charset = (String) resultItems.get(ResultItems.CHARSET);
    	if(charset == null){
    		charset = "UTF-8";
    	}
    	String url = resultItems.getRequest().getUrl();
        String html =  (String) resultItems.get(ResultItems.HTML);
        Map<String,Object> map = new HashMap<String,Object>();
		map.put(MySQL.COLUMN_URL, url);
		map.put(MySQL.COLUMN_HTML, html);
		map.put(MySQL.COLUMN_CHARSET, charset);
		map.put(MySQL.COLUMN_SUPPORT, "");
		map.put(MySQL.COLUMN_STATUS, 0);
		map.put(MySQL.COLUMN_CREATEDATE, new Date());
		map.put(MySQL.COLUMN_UPDATEDATE, new Date());
        try {
        	MySQL.insert(tableName, map);
        } catch (Exception e) {
        	e.printStackTrace();
            logger.info("insert mysql error", e);
        }
    }
}
