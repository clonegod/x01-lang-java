package strategy.sort;

import java.util.Collection;

public class Sorter {
	
	private SortStrategy sortStrategy;
	
	public Sorter(SortStrategy sortStrategy) {
		this.sortStrategy = sortStrategy;
	}

	public void sortNow(Collection<?> c) {
		sortStrategy.sort();
	}

	public SortStrategy getSortStrategy() {
		return sortStrategy;
	}

	public void setSortStrategy(SortStrategy sortStrategy) {
		this.sortStrategy = sortStrategy;
	}
	
	
}
