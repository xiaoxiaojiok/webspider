package com.coderxiao.webspider.scheduler;

import java.util.Set;

import com.alibaba.fastjson.JSON;
import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;

import com.coderxiao.webspider.util.ConfigUtil;
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
    
	protected static final JedisPoolConfig jedisPoolConfig= new JedisPoolConfig();
    
	static{
		int maxIdel = ConfigUtil.getInstance().getRedisMaxIdel();
		int maxTotal = ConfigUtil.getInstance().getRedisMaxTotal();
		long maxWait = ConfigUtil.getInstance().getRedisMaxWait();
		boolean testOnBorrow = ConfigUtil.getInstance().getRedisTestOnBorrow();
		host = ConfigUtil.getInstance().getRedisIP();
    	jedisPoolConfig.setMaxIdle(maxIdel);
    	jedisPoolConfig.setMaxTotal(maxTotal);
    	jedisPoolConfig.setMaxWaitMillis(maxWait);
    	jedisPoolConfig.setTestOnBorrow(testOnBorrow);
	}

    public RedisScheduler(){
        pool = new JedisPool(jedisPoolConfig, host);
    }

    @Override
    public void push(Request request, Task task) {
    	// 从池中获取一个Jedis对象 
        Jedis jedis = pool.getResource();
        try{
            //使用SortedSet进行url去重
            if (jedis.zrank(SET_PREFIX+task.getUUID(),request.getUrl())==null || shouldReserved(request)){
                //使用List保存队列
                jedis.rpush(QUEUE_PREFIX+task.getUUID(),JSON.toJSONString(request));
                jedis.zadd(SET_PREFIX+task.getUUID(),System.currentTimeMillis(),request.getUrl());
            }
        }finally{
            // 释放对象池  
            pool.returnResource(jedis);
        }
    }

    @Override
    public Request poll(Task task) {
    	// 从池中获取一个Jedis对象 
        Jedis jedis = pool.getResource();
        String request = null;
        try{
            request = jedis.lpop(QUEUE_PREFIX+task.getUUID());
        // 释放对象池
        }finally{
        	pool.returnResource(jedis);
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
    	Long status;
    	Jedis jedis = pool.getResource();
        try{
        	status = jedis.del(key);
        // 释放对象池  
        }finally{
        	 pool.returnResource(jedis);
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
    	Jedis jedis = pool.getResource();
        try{
            //使用SortedSet进行url去重
            if (jedis.zrank(key,value)==null){
                jedis.zadd(key,System.currentTimeMillis(),value);
            }
        }finally{
        	 pool.returnResource(jedis);
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
    	Jedis jedis = pool.getResource();
        long total = 0;
        try{
        	total = jedis.zcard(key);
        }finally{
        	pool.returnResource(jedis);
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
    	Jedis jedis = pool.getResource();
        long total = 0;
        try{
        	total = jedis.zrem(key,value);
        }finally{
        	pool.returnResource(jedis);
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
    	Jedis jedis = pool.getResource();
        Set<String> total = null;
        try{
        	total = jedis.zrange(key,start,stop);
        }finally{
        	pool.returnResource(jedis);
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
		Jedis jedis = pool.getResource();
        long total = 0;
        try{
        	total = jedis.llen(QUEUE_PREFIX+task.getUUID());
        }finally{
        	pool.returnResource(jedis);
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
		Jedis jedis = pool.getResource();
        long total = 0;
        try{
        	total = jedis.zcard(SET_PREFIX+task.getUUID());
        }finally{
        	pool.returnResource(jedis);
        }
		return total;
	}

    /**
     * 清空当前数据库
     */
    public void flushDB() {
        Jedis jedis = pool.getResource();
        try{
            jedis.flushDB();
        }finally{
            pool.returnResource(jedis);
        }
    }
}
