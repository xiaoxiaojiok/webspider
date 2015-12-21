package com.coderxiao.admin.directory.webspider;

import com.coderxiao.admin.directory.build.Directory;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * <p>
 *     节点存储信息基类
 * </p>
 * Created by XiaoJian on 2015/12/18.
 */
public abstract class AbstractInfo {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected Properties values;

    protected String path;

    public String getProperty(final String key) {
        return values.getProperty(key);
    }

    public void setProperty(String key, String value) {
        values.put(key, value);
    }

    public Stat save(){
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        try {
            values.store(byteOut,"update at " + new Date().toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        byte[] data = byteOut.toByteArray();
        Stat stat = Directory.setData(path, data);
        return stat;
    }

    public Stat load(){
        Stat stat = new Stat();
        byte[] data = Directory.getData(path, stat);
        if (data != null) {
            try {
                values.load(new ByteArrayInputStream(data));
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return stat;
    }

    public void print() {
        if(values == null) return;
        Iterator<Map.Entry<Object, Object>> it = values.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Object, Object> entry = it.next();
            System.out.println(entry.getKey() + "=" + entry.getValue());
        }
    }
}
