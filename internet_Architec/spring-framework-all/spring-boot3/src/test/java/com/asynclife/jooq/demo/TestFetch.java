package com.asynclife.jooq.demo;

import static org.jooq.test.modle.tables.Author.AUTHOR;
import static org.jooq.test.modle.tables.Book.BOOK;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.test.modle.Keys;
import org.jooq.test.modle.tables.records.AuthorRecord;
import org.jooq.test.modle.tables.records.BookRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.asynclife.jooq.demo.pojo.MyAuthor;
import com.asynclife.jooq.demo.pojo.MyBook1;
import com.asynclife.jooq.demo.pojo.MyBook2;

public class TestFetch {
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
	
	
	/**
	 * 将结果集拆分到不同的Record实体中
	 */
	@Test
	public void testMappingRecordToEachSpecifyRecord() {
		// Join two tables
		Record record = create.select()
		                      .from(BOOK)
		                      .join(AUTHOR).on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
		                      .where(BOOK.ID.equal(1))
		                      .fetchOne();

		// "extract" the two individual strongly typed TableRecord types from the denormalised Record:
		BookRecord book = record.into(BOOK);
		AuthorRecord author = record.into(AUTHOR);

		// Typesafe field access is now possible:
		System.out.println("Title       : " + book.getTitle());
		System.out.println("Published in: " + book.getPublishedIn());
		System.out.println("Author      : " + author.getFirstName() + " " + author.getLastName());
	}
	
	
	/**
	 * 属性名称与字段名称匹配的将被自动注入 --- 依赖与名称的匹配
	 */
	@Test
	public void testFetchIntoPOJOBySameFiledName() {
		MyBook1 myBook = create.selectFrom(BOOK).fetchAny().into(MyBook1.class);
		System.out.println(myBook);
	}
	
	/**
	 * use this example!!!
	 * 通过构造函数注入，构造函数的参数个数与类型必须与查询字段完全匹配
	 * --- 构造函数参数名不一致时，配置@ConstructorProperties(...)进行转化
	 * 
	 * 要求：
	 * 	如果POJO有默认的构造函数，则不会使用带参数的构造函数，将会通过字段名匹配规则对POJO属性进行赋值 
	 */
	@Test
	public void testFetchIntoPoJoByConstructor() {
		// fetchAny 返回任一记录
		MyBook2 myBook = create.selectFrom(BOOK).fetchAny().into(MyBook2.class);
		System.out.println(myBook);
		
		// 返回集合
		List<MyBook2> books =  create.selectFrom(BOOK).fetchInto(MyBook2.class);
		for(MyBook2 book : books) {
			System.out.println(book);
		}
		
	}
	
	
	/**
	 * this is useful
	 * 
	 * 只用1条SQL，进行关联查询
	 */
	@Test 
	public void testFetchResultOfJoinTableToPOJO() {
		// Join two tables
		Result<Record> result = create.select()
		                      .from(AUTHOR)
		                      .join(BOOK).on(BOOK.AUTHOR_ID.eq(AUTHOR.ID))
		                      .where(AUTHOR.ID.equal(1))
		                      .fetch();
		MyAuthor myAuthor = null;
		for(Record r : result) {
			if(myAuthor == null) {
				myAuthor = r.into(MyAuthor.class);
			}
			myAuthor.books.add(r.into(MyBook2.class));
		}
		
		System.out.println(myAuthor);
	}
	
	/**
	 * 先查出主表
	 * 在用外键查询子表
	 * 发出2条SQL
	 */
	@Test
	public void testFetchChildren() {
		AuthorRecord ar = create.selectFrom(AUTHOR).where(AUTHOR.ID.equal(1)).fetchOne();
		Result<BookRecord> books = ar.fetchChildren(Keys.FK_BOOK_AUTHOR);
		
		MyAuthor author = ar.into(MyAuthor.class);
		
		for(BookRecord br : books) {
			MyBook2 book = br.into(MyBook2.class);
			author.books.add(book);
		}
		
		System.out.println(author);
	}
	
	/**
	 * 先查子表，再根据外键关系查询主表
	 */
	@Test
	public void testFetchParent() {
		
		BookRecord br = create.selectFrom(BOOK).fetchAny();
		AuthorRecord ar = br.fetchParent(Keys.FK_BOOK_AUTHOR);
		
		MyBook2 book = br.into(MyBook2.class);
		MyAuthor author = ar.into(MyAuthor.class);
		
		book.author = author;
		
		System.out.println(book);
		
	}
	
	@Test
	public void testInsertPOJOTo() {
		MyBook2 book = new MyBook2(null, 3, "JOOQ1", 2013, 2);
		
		BookRecord brecord = create.newRecord(BOOK);
		brecord.setAuthorId((int)book.aid);
		brecord.setLanguageId((int)book.lanId);
		brecord.setPublishedIn((int)book.publishedIn);
		brecord.setTitle(book.title);
		
		brecord.store();
	}
	
	
	/**
	 * this is very good!!!
	 * 返回分组后的结果
	 */
	@Test
	public void testFetchGroups() {
		// Group by AUTHOR_ID and list all books written by any author:
		Map<Integer, Result<BookRecord>> group1 = create.selectFrom(BOOK).fetch().intoGroups(BOOK.AUTHOR_ID);
		Map<Integer, Result<BookRecord>> group2 = create.selectFrom(BOOK).fetchGroups(BOOK.AUTHOR_ID);
		
		Map<Integer, List<String>>       group3 = create.selectFrom(BOOK).fetch().intoGroups(BOOK.AUTHOR_ID, BOOK.TITLE);
		Map<Integer, List<String>>       group4 = create.selectFrom(BOOK).fetchGroups(BOOK.AUTHOR_ID, BOOK.TITLE);
		
	}
	
	
	/**
	 * In practice!!!
	 */
	@Test
	public void testFetchAuthorGroupByLastName() {
		Map<String, Result<Record>>  authors = create.select()
												.from(AUTHOR)
												.join(BOOK).onKey()
												.fetchGroups(AUTHOR.LAST_NAME);
		List<MyAuthor> authorList = new ArrayList<MyAuthor>();
		
		for(String lastName : authors.keySet()) {
			 Result<Record> arecords = authors.get(lastName);
			 MyAuthor author = null;
			 for(Record ar : arecords) {
				 if(author == null)
					 author = ar.into(MyAuthor.class);
				 MyBook2 book = ar.into(MyBook2.class);
				 author.books.add(book);
			 }
			 authorList.add(author);
		}
		
		System.out.println(authorList);
	}
	
}
