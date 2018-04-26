package com.clonegod.unittest.junit;
import java.io.Closeable;
import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestFixturesExample {
	
  static class ExpensiveManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }

  static class ManagedResource implements Closeable {
    @Override
    public void close() throws IOException {}
  }
  
  // 初始化 class-level 资源对象
  private static ExpensiveManagedResource myExpensiveManagedResource;

  @BeforeClass
  public static void setUpClass() {
    System.out.println("@BeforeClass setUpClass");
    myExpensiveManagedResource = new ExpensiveManagedResource();
  }

  @AfterClass
  public static void tearDownClass() throws IOException {
    System.out.println("@AfterClass tearDownClass");
    myExpensiveManagedResource.close();
    myExpensiveManagedResource = null;
  }

  //初始化 method-level 资源对象
  private ManagedResource myManagedResource;
  
  @Before
  public void setUp() {
    this.println("@Before setUp");
    this.myManagedResource = new ManagedResource();
  }

  @After
  public void tearDown() throws IOException {
    this.println("@After tearDown");
    this.myManagedResource.close();
    this.myManagedResource = null;
  }
  
  private void println(String string) {
    System.out.println(string);
  }

  @Test
  public void test1() {
    this.println("@Test test1()");
  }

  @Test
  public void test2() {
    this.println("@Test test2()");
  }
}