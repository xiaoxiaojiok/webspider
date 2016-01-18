package com.coderxiao.webspider.scheduler;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;

import com.coderxiao.webspider.util.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * <p>
 * 基于Redis的分布式URL队列调度器 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 0.1.0
 */
public class RedisScheduler implements MonitorableScheduler{

	protected JedisPool pool;
    
	protected static final String host;

	public static final String QUEUE_PREFIX = "queue_";

	public static final String SET_PREFIX = "set_";
	
	public static final String SITE_KEY = "site_key";
	
	public static final String WEBSERVICE_KEY = "webservice_key";

    public static final String DOCKER_PORT_KEY = "docker_port_key";
    
	protected static final JedisPoolConfig jedisPoolConfig= new JedisPoolConfig();

    protected Logger logger = LoggerFactory.getLogger(getClass());
    
	static{
		int maxTotal = ConfigUtil.getInstance().getRedisMaxTotal();
		long maxWait = ConfigUtil.getInstance().getRedisMaxWait();
		host = ConfigUtil.getInstance().getRedisIP();
    	jedisPoolConfig.setMaxTotal(maxTotal);
    	jedisPoolConfig.setMaxWaitMillis(maxWait);
    	jedisPoolConfig.setTestOnBorrow(true);
	}

    public RedisScheduler(){
        pool = new JedisPool(jedisPoolConfig, host);
    }

    @Override
    public void push(Request request, Task task) {
        Jedis jedis = null;
        try {
            // 从池中获取一个Jedis对象
            jedis = pool.getResource();
            //使用SortedSet进行url去重
            if (jedis.zrank(SET_PREFIX + task.getUUID(), request.getUrl()) == null || shouldReserved(request)) {
                //使用List保存队列
                jedis.rpush(QUEUE_PREFIX + task.getUUID(), JSON.toJSONString(request));
                jedis.zadd(SET_PREFIX + task.getUUID(), System.currentTimeMillis(), request.getUrl());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            // 释放对象
            release(jedis);
        }
    }


    @Override
    public Request poll(Task task) {
        String request = null;
        Jedis jedis = null;
        try{
            // 从池中获取一个Jedis对象
            jedis = pool.getResource();
            request = jedis.lpop(QUEUE_PREFIX+task.getUUID());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
        if (request==null) {
            return null;
        }
        return JSON.parseObject(request, Request.class);
    }
    
    protected boolean shouldReserved(Request request) {
        return request.getExtra(Request.CYCLE_TRIED_TIMES) != null;
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
     * <pre>
	 * 获取任务task剩下的URL数目
	 * </pre>
     */
	@Override
	public long getLeftRequestsCount(Task task) {
		Jedis jedis = null;
        long total = 0;
        try{
            jedis = pool.getResource();
        	total = jedis.llen(QUEUE_PREFIX+task.getUUID());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
		return total;
	}


    /**
     * <pre>
	 * 获取任务task总共的URL数目
	 * </pre>
     */
	@Override
	public long getTotalRequestsCount(Task task) {
		Jedis jedis = null;
        long total = 0;
        try{
            jedis = pool.getResource();
        	total = jedis.zcard(SET_PREFIX+task.getUUID());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
		return total;
	}

    /**
     * <pre>
     *     对key对应的数字做加1操作,如果key不存在，那么在操作之前，这个key对应的值会被置为0
     * </pre>
     * @param key 要操作的key
     * @return 返回操作后key的值
     */
    public Long incr(String key) {
        Jedis jedis = null;
        Long result = null;
        try{
            jedis = pool.getResource();
            result = jedis.incr(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
        return result;
    }

    /**
     * <pre>
     *     判断key是否存在
     * </pre>
     * @param key
     * @return true 如果key存在 / false 如果key不存在
     */
    public Boolean exists(String key) {
        Jedis jedis = null;
        Boolean result = null;
        try{
            jedis = pool.getResource();
            result = jedis.exists(key);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
        return result;
    }

    /**
     * <pre>
     *     将key和value对应，如果key已经存在了，它将会被覆盖而不管它是什么类型
     * </pre>
     * @param key
     * @param value
     * @return
     */
    public String set(String key,String value) {
        Jedis jedis = null;
        String result = null;
        try{
            jedis = pool.getResource();
            result = jedis.set(key, value);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            release(jedis);
        }
        return result;
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
