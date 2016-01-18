package com.coderxiao.admin.util;

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

	public String getWebserviceAddress(){
		String ip = IPUtils.getRealIp();
		if(ip == null){
			ip = "localhost";
		}
		String address = "http://" + ip +":"+ getConfig("webservice.port") + getConfig("webservice.address");
		return address;
	}

	public String getDockerWebserviceAddress(String ip){
		if(ip == null){
			ip = "localhost";
		}
		String address = "http://" + ip +":"+ getConfig("webservice.port") + getConfig("webservice.address");
		return address;
	}

	public int getRedisMaxIdel(){
		return Integer.parseInt(getConfig("redis.pool.maxIdle"));
	}

	public int getRedisMaxTotal(){
		return Integer.parseInt(getConfig("redis.pool.maxTotal"));
	}

	public long getRedisMaxWait(){
		return Long.parseLong(getConfig("redis.pool.maxWait"));
	}

	public boolean getRedisTestOnBorrow(){
		return Boolean.parseBoolean(getConfig("redis.pool.testOnBorrow"));
	}

	public String getRedisIP(){
		return getConfig("redis.ip");
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

	public int getWorkerThread(){
		return Integer.parseInt(getConfig("worker.threads"));
	}

	public String getMysqlUrl() {
		return getConfig("jdbcUrl");
	}

	public String getMysqlUser() {
		return getConfig("username");
	}

	public String getMysqlPwd() {
		return getConfig("password");
	}

	public String getMysqlDriver() {
		return getConfig("driverClassName");
	}

	public String getPoolMaxPoolSize() {
		return getConfig("maximumPoolSize");
	}

	public String getPoolMinIdle() {
		return getConfig("minimumIdle");
	}

	public String getPoolIdleTimeout() {
		return getConfig("idleTimeout");
	}

	public String getPoolConnectionTimeout() {
		return getConfig("connectionTimeout");
	}

	public String getPoolMaxLifeTime() {
		return getConfig("maxLifetime");
	}

	public String getPoolTestQuery() {
		return getConfig("connectionTestQuery");
	}

	public int getSpiderThread() {
		return Integer.parseInt(getConfig("spider.thread"));
	}

	public String getMongoURL(){
		return getConfig("mongo.url");
	}

	public String getMongoDB() {
		return getConfig("mongo.db");
	}

	public String getDockerPortStart() {
		return getConfig("docker.port.start");
	}
}
