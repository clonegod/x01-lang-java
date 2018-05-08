package com.asynclife.jooq.demo;

import static org.jooq.impl.DSL.*;
import static org.jooq.test.modle.tables.Author.AUTHOR;
import static org.jooq.test.modle.tables.Book.BOOK;
import static org.jooq.test.modle.tables.AuthorArchive.AUTHOR_ARCHIVE;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.jooq.DSLContext;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.test.modle.tables.Book;
import org.jooq.test.modle.tables.records.AuthorRecord;
import org.jooq.test.modle.tables.records.BookRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.asynclife.jooq.demo.pojo.MyAuthor;
import com.asynclife.jooq.demo.pojo.MyBook2;

public class TestInsert {
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
	public void testInsertSingle() {
		create.insertInto(AUTHOR,
		        AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.DATE_OF_BIRTH, AUTHOR.YEAR_OF_BIRTH, AUTHOR.DISTINGUISHED)
		      .values(mockAuthor())
		      .execute();
		
	}
	
	@Test
	public void testInsertMultiple() {
		create.insertInto(AUTHOR,
				AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, AUTHOR.DATE_OF_BIRTH, AUTHOR.YEAR_OF_BIRTH, AUTHOR.DISTINGUISHED)
				.values(mockAuthor())
				.values(mockAuthor())
				.execute();
		
	}
	
	@Test
	public void testInsertBySet() {
		create.insertInto(AUTHOR)
	      .set(AUTHOR.FIRST_NAME, "Hermann")
	      .set(AUTHOR.LAST_NAME, "Hesse")
	      .set(AUTHOR.DATE_OF_BIRTH, new java.sql.Date(2010, 1, 2))
	      .set(AUTHOR.YEAR_OF_BIRTH, 2010)
	      .set(AUTHOR.DISTINGUISHED, 0)
	      .newRecord()
	      .set(AUTHOR.FIRST_NAME, "Alfred")
	      .set(AUTHOR.LAST_NAME, "Döblin")
	      .set(AUTHOR.DATE_OF_BIRTH, new java.sql.Date(1990, 5, 20))
	      .set(AUTHOR.YEAR_OF_BIRTH, 1990)
	      .set(AUTHOR.DISTINGUISHED, 0)
	      .execute();
	}
	
	@Test
	public void testInsertBySelect() {
		create.insertInto(AUTHOR_ARCHIVE)
	      .select(selectFrom(AUTHOR).where(AUTHOR.DISTINGUISHED.isTrue()))
	      .execute();
	}
	
	@Test
	public void testInsertIfDuplicate() {
		// Add a new author called "Koontz" with ID 3.
		// If that ID is already present, update the author's name
		create.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.LAST_NAME)
		      .values(3, "Koontz")
		      .onDuplicateKeyUpdate()
		      .set(AUTHOR.LAST_NAME, "Koontz")
		      .execute();
	}
	
	@Test
	public void testInsertIfDuplicateIgnore() {
		// Add a new author called "Koontz" with ID 3.
		// If that ID is already present, ignore the INSERT statement
		create.insertInto(AUTHOR, AUTHOR.ID, AUTHOR.LAST_NAME)
		      .values(3, "Koontz")
		      .onDuplicateKeyIgnore()
		      .execute();
	}
	
	@Test
	public void testInsertReturningID() {
		// Add another author, with a generated ID
		Record record =
		create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		      .values("Charlotte", "Roche")
		      .returning(AUTHOR.ID)
		      .fetchOne();

		System.out.println(record.getValue(AUTHOR.ID));
	}
	
	@Test
	public void testInsertReturningMore() {
		// For some RDBMS, this also works when inserting several values
		// The following should return a 2x2 table
		Result<AuthorRecord> result =
		create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		      .values("Johann Wolfgang", "von Goethe")
		      .values("Friedrich", "Schiller")
		      // You can request any field. Also trigger-generated values
		      .returning(AUTHOR.ID, AUTHOR.LAST_NAME)
		      .fetch();
		
		for(AuthorRecord ar : result) {
			System.out.println(ar);
		}
	}
	
	
	@Test
	public void testBatchInsertQuery() {
		// 1. several queries
		// ------------------
		create.batch(
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).values("Erich"  , "Gamma"    ),
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).values("Richard", "Helm"     ),
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).values("Ralph"  , "Johnson"  ),
			create.insertInto(AUTHOR, AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME).values("John"   , "Vlissides"))
		.execute();
		
		
	}
	
	/**
	 * 批量插入
	 */
	@Test
	public void testBatchInsert() {
		MyAuthor author = new MyAuthor();
		author.firstName = "a";
		author.lastName = "b";
		author.dateOfBirth = new java.util.Date();
		author.distinguished = false;
		
		MyBook2 book1 = new MyBook2(null, 1, "title1", 19999, 2);
		MyBook2 book2 = new MyBook2(null, 1, "title2", 19999, 1);
		
		author.books.add(book1);
		author.books.add(book2);
		
		
		int aid = create.insertInto(AUTHOR)
			.values(null, author.firstName, author.lastName, new java.sql.Date(new java.util.Date().getTime()), author.yearOfBirth, 1)
			.returning(AUTHOR.ID)
			.fetchOne()
			.getValue(AUTHOR.ID);
		
		List<BookRecord> books = new ArrayList<BookRecord>();
		
		for(MyBook2 b : author.books) {
			BookRecord br = create.newRecord(BOOK, b);
			br.setAuthorId(aid);
			br.setLanguageId(1);
			books.add(br);
		}
		create.batchInsert(books).execute();
	}
	
	
	
	
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Collection mockAuthor() {
		
		Random r = new Random();
		
		int[] years = {1970,1980};
		int[] monthes = {1,2,4,5,6,7,8,9,10,11,12};
		int[] days = monthes;
		
		int year = years[r.nextInt(years.length)];
		int month = monthes[r.nextInt(monthes.length)];
		int day = days[r.nextInt(days.length)];
		
		DateTime dt = new DateTime( year + "-" + month + "-" + day);
		
		String[] lastNames = {"zhang", "li"};
		
		List list = new ArrayList();
		list.add("author-"+r.nextInt(10));
		list.add(lastNames[r.nextInt(2)]);
		list.add(new Date(dt.getMillis()));
		list.add(year);
		list.add(r.nextInt(1));
		
		
		return list;
	}
}
