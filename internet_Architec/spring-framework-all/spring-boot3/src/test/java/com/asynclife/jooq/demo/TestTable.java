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
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Record2;
import org.jooq.SQLDialect;
import org.jooq.Table;
import org.jooq.impl.DSL;
import org.jooq.test.modle.tables.Author;
import org.jooq.test.modle.tables.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestTable {
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
	public void testAlias() {
		// Declare your aliases before using them in SQL:
		Author a = AUTHOR.as("a");
		Book b = BOOK.as("b");

		// Use aliased tables in your statement
		create.select()
		      .from(a)
		      .join(b).on(a.ID.equal(b.AUTHOR_ID))
		      .where(a.YEAR_OF_BIRTH.greaterThan(1920)
		      .and(a.FIRST_NAME.equal("莫言")))
		      .orderBy(b.TITLE)
		      .fetch();
	}
	
	@Test
	public void testSubQuery() {
		create.select()
	      .from(BOOK)
	      .where(BOOK.AUTHOR_ID.equal(create
	             .select(AUTHOR.ID)
	             .from(AUTHOR)
	             .where(AUTHOR.LAST_NAME.equal("zhang"))))
	      .fetch();
	}
	
	// 来自, 源于... Derived
	@Test
	public void testDerivedTable() {
		Table<Record2<Integer, Integer>> nested =
			    create.select(BOOK.AUTHOR_ID, count().as("books"))
			          .from(BOOK)
			          .groupBy(BOOK.AUTHOR_ID).asTable("nested");

			create.select(nested.fields())
			      .from(nested)
			      .orderBy(nested.field("books"))
			      .fetch();
	}
	
	// 相关的-Correlated
	@Test
	public void testCorrelatedSubquery() {
		// The type of books cannot be inferred from the Select<?>
		Field<Object> books =
		    create.selectCount()
		          .from(BOOK)
		          .where(BOOK.AUTHOR_ID.equal(AUTHOR.ID))
		          .asField("books");

		create.select(AUTHOR.ID, books)
		      .from(AUTHOR)
		      .orderBy(books.desc(), AUTHOR.ID.asc())
		      .fetch();
	}
	
	@Test
	public void testColumnAlias() {
		Record record = create.select(
		         concat(AUTHOR.FIRST_NAME, val(" "), AUTHOR.LAST_NAME).as("author"),
		         count().as("books"))
		      .from(AUTHOR)
		      .join(BOOK).on(AUTHOR.ID.equal(BOOK.AUTHOR_ID))
		      .groupBy(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		      .fetchAny();
		
		System.out.println("Author : " + record.getValue("author"));
		System.out.println("Books  : " + record.getValue("books"));
	}
	

}
