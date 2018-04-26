package com.asynclife.jooq.example;

import static org.jooq.impl.DSL.field;
import static org.jooq.impl.DSL.table;
import static org.jooq.test.modle.tables.Author.AUTHOR;
import static org.jooq.test.modle.tables.Book.BOOK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.test.modle.Keys;
import org.jooq.test.modle.tables.records.AuthorRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JooqSimpleTest {
	DSLContext create = null;
	Connection conn = null;

	@Before
	public void setUp() {
		String userName = "root";
		String password = "root123";
		String url = "jdbc:mysql://localhost:3306/library";

		
		// Connection is the only JDBC resource that we need
		// PreparedStatement and ResultSet are handled by jOOQ, internally
		try {
			conn = DriverManager.getConnection(url, userName,
					password);
			if (create == null)
				create = DSL.using(conn, SQLDialect.MYSQL);
		}

		// For the sake of this tutorial, let's keep exception handling simple
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@After
	public void setDone() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGenSQL() {
		// Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
		String sql = create.select(field("BOOK.TITLE"), field("AUTHOR.FIRST_NAME"), field("AUTHOR.LAST_NAME"))
		                   .from(table("BOOK"))
		                   .join(table("AUTHOR"))
		                   .on(field("BOOK.AUTHOR_ID").equal(field("AUTHOR.ID")))
		                   .where(field("BOOK.PUBLISHED_IN").equal(1948))
		                   .getSQL();
		
		System.out.println(sql);
	}
	
	@Test
	public void testUseDatabaseSchema() {
		// Fetch a SQL string from a jOOQ Query in order to manually execute it with another tool.
		String sql = create.select(BOOK.TITLE, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		                   .from(BOOK)
		                   .join(AUTHOR)
		                   .on(BOOK.AUTHOR_ID.equal(AUTHOR.ID))
		                   .where(BOOK.PUBLISHED_IN.equal(1948))
		                   .getSQL();
		
		System.out.println(sql);
	}
	
	@Test
	public void testRunSQLWithJooq() throws SQLException {
		
		// Use your favourite tool to construct SQL strings:
		String sql = "SELECT title, first_name, last_name FROM book JOIN author ON book.author_id = author.id " +
		             "WHERE book.published_in = 1984";

		// Fetch results using jOOQ
		Result<Record> resultByDSL = create.fetch(sql);
		System.out.println(resultByDSL);

		// Or execute that SQL with JDBC, fetching the ResultSet with jOOQ:
		ResultSet rs = conn.createStatement().executeQuery(sql);
		Result<Record> resultByJdbc = create.fetch(rs);
		System.out.println(resultByJdbc);
		
	}
	
	@Test
	public void testComplextQuery() {
		// Fetch all authors
		for (AuthorRecord author : create.fetch(AUTHOR)) {

		    // Skip previously distinguished authors
		    if (author.getDistinguished() !=null && (int) author.getDistinguished() == 1)
		        continue;
		  
		    // Check if the author has written more than 5 books
		    if (author.fetchChildren(Keys.FK_BOOK_AUTHOR).size() > 5) {
		    
		        // Mark the author as a "distinguished" author
		        author.setDistinguished(1);
		        author.store(); // save or update author record
		    }
		}
	}
}
