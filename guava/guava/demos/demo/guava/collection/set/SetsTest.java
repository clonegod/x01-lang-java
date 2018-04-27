package demo.guava.collection.set;

import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class SetsTest {
	
	@Test
	public void testSetDifference() {
		Set<String> s1 = Sets.newHashSet("1","2","3");
		Set<String> s2 = Sets.newHashSet("2","3","4");
		SetView<String> elementsOnlyInSet1 = Sets.difference(s1, s2);
		System.out.println(elementsOnlyInSet1);
	}
	
	@Test
	public void testSetSymmetricDifference() {
		Set<String> s1 = Sets.newHashSet("1","2","3");
		Set<String> s2 = Sets.newHashSet("2","3","4");
		SetView<String> elementsNotInBoth = Sets.symmetricDifference(s1, s2);
		System.out.println(elementsNotInBoth);
	}
	
	@Test
	public void testIntersection(){
		Set<String> s1 = Sets.newHashSet("1","2","3");
		Set<String> s2 = Sets.newHashSet("3","2","4");
		Sets.SetView<String> sv = Sets.intersection(s1,s2);
		assertThat(sv.size()==2 && sv.contains("2") && sv.contains("3"), is(true));
	}
	
	@Test
	public void testUnion(){
		Set<String> s1 = Sets.newHashSet("1","2","3");
		Set<String> s2 = Sets.newHashSet("3","2","4");
		Sets.SetView<String> sv = Sets.union(s1,s2);
		assertThat(sv.size()==4 &&
				sv.contains("2") &&
				sv.contains("3") &&
				sv.contains("4") &&
				sv.contains("1"), is(true));
	}
}
