package com.asynclife.jooq.demo;

import static org.jooq.impl.DSL.*;
import static org.jooq.test.modle.tables.Author.AUTHOR;
import static org.jooq.test.modle.tables.Person.PERSON;
import static org.jooq.test.modle.tables.Book.BOOK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Random;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestUpdate {
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
	public void testUpdate()  {
		create.update(AUTHOR)
	      .set(AUTHOR.FIRST_NAME, "Hermann")
	      .set(AUTHOR.LAST_NAME, "Hesse")
	      .set(AUTHOR.YEAR_OF_BIRTH, new Random().nextInt(2000))
	      .where(AUTHOR.ID.greaterOrEqual(50))
	      .execute();
	}
	
	@Test
	public void testUpdateBySelect() {
		create.update(AUTHOR)
	      .set(AUTHOR.FIRST_NAME,
	         select(PERSON.NAME)
	        .from(PERSON)
	        .where(PERSON.ID.equal(AUTHOR.ID))
	      )
	      .where(AUTHOR.ID.equal(3))
	      .execute();
	}
	
}
