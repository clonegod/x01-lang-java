package clonegod.mybatis.v2.statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import clonegod.mybatis.v2.config.CGConfiguration;
import clonegod.mybatis.v2.config.CGMapperRegistry;
import clonegod.mybatis.v2.result.CGResultSetHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CGStatementHandler implements StatementHandler {
    final CGConfiguration configuration;

    private final CGResultSetHandler resultSetHandler;

    public CGStatementHandler(CGConfiguration configuration) {
        this.configuration = configuration;
        resultSetHandler = new CGResultSetHandler(configuration);
    }

    @Override
    public <T> T query(CGMapperRegistry.MapperData<T> mapperData, Object parameter) throws Exception {
    	Connection conn = null;
    	PreparedStatement pstmt = null;
        try {
            //JDBC
            conn = getConnection();
            //TODO ParamenterHandler
            String sql = String.format(mapperData.getSql(), Integer.parseInt(String.valueOf(parameter)));
            pstmt = conn.prepareStatement(sql);
            pstmt.execute();
            log.info("===>>> execute sql: {}", sql);
            //ResultSetHandler
            return resultSetHandler.handle(pstmt.getResultSet(), mapperData.getType());
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        	pstmt.close();
        	conn.close();
        }
        return null;
    }


    public Connection getConnection() throws SQLException {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/blogs?useUnicode=true&characterEncoding=utf-8&useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = "alice";
        String password = "alice123";
        Connection conn = null;
        try {
            Class.forName(driver); //classLoader,加载对应驱动
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return conn;
    }
}
