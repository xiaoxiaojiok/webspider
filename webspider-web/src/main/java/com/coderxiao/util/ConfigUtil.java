package com.coderxiao.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 *
 * <p>
 * 配置信息工具类 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 1.0.0
 *
 */
public class ConfigUtil {

	private static Properties props = null;

	private static Logger logger = LoggerFactory.getLogger(ConfigUtil.class);
	
	static{
		InputStream is = ConfigUtil.class.getClassLoader().getResourceAsStream("config.properties");
		if (is == null) {  
	        throw new IllegalArgumentException(  
	                "[config.properties] is not found!");  
    	}  
		props = new Properties();
		try {
			props.load(is);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}


	private ConfigUtil(){}

	private static class ConfigUtilGet{
		private static final ConfigUtil instance= new ConfigUtil();
	}

	public static ConfigUtil one(){
		return ConfigUtilGet.instance;
	}

	private String getConfig(String key) {
		String value ="" + props.getProperty(key);
		return value.trim();
	}
	
	public String getZkAddress(){
		return getConfig("zookeeper.address");
	}

	public int getZkTimeout(){
		return Integer.parseInt(getConfig("zookeeper.timeout"));
	}

}
