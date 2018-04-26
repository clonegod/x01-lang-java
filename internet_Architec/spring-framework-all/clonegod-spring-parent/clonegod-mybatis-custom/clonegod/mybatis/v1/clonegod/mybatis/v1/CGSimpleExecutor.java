package clonegod.mybatis.v1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import clonegod.mybatis.dao.Author;
import clonegod.mybatis.utils.JDBCUtil;

public class CGSimpleExecutor implements CGExecutor {

	@Override
	public <T> T query(String statement, Object parameter) {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
        	connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(statement);
            preparedStatement.setInt(1, Integer.parseInt(parameter.toString()));
            rs = preparedStatement.executeQuery();
            if (rs.next()) {
                Author author = new Author();
                author.setId(rs.getInt("id"));
                author.setUsername(rs.getString("username"));
                author.setPassword(rs.getString("password"));
                author.setEmail(rs.getString("email"));
                return (T) author;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        	JDBCUtil.close(rs, preparedStatement, connection);
        }
        return null;
	}

}
