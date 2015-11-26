package com.coderxiao.webspider.pipeline;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.annotation.ThreadSafe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.coderxiao.webspider.ResultItems;
import com.coderxiao.webspider.Task;
import com.coderxiao.webspider.util.FilePersistentBase;

/**
 * Store results in files.<br>
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
@ThreadSafe
public class HTMLPipeline extends FilePersistentBase implements Pipeline {

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * create a FilePipeline with default path"/data/webmagic/"
     */
    public HTMLPipeline() {
        setPath("/data/webmagic/");
    }

    public HTMLPipeline(String path) {
        setPath(path);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String path = this.path + PATH_SEPERATOR + task.getSite().getId() + PATH_SEPERATOR;
        try {
        	String charset = (String) resultItems.get(ResultItems.CHARSET);
        	if(charset == null){
        		charset = "UTF-8";
        	}
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(
            		new FileOutputStream(
            				getFile(path + DigestUtils.md5Hex(resultItems.getRequest().getUrl()) + ".html")),charset));
            for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
                if (entry.getValue() instanceof Iterable) {
                    Iterable value = (Iterable) entry.getValue();
                    for (Object o : value) {
                        printWriter.println(o);
                    }
                } else {
                    printWriter.println(entry.getValue());
                }
            }
            printWriter.close();
        } catch (IOException e) {
            logger.warn("write file error", e);
        }
    }
}
