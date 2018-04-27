package demo.guava.functional.supplier;

import java.util.Map;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.base.Supplier;

import demo.guava.functional.State;
import demo.guava.functional.predicate.RegionPredicate;

public class ComposedPredicateSupplier implements Supplier<Predicate<String>> {
	@Override
	public Predicate<String> get() {
		Map<String, State> stateMap = State.getStates();
		Function<String, State> mf = Functions.forMap(stateMap);
		return Predicates.compose(new RegionPredicate(), mf);
	}
}