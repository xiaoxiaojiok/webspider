package com.coderxiao.webspider.pipeline;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;
import com.coderxiao.webspider.util.MongoUtil;
import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Store results in MongoDB.<br>
 *
 * @since 0.1.0
 */
@ThreadSafe
public class MongoPipeline implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public MongoPipeline() {
    	
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String collection = MongoUtil.COLLECTION_PREFIX + task.getSite().getId();
        String charset = (String) resultItems.get(ResultItems.CHARSET);
    	if(charset == null){
    		charset = "UTF-8";
    	}
    	String url = resultItems.getRequest().getUrl();
        String html =  (String) resultItems.get(ResultItems.HTML);
        Map<String,Object> map = new HashMap<String,Object>();
		map.put(MongoUtil.URL, url);
		map.put(MongoUtil.HTML, html);
		map.put(MongoUtil.CHARSET, charset);
		map.put(MongoUtil.SUPPORT, "");
		map.put(MongoUtil.STATUS, 0);
		map.put(MongoUtil.CREATE_DATE, new Date());
		map.put(MongoUtil.UPDATE_DATE, new Date());
        try {
        	MongoUtil.one().insert(collection, map);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
