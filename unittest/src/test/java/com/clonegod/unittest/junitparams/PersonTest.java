package com.clonegod.unittest.junitparams;

import static org.assertj.core.api.Assertions.assertThat;

import junitparams.naming.TestCaseName;
import org.junit.*;
import org.junit.runner.*;

import junitparams.*;

@RunWith(JUnitParamsRunner.class)
public class PersonTest {

	@Test
	@Parameters({ "17, false", "22, true" })
	public void isAdultAgeDirect(int age, boolean valid) throws Exception {
		assertThat(new Person(age).isAdult()).isEqualTo(valid);
	}

	@Test
	@Parameters(method = "adultValues")
	public void isAdultAgeDefinedMethod(int age, boolean valid) throws Exception {
		assertThat(new Person(age).isAdult()).isEqualTo(valid);
	}

	@NamedParameters("grownups")
	private Object[] adultValues() {
		return new Object[] { new Object[] { 17, false }, new Object[] { 22, true } };
	}

	@Test
	@Parameters
	public void isAdultAgeDefaultMethod(int age, boolean valid) throws Exception {
		assertThat(new Person(age).isAdult()).isEqualTo(valid);
	}

	@SuppressWarnings("unused")
	private Object[] parametersForIsAdultAgeDefaultMethod() {
		return adultValues();
	}

	@Test
	@Parameters(source = PersonProvider.class)
	public void personIsAdult(Person person, boolean valid) {
		assertThat(person.isAdult()).isEqualTo(valid);
	}


	@Test
	@Parameters(method = "adultValues")
	@TestCaseName("Is person with age {0} adult? It's {1} statement.")
	public void isAdultWithCustomTestName(int age, boolean valid) throws Exception {
		assertThat(new Person(age).isAdult()).isEqualTo(valid);
	}

}