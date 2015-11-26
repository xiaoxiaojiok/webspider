package com.coderxiao.webspider.common;

import java.io.IOException;
import java.util.ResourceBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * 
 * <pre>
 * Jedis测试类，测试redis缓存是否有效
 * </pre>
 * 
 * @author XiaoJian
 */
public class JedisTest{
	
	private static JedisPool pool;  
	
	@Before
	public  void init() throws Exception {

	}

	@After
	public  void destroy() throws Exception {
		
	}

	@Test    
	//简单测试远程redis缓存是否可用
	public void testSimple() throws IOException {
		
		Jedis jedis = new Jedis("125.216.243.97",6379);  
		String keys = "name";  
		System.out.println(jedis.get(keys));  
		// 删数据  
		jedis.del(keys);  
		// 存数据  
		jedis.set(keys, "okok");  
		// 取数据  
		String value = jedis.get(keys);  
		  
		System.out.println(value);  
	}

}
