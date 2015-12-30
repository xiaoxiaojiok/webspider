package com.coderxiao.webspider.scheduler;

import com.alibaba.fastjson.JSON;
import com.coderxiao.webspider.Request;
import com.coderxiao.webspider.Task;

import redis.clients.jedis.Jedis;

/**
 * <p>
 * 基于Redis的深度分布式URL队列调度器 <br>
 * </p>
 * @author XiaoJian <br>
 * @since 0.1.0
 */
public class DepthRedisScheduler extends RedisScheduler {
    
    public DepthRedisScheduler(){
        super();
    }

    @Override
    public void push(Request request, Task task) {
		 Object depthObject = request.getExtra(Request.DEPTH);
	        if (depthObject == null) {
	            request.putExtra(Request.DEPTH, 0);
	        } else {
	            int depth = (Integer) depthObject;
	            if (depth > task.getSite().getDepth()) {
	                return ;
	            }
	        }
	        
        Jedis jedis = null;
        try{
            // 从池中获取一个Jedis对象
            jedis = pool.getResource();
            //使用SortedSet进行url去重
            if (jedis.zrank(SET_PREFIX+task.getUUID(),request.getUrl())==null || shouldReserved(request)){
                //使用List保存队列
                jedis.rpush(QUEUE_PREFIX+task.getUUID(),JSON.toJSONString(request));
                jedis.zadd(SET_PREFIX+task.getUUID(),System.currentTimeMillis(),request.getUrl());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            // 释放对象
            release(jedis);
        }
    }

    @Override
    public Request poll(Task task) {
        Jedis jedis = null;
        String request = null;
        try{
            // 从池中获取一个Jedis对象
            jedis = pool.getResource();
            request = jedis.lpop(QUEUE_PREFIX+task.getUUID());
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally{
            // 释放对象
            release(jedis);
        }
        if (request==null) {
            return null;
        }
        return JSON.parseObject(request, Request.class);
    }

}
