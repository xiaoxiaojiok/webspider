package com.coderxiao.webspider.pipeline;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coderxiao.webspider.util.SqlUtils;

public class MySQL {

	public final static String DESC = "desc";
	public final static String ASC = "asc";

	public final static String TABLE_PREFIX = "site_";

	public final static String COLUMN_ID = "id";
	public final static String COLUMN_URL = "url";
	public final static String COLUMN_HTML = "html";
	public final static String COLUMN_CHARSET = "charset";
	public final static String COLUMN_SUPPORT = "support";
	public final static String COLUMN_STATUS = "status";
	public final static String COLUMN_CREATEDATE = "createdate";
	public final static String COLUMN_UPDATEDATE = "updatedate";
	
	public final static int NUM_COLUMNS = 8;
	public final static int MAX_SELECT = 100;

	private MySQL() {
	}

	/**
	 * <p>
	 * 创建数据库表,若存在则不再创建
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回受影响的行数
	 */
	public static int create(String tableName) {
		if (SqlUtils.me().isExists(tableName)) {
			return 0;
		}
		String sql = "create table " + tableName + " (" +
				COLUMN_ID + " int auto_increment primary key," + 
				COLUMN_URL + " varchar(255)," + 
				COLUMN_HTML + " longtext,"+ 
				COLUMN_CHARSET + " varchar(30)," + 
				COLUMN_SUPPORT + " longtext,"+ 
				COLUMN_STATUS + " int," + 
				COLUMN_CREATEDATE+ " datetime," + 
				COLUMN_UPDATEDATE + " datetime)";
		int result = SqlUtils.me().setSql(sql).executeUpdate();
		sql = "create unique index index_id on " + tableName + "(" + COLUMN_ID + ")";
		SqlUtils.me().setSql(sql).executeUpdate();
		return result;
	}

	/**
	 * <p>
	 * 删除数据库表
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回受影响的行数
	 */
	public static int drop(String tableName) {
		return SqlUtils.me().drop(tableName);
	}

	/**
	 * <p>
	 * 查询数据库表数目
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @param where
	 *            键值对
	 * @return 返回数目
	 * @throws SQLException
	 */
	public static long count(String tableName, Map<String, Object> where)
			throws SQLException {
		return SqlUtils.me().count(tableName, where);
	}

	/**
	 * <p>
	 * 查询数据库表
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @param where
	 *            键值对
	 * @return 返回迭代集合
	 */
	public static List<Object> select(String tableName,
			Map<String, Object> where) {
		return SqlUtils.me().select(tableName, where);
	}

	/**
	 * <p>
	 * 查询数据库表
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @param columns
	 *            列名
	 * @param where
	 *            键值对
	 * @param startOffset
	 *            位移（从0开始，小于0会忽略）
	 * @param maxSize
	 *            数量 (小于0会忽略)
	 * @param orderBy
	 *            按什么排序
	 * @return
	 */
	public static List<Object> select(String tableName, Set<String> columns,
			Map<String, Object> where, int startOffset, int maxSize,
			Map<String, String> orderBy) {
		return SqlUtils.me().select(tableName, columns, where, startOffset,
				maxSize, orderBy);
	}

	/**
	 * <p>
	 * 插入数据
	 * </p>
	 * 
	 * @param tableName
	 *            数据库表
	 * @param columns
	 *            列名和列的值
	 * @return 返回受影响的行数
	 */
	public static int insert(String tableName, Map<String, Object> columns) {
		return SqlUtils.me().insert(tableName, columns);
	}

	/**
	 * <p>
	 * 更新数据
	 * </p>
	 * 
	 * @param tableName
	 *            数据库表
	 * @param set
	 *            列名
	 * @param where
	 *            列名和列的值
	 * @return 返回受影响的行数
	 */
	public static int update(String tableName, Map<String, Object> set,
			Map<String, Object> where) {
		return SqlUtils.me().update(tableName, set, where);
	}

	/**
	 * <p>
	 * 删除数据
	 * </p>
	 * 
	 * @param tableName
	 *            数据库表
	 * @param where
	 *            列名和列的值
	 * @return 返回受影响的行数
	 */
	public static int delete(String tableName, Map<String, Object> where) {
		return SqlUtils.me().delete(tableName, where);
	}
}
