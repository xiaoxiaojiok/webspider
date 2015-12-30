package com.coderxiao.webspider.util;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.set;

/**
 * MongoUtil工具测试类
 *
 * Created by XiaoJian on 2015/12/28.
 */
public class MongoUtilTest {

    public static final int COUNT = 100;
    public static final int SITE = 8;
    public static final String COLLECTION = MongoUtil.COLLECTION_PREFIX + SITE;

    @Test
    public void testInsert() {
        for (int i = 1; i<= COUNT; i++) {
            HashMap<String, Object> map = new HashMap<>();
            map.put(MongoUtil.URL, "www.baidu.com_" +i);
            map.put(MongoUtil.HTML, "html_"+i);
            map.put(MongoUtil.CHARSET, "utf-8");
            map.put(MongoUtil.SUPPORT, "");
            map.put(MongoUtil.STATUS, i % 10);
            map.put(MongoUtil.CREATE_DATE, new Date());
            map.put(MongoUtil.UPDATE_DATE, new Date());
            MongoUtil.one().insert(COLLECTION,map);
        }

    }

    @Test
    public void testCount() {
        long count = MongoUtil.one().count(COLLECTION);
        System.out.println("count = " + count);

    }

    @Test
    public void testFirst() {
        String first = MongoUtil.one().first(COLLECTION);
        System.out.println("first = " + first);
        JSONObject jsonObject = JSONObject.parseObject(first);
        System.out.println("jsonObject.get(\"url\") = " + jsonObject.get(MongoUtil.UPDATE_DATE));
    }

    @Test
    public void testGetAll() {
        List<String> all = MongoUtil.one().getAll(COLLECTION);
        System.out.println("all = " + all);
    }

    @Test
    public void queryOne() {
        String s = MongoUtil.one().queryOne(COLLECTION, eq(MongoUtil.URL, "www.baidu.com_1"));
        System.out.println("s = " + s);
    }

    @Test
    public void queryAll() {
        List<String> query = MongoUtil.one().query(COLLECTION, eq(MongoUtil.STATUS, 6), true);
        for (int i = 0; i < query.size(); i++) {
            System.out.println("query = " + query.get(i));
        }
    }

    @Test
    public void testUpdateOne() {
        long result = MongoUtil.one().updateOne(COLLECTION, eq(MongoUtil.URL, "www.baidu.com_1"), set(MongoUtil.SUPPORT, "byXi"));
        System.out.println("result = " + result);
        String s = MongoUtil.one().queryOne(COLLECTION, eq(MongoUtil.URL, "www.baidu.com_1"));
        System.out.println("s = " + s);
    }

    @Test
    public void testUpdateAll() {
        long result = MongoUtil.one().updateAll(COLLECTION,eq(MongoUtil.STATUS, 6), set(MongoUtil.SUPPORT, "support"));
        System.out.println("result = " + result);
        String s = MongoUtil.one().queryOne(COLLECTION,eq(MongoUtil.STATUS, 6));
        System.out.println("s = " + s);
    }

    @Test
    public void testDeleteOne() {
        long result = MongoUtil.one().deleteOne(COLLECTION,eq(MongoUtil.STATUS, 6));
        System.out.println("result = " + result);
    }

    @Test
    public void testDeleteAll() {
        long result = MongoUtil.one().deleteAll(COLLECTION,eq(MongoUtil.STATUS, 6));
        System.out.println("result = " + result);
    }

    @Test
    public void testGetDBs() {
        String dBs = MongoUtil.one().getDBs();
        System.out.println(dBs);
    }

    @Test
    public void testDropDB() {
        MongoUtil.one().dropDB("test");
    }

    @Test
    public void testGetCollections() {
        String collections = MongoUtil.one().getCollections();
        System.out.println(collections);
    }

    @Test
    public void testDropCollection() {
        MongoUtil.one().dropCollection(COLLECTION);

    }
}