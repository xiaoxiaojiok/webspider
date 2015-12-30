package com.coderxiao.webspider.util;

import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.mongodb.client.model.Projections.excludeId;

/**
 * Mongo DB工具类
 *
 * Created by XiaoJian on 2015/12/28.
 */
public class MongoUtil {

    public final static String COLLECTION_PREFIX = "site_";

    public final static String URL = "url";
    public final static String HTML = "html";
    public final static String CHARSET = "charset";
    public final static String SUPPORT = "support";
    public final static String STATUS = "status";
    public final static String CREATE_DATE = "createdate";
    public final static String UPDATE_DATE = "updatedate";

    private Logger logger = LoggerFactory.getLogger(getClass());

    private static MongoClientURI connectionString;

    private static MongoClient mongoClient;

    private static MongoDatabase database;


    static {
        connectionString = new MongoClientURI(ConfigUtil.getInstance().getMongoURL());
        mongoClient = new MongoClient(connectionString);
        database = mongoClient.getDatabase(ConfigUtil.getInstance().getMongoDB());
    }

    private MongoUtil(){

    }

    private static class MongoUtilGet{
        private static final MongoUtil instance = new MongoUtil();
    }

    public static MongoUtil one() {
        return MongoUtilGet.instance;
    }

    /**
     * 插入一条文档
     * @param collectionName 集合名
     * @param map 字段集
     */
    public void insert(String collectionName, Map<String,Object> map) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = new Document();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            document.append(entry.getKey(), entry.getValue());
        }
        collection.insertOne(document);
    }

    /**
     * 返回集合包含的文档数
     *
     * @param collectionName 集合名
     * @return 文档数
     */
    public long count(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        if (collection == null) return 0;
        return collection.count();
    }

    /**
     * 查询集合第一条文档
     *
     * @param collectionName 集合名
     * @return 文档的json字符串
     */
    public String first(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document document = collection.find().first();
        if(document == null) return null;
        return document.toJson();
    }


    /**
     * 得到集合所有的文档
     * @param collectionName 集合名
     * @return 返回文档的json字符串列表
     */
    public List<String> getAll(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        MongoCursor<Document> cursor = collection.find().iterator();
        List<String> list = new ArrayList<String>();
        try {
            while (cursor.hasNext()) {
                list.add(cursor.next().toJson());
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            cursor.close();
        }
        return list;
    }

    /**
     * 通过查询得到第一条文档
     * @param collectionName 集合名
     * @param where json参数
     * @return
     */
    public String queryOne(String collectionName, Bson where) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        Document first = collection.find(where).first();
        if(first == null) return null;
        return first.toJson();
    }

    /**
     * 返回查询集合
     * @param collectionName 集合名
     * @param where json参数
     * @param excludeID 不包括的字段
     * @return
     */
    public List<String> query(String collectionName, Bson where, boolean excludeID) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        final List<String> list = new ArrayList<String>();
        Block<Document> printBlock = new Block<Document>() {
            @Override
            public void apply(final Document document) {
                list.add(document.toJson());
            }
        };
        if (excludeID) {
            collection.find(where).projection(excludeId()).forEach(printBlock);
        } else {
            collection.find(where).forEach(printBlock);
        }
        return list;
    }

    /**
     * 更新匹配的第一条文档
     * @param collectionName 集合名
     * @param where
     * @param set
     * @return 返回修改的数
     */
    public long updateOne(String collectionName, Bson where, Bson set) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        UpdateResult updateResult = collection.updateOne(where, set);
        return updateResult.getModifiedCount();
    }

    /**
     * 更新匹配的所有文档
     * @param collectionName 集合名
     * @param where
     * @param set
     * @return 返回修改的数
     */
    public long updateAll(String collectionName, Bson where, Bson set) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        UpdateResult updateResult = collection.updateMany(where, set);
        return updateResult.getModifiedCount();
    }

    /**
     * 删除匹配的第一条文档
     * @param collectionName 集合名
     * @param where
     * @return 返回删除的数目
     */
    public long deleteOne(String collectionName, Bson where) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        DeleteResult deleteResult = collection.deleteOne(where);
        return deleteResult.getDeletedCount();
    }

    /**
     * 删除匹配的所有文档
     * @param collectionName
     * @param where
     * @return 返回删除的数目
     */
    public long deleteAll(String collectionName, Bson where) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        DeleteResult deleteResult = collection.deleteMany(where);
        return deleteResult.getDeletedCount();
    }

    /**
     * 得到可用的数据库列表
     * @return
     */
    public String getDBs() {
        MongoIterable<String> strings = mongoClient.listDatabaseNames();
        StringBuffer result = new StringBuffer();
        for (String string : strings) {
            result.append(string).append("\n");
        }
        return result.toString();
    }

    /**
     * 删除一个数据库
     * @param dbName 数据库名
     */
    public void dropDB(String dbName) {
        mongoClient.getDatabase(dbName).drop();
    }

    /**
     * 返回当前数据库集合数
     * @return
     */
    public String getCollections() {
        MongoIterable<String> strings = database.listCollectionNames();
        StringBuffer result = new StringBuffer();
        for (String string : strings) {
            result.append(string).append("\n");
        }
        return result.toString();
    }

    /**
     * 删除某个集合
     * @param collectionName 集合名
     */
    public void dropCollection(String collectionName) {
        MongoCollection<Document> collection = database.getCollection(collectionName);
        collection.drop();
    }

}


