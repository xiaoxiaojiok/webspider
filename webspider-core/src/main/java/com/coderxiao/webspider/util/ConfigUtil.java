package com.coderxiao.webspider.util;

import com.coderxiao.zookeeper.util.ZooKeeperUtil;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/***
 * config.properties reload if it has changed
 * 
 */
public class ConfigUtil {

	private static Properties props = null;

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
			e.printStackTrace();
		}
	}
	
	private ConfigUtil(){}

	private static class ConfigUtilGet{
		private static final ConfigUtil instance= new ConfigUtil();
	}

	public static ConfigUtil getInstance(){
		return ConfigUtilGet.instance;
	}

	private String getConfig(String key) {
		String value ="" + props.getProperty(key);
		return value.trim();
	}
	
	public String getPipeLinePath(){
		Properties prop = System.getProperties();
		String system = prop.getProperty("os.name");
		if (system.startsWith("Win")) {
			return getConfig("pipeline.windows.path");
		} else {
			return getConfig("pipeline.linux.path");
		}
	}
	
	public int getWorkerThread(){
		return ZooKeeperUtil.one().getWorkerThread();
//		return Integer.parseInt(getConfig("worker.threads"));

	}
	
	public int getSpiderThread(){
		return ZooKeeperUtil.one().getSpiderThread();
//		return Integer.parseInt(getConfig("spider.thread"));
	}
	
	public String getWebserviceAddress(){
		String ip = IPUtils.getRealIp();
		if(ip == null){
			ip = "localhost";
		}
		String address = "http://" + ip +":"+ getConfig("webservice.port") + getConfig("webservice.address");
		return address;
	}
	
	public int getRetryTimes(){
		return Integer.parseInt(getConfig("site.retryTimes"));
	}
	
	public int getSleepTime(){
		return Integer.parseInt(getConfig("site.sleepTime"));
	}
	
	public int getCycleRetryTimes(){
		return Integer.parseInt(getConfig("site.cycleRetryTimes"));
	}
	
	public String getCharsetRegex(){
		return "<meta[^>]*?charset[\\s]*=[\\s]*['|\"]?([a-z|A-Z|0-9]*[\\-]*[0-9]*)[\\s|\\S]*['|\"]?";
//		return getConfig("site.charset.regex");
	}
	
	public int getRedisMaxIdel(){
		return ZooKeeperUtil.one().getRedisMaxIdel();
//		return Integer.parseInt(getConfig("redis.pool.maxIdle"));

	}
	
	public int getRedisMaxTotal(){
		return ZooKeeperUtil.one().getRedisMaxTotal();
//		return Integer.parseInt(getConfig("redis.pool.maxTotal"));
	}
	
	public long getRedisMaxWait(){
		return ZooKeeperUtil.one().getRedisMaxWait();
//		return Long.parseLong(getConfig("redis.pool.maxWait"));
	}
	
	public boolean getRedisTestOnBorrow(){
		return Boolean.parseBoolean(getConfig("redis.pool.testOnBorrow"));
	}
	
	public String getRedisIP(){
		return ZooKeeperUtil.one().getRedisIP();
//		return getConfig("redis.ip");
	}
	
	public String getStorageType(){
		return getConfig("pipeline.storageType");
	}
	
	public String getMysqlIP(){
		return getConfig("pipeline.mysql.ip");
	}
	
	public String getMysqlUser(){
		return getConfig("pipeline.mysql.username");
	}
	
	public String getMysqlPass(){
		return  getConfig("pipeline.mysql.password");
	}
	
	public String getStrategy(){
		return getConfig("scheduler.strategy");
	}
	
	public String getJettyIP(){
		String ip = IPUtils.getRealIp();
		if(ip == null){
			ip = "localhost";
		}
		return ip;
	}
	
	public int getJettyPort(){
		return Integer.parseInt(getConfig("jetty.port"));
	}
	
	public String getJettyContext(){
		return getConfig("jetty.context");
	}
	
	public static void main(String[] args) {
		System.out.println(new ConfigUtil().getCharsetRegex());
	}

	public String getZkAddress(){
		return getConfig("zookeeper.address");
	}

	public int getZkTimeout(){
		return Integer.parseInt(getConfig("zookeeper.timeout"));
	}
}
