package clonegod.mybatis.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class JDBCUtil {
	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost:3306/blogs?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

	// Database credentials
	static final String USER = "alice";
	static final String PASS = "alice123";

	/**
	 * 打开JDBC 数据库连接
	 * 
	 */
	public static Connection getConnection() {
		try {
			Class.forName(JDBC_DRIVER);
			Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
			System.out.println("TransactionIsolation=" + parseTransactionIsolation(connection.getTransactionIsolation()));
			return connection;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 数据库的5种事务隔离级别
	 * int TRANSACTION_NONE             = 0;
	 * int TRANSACTION_READ_UNCOMMITTED = 1;
	 * int TRANSACTION_READ_COMMITTED   = 2; // 推荐
	 * int TRANSACTION_REPEATABLE_READ  = 4; // 默认
	 * int TRANSACTION_SERIALIZABLE     = 8; // 事务串行执行，无并发可言，不适用
	 */
	private static String parseTransactionIsolation(int n) {
		switch(n) {
			case 0: return "TRANSACTION_NONE";
			case 1: return "TRANSACTION_READ_UNCOMMITTED";
			case 2: return "TRANSACTION_READ_COMMITTED";
			case 4: return "TRANSACTION_REPEATABLE_READ";
			case 8: return "TRANSACTION_SERIALIZABLE";
			default: 
				return "";
		}
	}

	/**
	 * 一定要关闭相关资源！！！
	 */
	public static void close(Object... resources) {
		if (resources.length == 0) {
			return;
		}
		try {
			for (Object resource : resources) {
				if (resource instanceof ResultSet) {
					((ResultSet) resource).close();
					System.out.println("\t释放资源：关闭ResultSet");
				}
				if (resource instanceof Statement) {
					((Statement) resource).close();
					System.out.println("\t释放资源：关闭Statement");
				}
				if (resource instanceof Connection) {
					((Connection) resource).close();
					System.out.println("\t释放资源：关闭Connection");
				}
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
