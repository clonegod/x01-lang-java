package demo.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

import clonegod.framework.dal.dao.Author;
import clonegod.mybatis.utils.JDBCUtil;

@FixMethodOrder
public class JDBCExample {
	
	private static Author author;

	@BeforeClass
	public static void init() {
		author = new Author().withUsername("alice")
					.withPassword("alice123")
					.withEmail("alice@mybatis.com");
	}
	
	@Test
	public void test01_ClearDB() throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		try {
			connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("DELETE FROM author where 1 = 1");
			preparedStatement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			JDBCUtil.close(preparedStatement, connection);
		}
	}
	
	@Test
    public void test02_Insert() throws SQLException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
        	connection = JDBCUtil.getConnection();
			connection.setAutoCommit(false);
			preparedStatement = connection.prepareStatement("INSERT INTO author (username, password, email) VALUES (?,?,?)");
			preparedStatement.setString(1, author.getUsername());
			preparedStatement.setString(2, "hashcode:"+author.getPassword().hashCode());
			preparedStatement.setString(3, author.getPassword());
			int n = preparedStatement.executeUpdate();
			Assert.assertEquals(n, 1);
			connection.commit();
		} catch (SQLException e) {
			connection.rollback();
			throw e;
		} finally {
			JDBCUtil.close(preparedStatement, connection);
		}
    }
	
	@Test
    public void test03_Query() throws SQLException {
    	Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;
        try {
        	int authorId = 1;
        	connection = JDBCUtil.getConnection();
            preparedStatement = connection.prepareStatement(
            		"SELECT username, password, email FROM author WHERE id = ?");
            preparedStatement.setInt(1, authorId);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Author author = new Author();
                author.setId(authorId);
                author.setUsername(rs.getString(1));
                author.setPassword(rs.getString(2));
                author.setEmail(rs.getString(3));
                System.out.println(author.toString());
            }
        } catch (Exception e) {
            throw e;
        } finally {
        	JDBCUtil.close(rs, preparedStatement, connection);
        }
    }
	
}
