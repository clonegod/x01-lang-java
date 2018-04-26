package demo.guava.concurrency.asynctransforms;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Strings;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import demo.guava.basic.object.Person;

public class PersonAsyncFuntion implements AsyncFunction<String, Person> {
	
	// 将字符串转换为Person
	@Override
	public ListenableFuture<Person> apply(String input) throws Exception {
		SettableFuture<Person> listenableFuture = SettableFuture.create();
		if(Strings.isNullOrEmpty(input)) {
			System.err.println("input is Null");
			throw new NullPointerException();
		}
		TimeUnit.MILLISECONDS.sleep(ThreadLocalRandom.current().nextInt(1000));
		listenableFuture.set(new Person(input, 1));
		return listenableFuture;
	}
}
