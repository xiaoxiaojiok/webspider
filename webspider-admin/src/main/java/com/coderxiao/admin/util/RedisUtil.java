package com.coderxiao.admin.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Set;

/**
 * <p>
 * Redis工具类 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 1.0.0
 */
public class RedisUtil {

	protected static JedisPool pool;

	protected static final String host;

	public static final String QUEUE_PREFIX = "queue_";

	public static final String SET_PREFIX = "set_";

	public static final String SITE_KEY = "site_key";

	public static final String WEBSERVICE_KEY = "webservice_key";

	public static final String ONLINE_MACHINE_KEY = "online_machine_key";

	public static final String OFFLINE_MACHINE_KEY = "offline_machine_key";

	protected static final JedisPoolConfig jedisPoolConfig= new JedisPoolConfig();

	protected Logger logger = LoggerFactory.getLogger(getClass());

	static{
		int maxIdel = ConfigUtil.one().getRedisMaxIdel();
		int maxTotal = ConfigUtil.one().getRedisMaxTotal();
		long maxWait = ConfigUtil.one().getRedisMaxWait();
		boolean testOnBorrow = ConfigUtil.one().getRedisTestOnBorrow();
		host = ConfigUtil.one().getRedisIP();
//    	jedisPoolConfig.setMaxIdle(maxIdel);
    	jedisPoolConfig.setMaxTotal(maxTotal);
    	jedisPoolConfig.setMaxWaitMillis(maxWait);
    	jedisPoolConfig.setTestOnBorrow(testOnBorrow);
        pool = new JedisPool(jedisPoolConfig, host);
	}

    private RedisUtil(){

    }

    private static class RedisUtilGet{
        private static final RedisUtil instance = new RedisUtil();
    }

    public static RedisUtil one() {
        return RedisUtilGet.instance;
    }

	/**
	 *
	 * <pre>
	 * 移除单个字符串的key，移除单个列表、集合、有序集合或哈希表的key
	 * </pre>
	 * @param key
	 * @return 被删除的key数量
	 */
	public Long del(String key){
		Long status = 0L;
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			status = jedis.del(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
		return status;
	}

	/**
	 *
	 * <pre>
	 * 往有序列表key中增加一个成员value，或者更新分数如果它已经存在
	 * </pre>
	 * @param key
	 * @param value
	 */
	public void zadd(String key,String value){
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			//使用SortedSet进行url去重
			if (jedis.zrank(key,value)==null){
				jedis.zadd(key,System.currentTimeMillis(),value);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
	}

	/**
	 *
	 * <pre>
	 * 获取一个排序集合中的成员数量
	 * </pre>
	 * @param key
	 */
	public long zcard(String key){
		Jedis jedis = null;
		long total = 0;
		try{
			jedis = pool.getResource();
			total = jedis.zcard(key);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
		return total;
	}

	/**
	 *
	 * <pre>
	 * 从排序集合中删除一个或多个成员
	 * </pre>
	 * @param key
	 * @return 被删除的key数量
	 */
	public long zrem(String key,String... value){
		Jedis jedis = null;
		long total = 0;
		try{
			jedis = pool.getResource();
			total = jedis.zrem(key,value);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
		return total;
	}

	/**
	 *
	 * <pre>
	 * 从排序集合中返回成员
	 * </pre>
	 * @param key
	 */
	public Set<String> zrange(String key,long start,long stop){
		Jedis jedis = null;
		Set<String> total = null;
		try{
			jedis = pool.getResource();
			total = jedis.zrange(key,start,stop);
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
		return total;
	}

	/**
	 * 清空当前数据库
	 */
	public void flushDB() {
		Jedis jedis = null;
		try{
			jedis = pool.getResource();
			jedis.flushDB();
		} catch (Exception e) {
			logger.error(e.getMessage());
		} finally{
			release(jedis);
		}
	}

	/**
	 * 释放Jedis
	 *
	 * @param jedis
	 */
	protected void release(final Jedis jedis) {
		if (jedis != null) {
			jedis.close();
		}
	}

}
