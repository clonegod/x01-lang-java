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
import org.jooq.DatePart;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.jooq.test.modle.tables.Author;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestFunctions {
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
	public void testAddDays() {
		create.select(currentTimestamp().add(3)).fetch(); //  adding days 
	}
	
	@Test
	public void testConcat() {
		create.select(concat("A", "B", "C")).fetch();
	}
	
	@Test
	public void testRegex() {
		create.selectFrom(BOOK).where(BOOK.TITLE.likeRegex("^.*JAva.*$")).fetch();
	}
	
	@Test
	public void testDateFunc() {
		
		// 当前时间
		create.select(currentDate()).fetch(); // 2015-07-19
		create.select(currentTime()).fetch(); // 11:20:15
		create.select(currentTimestamp()).fetch(); // 2015-07-19 11:20:15.0
		
		// 日期增减
		create.select(dateAdd(currentDate(), 1, DatePart.YEAR)).fetch();
		create.select(dateAdd(currentDate(), 1, DatePart.MONTH)).fetch();
		create.select(dateAdd(currentDate(), 1, DatePart.DAY)).fetch();
		
		// 日期间隔
		create.select(dateDiff(dateAdd(currentDate(), 1, DatePart.YEAR), dateAdd(currentDate(), 2, DatePart.DAY))).fetch();
		
		create.select(timestampAdd(currentTimestamp(), 20, DatePart.DAY)).fetch();
		
		create.select(timestampDiff(timestampAdd(currentTimestamp(), 100, DatePart.SECOND), 
				timestampAdd(currentTimestamp(), 200, DatePart.SECOND)))
				.fetch();
		
	}
	
	@Test
	public void testAggregate() {
		create.select(count()).from(AUTHOR).fetch(); // 统计所有行
		create.select(count(AUTHOR.FIRST_NAME)).from(AUTHOR).fetch(); // 统计指定列，不统计值为NULL的列
		create.select(countDistinct(AUTHOR.FIRST_NAME)).from(AUTHOR).fetch(); // 统计指定列，去重，不统计值为NULL的列
		
		
		create.select(BOOK.AUTHOR_ID, count())
	      .from(BOOK)
	      .groupBy(BOOK.AUTHOR_ID)
	      .fetch();
		
		create.select(max(BOOK.ID)).from(BOOK).fetch();
	}
	
	@Test
	public void testExists() {
		// 有书的作者
		create.select().from(AUTHOR).whereExists(
				create.selectOne().from(BOOK).where(BOOK.AUTHOR_ID.eq(AUTHOR.ID)))
				.fetch();
		
		// 没有书的作者
		create.select().from(AUTHOR).whereNotExists(
				create.selectOne().from(BOOK).where(BOOK.AUTHOR_ID.eq(AUTHOR.ID)))
				.fetch();
	}
	
}
