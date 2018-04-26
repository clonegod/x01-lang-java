package baeldung.copyonwrite.CopyOnWriteArrayList;


import org.junit.Test;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.assertj.core.api.Assertions.assertThat;


public class CopyOnWriteArrayListUnitTest {
	
	/**
	 * CopyOnWriteArrayList返回的迭代器时稳定的
	 * 获取迭代器之后，即使有其它线程并发修改元素集合，也不会发生异常。
	 * 新增加的元素对之前的迭代器将不可见。
	 * 
	 */
    @Test
    public void givenCopyOnWriteList_whenIterateAndAddElementToUnderneathList_thenShouldNotChangeIterator() {
        //given
        final CopyOnWriteArrayList<Integer> numbers =
          new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

        //when
        Iterator<Integer> iterator = numbers.iterator();
        numbers.add(10);

        //then
        List<Integer> result = new LinkedList<>();
        iterator.forEachRemaining(result::add);
        assertThat(result).containsOnly(1, 3, 5, 8);

        //and
        Iterator<Integer> iterator2 = numbers.iterator();
        List<Integer> result2 = new LinkedList<>();
        iterator2.forEachRemaining(result2::add);

        //then
        assertThat(result2).containsOnly(1, 3, 5, 8, 10);

    }
    
    /**
     * CopyOnWriteArrayList的迭代器上不允许执行删除操作
     */
    @Test(expected = UnsupportedOperationException.class)
    public void givenCopyOnWriteList_whenIterateOverItAndTryToRemoveElement_thenShouldThrowException() {
        //given
        final CopyOnWriteArrayList<Integer> numbers =
          new CopyOnWriteArrayList<>(new Integer[]{1, 3, 5, 8});

        //when
        Iterator<Integer> iterator = numbers.iterator();
        while (iterator.hasNext()) {
            iterator.remove();
        }
    }
}
