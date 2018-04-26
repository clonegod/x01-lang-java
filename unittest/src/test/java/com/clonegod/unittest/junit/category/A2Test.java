package com.clonegod.unittest.junit.category;

import org.junit.Test;
import org.junit.experimental.categories.Category;

@Category({SlowTests.class, FastTests.class})
public class A2Test {
  @Test
  public void c() {

  }
}

