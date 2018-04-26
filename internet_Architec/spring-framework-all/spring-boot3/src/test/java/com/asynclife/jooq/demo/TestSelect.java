package com.asynclife.jooq.demo;

import static org.jooq.impl.DSL.*;
import static org.jooq.test.modle.tables.Author.AUTHOR;
import static org.jooq.test.modle.tables.Person.PERSON;
import static org.jooq.test.modle.tables.Book.BOOK;
import static org.jooq.test.modle.tables.Language.LANGUAGE;
import static org.jooq.test.modle.tables.BookToBookStore.BOOK_TO_BOOK_STORE;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.test.modle.tables.Book;
import org.jooq.test.modle.tables.Person;
import org.jooq.test.modle.tables.records.BookRecord;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestSelect {
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
	public void testSelect() {
		Result<Record3<String, String, Integer>> rs = 
			create.select(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME, count())
		      .from(AUTHOR)
		      .join(BOOK).on(BOOK.AUTHOR_ID.equal(AUTHOR.ID))
		      .join(LANGUAGE).on(LANGUAGE.CD.eq("DE"))
		      .and(BOOK.PUBLISHED_IN.greaterThan(1980))
		      .groupBy(AUTHOR.FIRST_NAME, AUTHOR.LAST_NAME)
		      .having(count().ge(1))
		      .orderBy(AUTHOR.LAST_NAME.asc().nullsFirst())
		      .limit(3)
		      .offset(0)
		      .forUpdate()
		      .fetch();
		
		for(Record3 r : rs) {
			System.out.println(r);
		}
	}
	
	@Test
	public void testSelectFromSingleTable() {
		BookRecord book = create.selectFrom(BOOK)
                .where(BOOK.TITLE.equal("swing-01"))
                .orderBy(BOOK.TITLE)
                .fetchAny();
		System.out.println(book);
	}
	
	@Test
	public void testSelectFromDual() {
		Result<Record1<Integer>> count = create.selectCount().fetch();
		System.out.println(count);
		
		Result<Record1<Integer>> zero = create.selectZero().fetch();
		System.out.println(zero);
		
		Result<Record1<Integer>> one = create.selectOne().fetch();
		System.out.println(one);
		
	}
	
	@Test
	public void testSelectDistinct() {
		Result<Record1<String>> titles = create.selectDistinct(BOOK.TITLE).from(BOOK).fetch();
		 for(Record1 r : titles) {
			 System.out.println(r.getValue(BOOK.TITLE));
		 }
	}
	
	@Test
	public void testSelectAll() {
		// Explicitly selects all columns available from BOOK
		 Result<Record> r1 = create.select().from(BOOK).fetch();
		 sop(r1);

		// Explicitly selects all columns available from BOOK and AUTHOR---笛卡尔积
		 Result<Record> r2 = create.select().from(BOOK, AUTHOR).fetch();
		 sop(r2);
		 Result<Record> r3 = create.select().from(BOOK).crossJoin(AUTHOR).fetch();
		 sop(r3);

		// Renders a SELECT * statement, as columns are unknown to jOOQ---未受JOOQ管理的表
		 Result<Record> r4 = create.select().from(table("BOOK")).fetch();
		 sop(r4);
	}
	
	@Test
	public void testSelectJoin() {
		// Call "join" directly on the AUTHOR table
		Result<Record> result = create.select()
		                         .from(AUTHOR.join(BOOK)
		                                     .on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)))
		                         .fetch();
		sop(result);
		
		
		// Call "join" on the type returned by "from"
		Result<Record> result2 = create.select()
		                         .from(AUTHOR)
		                         .join(BOOK)
		                         .on(BOOK.AUTHOR_ID.equal(AUTHOR.ID))
		                         .fetch();
		sop(result2);
	}
	
	@Test
	public void testSelectLeftOuterJoin() {
		// Call "join" on the type returned by "from"
		Result<Record> rs = create.select()
				.from(AUTHOR)
				.leftOuterJoin(BOOK)
				.on(BOOK.AUTHOR_ID.equal(AUTHOR.ID))
				.fetch();
		sop(rs);
	}
	
	@Test
	public void testSelectMultipleJoin() {
		// Nest joins and provide JOIN conditions only at the end
		Result<Record> rs = create.select()
		      .from(AUTHOR
		      .leftOuterJoin(BOOK
		        .join(BOOK_TO_BOOK_STORE) // BOOK先和BOOK_TO_BOOK_STORE内连接，得到中间表后，再用AUTHOR于中间表左连接
		        .on(BOOK_TO_BOOK_STORE.BOOK_ID.equal(BOOK.ID)))
		      .on(BOOK.AUTHOR_ID.equal(AUTHOR.ID)))
		      .fetch();
		sop(rs);
		
	}
	
	@Test
	public void testSelectOnKey() {
		Result<Record> rs = create.select()
	      .from(AUTHOR)
	      .join(BOOK).onKey()
	      .fetch();
		sop(rs);
	}
	
	@Test
	public void testSelectUsing() {
		// join(...).using(...)
		Result<Record> rs = create.select()
		      .from(AUTHOR)
		      .join(PERSON).using(AUTHOR.ID) // 通过2张表都有的同名字段进行连接，字段名称相同，值相同-> a.id=b.id
		      .fetch();
		sop(rs);
	}
	
	@Test
	public void testSelectNaturalJoin() {
		// naturalJoin(...)
		create.select()
		      .from(AUTHOR)
		      .naturalJoin(BOOK) // 通过2张表中所有相同的字段名进行连接
		      .fetch();
	}
	
	@Test
	public void testSelectWhere() {
		create.select()
	      .from(BOOK)
	      .where(BOOK.AUTHOR_ID.equal(1).and(
	    		  BOOK.TITLE.startsWith("java")))
	      .fetch();
		
		
		create.select()
	      .from(BOOK)
	      .where(BOOK.AUTHOR_ID.greaterOrEqual(2))
	      .and(BOOK.TITLE.startsWith("java"))
	      .or(BOOK.PUBLISHED_IN.lessThan(2000))
	      .fetch();
	}
	
	/*
	 - select 
	 - from  
	 - where 
	 - group by
	 - having
	 - order by
	 - limit offset
	 * */
	@Test
	public void testSelectGroupBy() {
		create.select(BOOK.AUTHOR_ID,  count())
	      .from(BOOK)
	      .groupBy(BOOK.AUTHOR_ID)
	      .fetch();
		
		
		// BOOK.TITLE 不是分组条件，也不是聚合函数，因此作为查询字段是不合适的，不建议使用---MYSQL才支持
		create.select(BOOK.AUTHOR_ID, BOOK.TITLE, count())
	      .from(BOOK)
	      .groupBy(BOOK.AUTHOR_ID)
	      .fetch();
		
		// select count(*) from `library`.`book` group by (select 1 from dual)
		create.selectCount()
	      .from(BOOK)
	      .groupBy() // group by 1 -> 只返回1条结果
	      .fetch();
		
	}
	
	@Test
	public void testSelectHaving() {
		create.select(BOOK.AUTHOR_ID, count())
	      .from(BOOK)
	      .groupBy(BOOK.AUTHOR_ID)
	      .having(count().greaterOrEqual(2))
	      .fetch();
		
		// 没有group，直接having
		create.select(BOOK.ID, BOOK.TITLE)
	      .from(BOOK)
	      .having(BOOK.TITLE.startsWith("java"))
	      .fetch();
	}
	
	@Test
	public void testSelectOrderBy() {
		create.select(BOOK.AUTHOR_ID, BOOK.TITLE)
	      .from(BOOK)
	      .orderBy(BOOK.AUTHOR_ID.asc(), BOOK.TITLE.desc())
	      .fetch();
	}
	
	@Test
	public void testSelectOrderByFieldIndex() {
		create.select(BOOK.AUTHOR_ID, BOOK.TITLE)
	      .from(BOOK)
	      .orderBy(one().asc(), inline(2).desc())
	      .fetch();
	}
	
	@Test
	public void testSelectOrderWithNulls() {
		create.select(
				AUTHOR.ID,
		         AUTHOR.FIRST_NAME,
		         AUTHOR.LAST_NAME)
		      .from(AUTHOR)
		      .orderBy(AUTHOR.LAST_NAME.asc(),
		               AUTHOR.FIRST_NAME.asc().nullsFirst())
		      .fetch();
	}
	
	@Test
	public void testSelectOrderByCase() {
		create.select()
	      .from(BOOK)
	      .orderBy(choose(BOOK.TITLE)
	               .when("swing-01", 1) // always appear on top
	               .otherwise(2).asc()) // ---数字代表优先级
	      .fetch();
		
		create.select()
		.from(BOOK)
		.orderBy(choose(BOOK.TITLE)
				.when("swing-01", 0) // always  appear on top
				.when("java-03", 1) // always  appear on second
				.otherwise(2).asc())
				.fetch();
	}
	
	@Test
	public void testSelectLimitOffset() {
		create.select().from(BOOK).limit(1).offset(2).fetch();
	}
	
	/**
	 *  instead of going to page 6 by using a record OFFSET， 
	 *  just fetch the record strictly after the previous page.
	 *  
	 *  http://blog.jooq.org/2013/10/26/faster-sql-paging-with-jooq-using-the-seek-method/
	 *  http://blog.jooq.org/2013/11/18/faster-sql-pagination-with-keysets-continued/
	 */
	@Test
	public void testSelectFastPagingBySeek() {
		create.select(AUTHOR.ID, AUTHOR.FIRST_NAME)
				.from(AUTHOR)
				.orderBy(AUTHOR.ID.asc())
				.seek(2) // where id > 2
				.limit(2)
				.fetch();
	}
	
	@Test
	public void testSelectUnion() {
		create.selectFrom(AUTHOR)
	      .orderBy(AUTHOR.DATE_OF_BIRTH.asc()).limit(1)
	      .union(
	    		  selectFrom(AUTHOR)
	    		  .orderBy(AUTHOR.DATE_OF_BIRTH.desc()).limit(1)
	      )
	      .orderBy(AUTHOR.YEAR_OF_BIRTH.asc().nullsLast())
	      .fetch();
	}
	
	
	
	private void sop(Result<Record> rs) {
		for (Iterator iterator = rs.iterator(); iterator.hasNext();) {
			Record record = (Record) iterator.next();
			System.out.println(record);
		}
	}
}
