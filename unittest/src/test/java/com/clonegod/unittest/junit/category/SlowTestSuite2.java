package com.clonegod.unittest.junit.category;

import org.junit.experimental.categories.Categories;
import org.junit.experimental.categories.Categories.ExcludeCategory;
import org.junit.experimental.categories.Categories.IncludeCategory;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Categories.class)
@IncludeCategory(SlowTests.class)
@ExcludeCategory(FastTests.class)
@SuiteClasses( { A1Test.class, A2Test.class }) // Note that Categories is a kind of Suite
public class SlowTestSuite2 {
  // Will run A.b, but not A.a or B.c
}