package com.coderxiao.webspider.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.coderxiao.webspider.pipeline.MySQL;

/***
 * <p>
 * DB Helper
 * </p>
 * 
 * @author XiaoJian
 *
 */
public class SqlUtils {

	private static SqlPool SqlPool = SqlPoolInstance.getInstance();

	private Connection conn = null;
	private PreparedStatement st = null;
	private ResultSet rs = null;

	private String sql;
	private List<Object> sqlValues;

	private SqlUtils() {
	}

	public static SqlUtils me() {
		return new SqlUtils();
	}

	public String getSql() {
		return sql;
	}

	public SqlUtils setSql(String sql) {
		this.sql = sql;
		return this;
	}

	public List<Object> getSqlValues() {
		return sqlValues;
	}

	public SqlUtils setSqlValues(List<Object> sqlValues) {
		this.sqlValues = sqlValues;
		return this;
	}

	/**
	 * <p>
	 * 执行查找，需要先设置sql和sqlValues
	 * </p>
	 * 
	 * @return
	 */
	public List<Object> executeQuery() {
		if (sql == null) {
			return new ArrayList<Object>();
		}
		List<Object> list = new ArrayList<Object>();
		try {
			conn = SqlPool.getConnection();
			st = conn.prepareStatement(sql);
			if (sqlValues != null && sqlValues.size() > 0) {
				setSqlValues(st, sqlValues);
			}
			rs = st.executeQuery();
			while (rs.next()) {
				for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
					list.add(rs.getObject(i));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql = null;
			sqlValues = null;
			SqlPool.release(conn, st, rs);
		}
		return list;
	}

	/**
	 * <p>
	 * 执行增删改，需要先设置sql和sqlValues
	 * </p>
	 * 
	 * @return 返回受影响的行数
	 */
	public int executeUpdate() {
		if (sql == null) {
			return 0;
		}
		int result = -1;
		try {
			conn = SqlPool.getConnection();
			st = conn.prepareStatement(sql);
			if (sqlValues != null && sqlValues.size() > 0) {
				setSqlValues(st, sqlValues);
			}
			result = st.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			sql = null;
			sqlValues = null;
			SqlPool.release(conn, st, rs);
		}
		return result;
	}

	/**
	 * <p>
	 * 判断数据库表是否已经存在
	 * </p>
	 * 
	 * @param tableName
	 *            表名
	 * @return 返回true或false
	 */
	public boolean isExists(String tableName) {
		try {
			conn = SqlPool.getConnection();
			rs = conn.getMetaData().getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			SqlPool.release(conn, st, rs);
		}
		return false;
	}

	private void setSqlValues(PreparedStatement pst, List<Object> sqlValues) {
		for (int i = 0; i < sqlValues.size(); i++) {
			try {
				pst.setObject(i + 1, sqlValues.get(i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
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
	public int drop(String tableName) {
		checkIfExists(tableName);
		sql = "drop table " + tableName;
		int result = executeUpdate();
		return result;
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
	public long count(String tableName, Map<String, Object> where)
			throws SQLException {
		checkIfExists(tableName);
		List<Object> result = null;
		int ws = (where != null) ? where.size() : 0;
		StringBuilder sb = new StringBuilder(64 + ws * 32);
		sb.append("\n select count(").append(MySQL.COLUMN_ID).append(") count from ").append(tableName);
		if (ws == 0) {
			sql = sb.toString();
			result = executeQuery();
			if (result != null && result.size() != 0) {
				return (Long) result.get(0);
			}
		}
		appendWhere(sb, where, ws);
		sql = sb.toString();
		result = executeQuery();
		if (result != null && result.size() != 0) {
			return (Long) result.get(0);
		}
		return 0;
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
	 * @return 返回迭代列表
	 */
	public List<Object> select(String tableName, Map<String, Object> where) {
		checkIfExists(tableName);
		int ws = (where != null) ? where.size() : 0;
		StringBuilder sb = new StringBuilder(64 + ws * 32);
		sb.append("\n select * from ").append(tableName);
		if (ws == 0) {
			sql = sb.toString();
			return executeQuery();
		}
		appendWhere(sb, where, ws);
		sql = sb.toString();
		return executeQuery();
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
	 *            位移（从0开始）
	 * @param maxSize
	 *            数量
	 * @param orderBy
	 *            按什么排序
	 * @return 返回迭代列表
	 */
	public List<Object> select(String tableName, Set<String> columns,
			Map<String, Object> where, int startOffset, int maxSize,
			Map<String, String> orderBy) {
		checkIfExists(tableName);
		if (columns == null) {
			return new ArrayList<Object>();
		}
		if (columns.size() <= 0) {
			return new ArrayList<Object>();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("\n select ");
		int idx = 0;
		for (String column : columns) {
			if (idx == columns.size() - 1) {
				sb.append(column).append(" ");
			} else {
				sb.append(column).append(",");
			}
			idx++;
		}
		sb.append("from ").append(tableName).append(" ");

		int ws = (where != null) ? where.size() : 0;
		appendWhere(sb, where, ws);

		if (orderBy != null && orderBy.size() > 0) {
			sb.append("\n order by ");
			int index = 0;
			for (String columnName : orderBy.keySet()) {
				sb.append(columnName).append(" ")
						.append(orderBy.get(columnName));
				index++;
				if (index < orderBy.size()) {
					sb.append(",");
				}
			}
		}
		if (startOffset >= 0 && maxSize >= 0) {
			sb.append(" limit ").append(startOffset).append(",")
					.append(maxSize);
		}
		sql = sb.toString();
		return executeQuery();
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
	public int insert(String tableName, Map<String, Object> columns) {
		checkIfExists(tableName);
		if (columns == null) {
			return 0;
		}
		if (columns.size() == 0) {
			return 0;
		}
		int columnSize = columns.size();
		StringBuilder sb = new StringBuilder(64 + columnSize * 32);
		sb.append("\n insert into ").append(tableName);
		sb.append(" ( ");
		int index = 0;
		for (String item : columns.keySet()) {
			sb.append(item);
			index++;
			if (index != columnSize) {
				sb.append(",");
			}
		}
		sb.append(" )\n");
		sb.append(" values ( ");
		index = 0;
		sqlValues = new ArrayList<Object>();
		for (String item : columns.keySet()) {
			Object value = columns.get(item);
			sqlValues.add(value);
			sb.append("?");
			index++;
			if (index != columnSize) {
				sb.append(",");
			}
		}
		sb.append(" )");
		sql = sb.toString();
		int result = executeUpdate();
		return result;
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
	public int update(String tableName, Map<String, Object> set,
			Map<String, Object> where) {
		checkIfExists(tableName);
		if (set == null) {
			return 0;
		}
		if (set.size() == 0) {
			return 0;
		}
		int ss = set.size();
		int ws = (where != null) ? where.size() : 0;
		StringBuilder sb = new StringBuilder(64 + ss * 32 + ws * 32);
		sb.append("\n update ").append(tableName).append("\n set ");
		int index = 0;
		sqlValues = new ArrayList<Object>();
		for (String key : set.keySet()) {
			Object v = set.get(key);
			sqlValues.add(v);
			sb.append("\t").append(key).append("=").append("?");
			index++;
			if (index < ss) {
				sb.append(",\n");
			}
		}
		if (ws == 0) {
			sql = sb.toString();
			return executeUpdate();
		}
		appendWhere(sb, where, ws);
		sql = sb.toString();
		return executeUpdate();
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
	public int delete(String tableName, Map<String, Object> where) {
		checkIfExists(tableName);
		int result = 0;
		int ws = (where != null) ? where.size() : 0;
		StringBuilder sb = new StringBuilder(64 + ws * 32);
		sb.append("\n delete from ").append(tableName);
		if (ws == 0) {
			sql = sb.toString();
			return executeUpdate();
		}
		appendWhere(sb, where, ws);
		sql = sb.toString();
		result = executeUpdate();
		return result;
	}

	// 拼接where子句
	private void appendWhere(StringBuilder sb, Map<String, Object> where, int ws) {
		if (where == null) {
			return;
		}
		sb.append("\n where ");
		int index = 0;
		if (sqlValues == null) {
			sqlValues = new ArrayList<Object>();
		}
		for (String key : where.keySet()) {
			Object v = where.get(key);
			sqlValues.add(v);
			sb.append(key).append("=").append("?");
			index++;
			if (index < ws) {
				sb.append("\n   and ");
			}
		}
	}
	
	private void checkIfExists(String tableName) {
        if (!isExists(tableName)) {
            throw new IllegalArgumentException("Table " + tableName +" doesn't exist");
        }
    }
}
