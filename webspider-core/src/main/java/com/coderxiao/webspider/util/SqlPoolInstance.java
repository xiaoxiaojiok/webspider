package com.coderxiao.webspider.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class SqlPoolInstance {

	private static class SqlPoolInstanceGet{
		private static final SqlPool instance = new SqlPool();
	}
	
	private SqlPoolInstance(){}
	
	public static SqlPool getInstance(){
		return SqlPoolInstanceGet.instance;
	}
	
}


class SqlPool {

	private static HikariDataSource ds = null;

	static {
		try {
			InputStream is = SqlPool.class.getClassLoader()
					.getResourceAsStream("db.properties");
			if (is == null) {
				throw new IllegalArgumentException(
						"[db.properties] is not found!");
			}
			Properties prop = new Properties();
			prop.load(is);
			HikariConfig config = new HikariConfig(prop);
			ds = new HikariDataSource(config);
		} catch (Exception e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	public Connection getConnection() throws SQLException {
		return ds.getConnection();
	}

	public void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}

