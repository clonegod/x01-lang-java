package com.asynclife.jooq.example;

import static org.jooq.impl.DSL.selectFrom;
import static org.jooq.impl.DSL.using;
import static org.jooq.test.modle.tables.Author.AUTHOR;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JooqInsertTest {
	
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
	public void testInsertBySingleValues() {
	    DSLContext create = using(conn, SQLDialect.MYSQL);
	   
	    create.insertInto(AUTHOR, 
	    		AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
	    		.values("zhang", "san")
	    		.execute();
    
	}
	
	
	@Test
	public void testInsertByMultipleValues() {
		DSLContext create = using(conn, SQLDialect.MYSQL);
		
		create.insertInto(AUTHOR, 
				 AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
				.values("li", "san")
				.values("wang", "san")
				.execute();
		
	}
	
	@Test
	public void testInsertBySet() {
		DSLContext create = using(conn, SQLDialect.MYSQL);
		
		create.insertInto(AUTHOR)
		.set(AUTHOR.FIRST_NAME, "Hermann")
		.set(AUTHOR.LAST_NAME, "Hesse")
		.newRecord()
		.set(AUTHOR.FIRST_NAME, "Alfred")
		.set(AUTHOR.LAST_NAME, "Döblin")
		.execute();
		
	}
	
	/*@Test
	public void testInsertBySelect() {
		DSLContext create = using(conn, SQLDialect.MYSQL);
		
		create.insertInto(AUTHOR)
		.select(selectFrom(AUTHOR).where(AUTHOR.ID.eq(1)))
		.execute();
		
	}*/
	
	@Test
	public void testSaveOrUpdateWhenDuplicate() {
		// Add a new author called "Koontz" with ID 3.
		// If that ID is already present, update the author's name
		create.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.LAST_NAME)
		.values(3, "Koontz")
		.onDuplicateKeyUpdate()
		.set(AUTHOR.LAST_NAME, "Koontz")
		.execute();
	}
	
	
	@Test
	public void testInsertAndReturn() {
		// Add another author, with a generated ID
		Record record =
		create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		.values("Charlotte", "Roche")
		.returning(AUTHOR.ID)
		.fetchOne();
		System.out.println(record.getValue(AUTHOR.ID));
		
		// For some RDBMS, this also works when inserting several values
		// The following should return a 2x2 table
		Result<?> result =
		create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		.values("Johann Wolfgang", "von Goethe")
		.values("Friedrich", "Schiller")
		// You can request any field. Also trigger-generated values
		.returning(AUTHOR.ID, AUTHOR.LAST_NAME)
		.fetch();
		System.out.println(result.getValue(0, AUTHOR.ID));
		System.out.println(result.getValue(1, AUTHOR.ID));
		
		
	}
}
