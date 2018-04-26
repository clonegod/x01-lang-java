package clonegod.algorithm.sort;

import java.util.Arrays;

/**
 * 快速排序
 * 
 * 基本思想：
 * 1．先从数列中取出一个数作为基准数。 
 * 2．分区过程，将比这个数大的数全放到它的右边，小于或等于它的数全放到它的左边。
 * 3．再对左、右区间分别重复第二步，直到各区间只有一个数。
 * 
 * 区间内部：
 * 	有一个左指针不断往左移动（当元素值小于pivot）
 * 	一个右指针不断往左移动（当元素值大于pivot）
 * 	有一个pivot轴进行二次分区
 * 
 */
public class QuickSort {
	
	static int compareCount = 0;
	
	public void sort(int[] inputArr) {
		if (inputArr == null || inputArr.length == 0) {
			return;
		}
		quickSort(inputArr, 0, inputArr.length - 1);
	}

	public static void quickSort(int[] array, int lowerIndex, int higherIndex) {
		int i = lowerIndex;
		int j = higherIndex;
		int pivot = array[lowerIndex + (higherIndex - lowerIndex) / 2];
		// Divide into two arrays
		// 比较关系必须是：<=
		while(i <= j) { 
			// 从左往右，如果比pivot小，则继续比较下一个
			while(array[i] < pivot) { // 当遇到比pivot大的数，停止向后搜索
				i++; // 向后移动指针
			}
			// 从右往左，如果比pivot大，则继续比较上一个，直到找到某个比pivot小的数停止
			while(array[j] > pivot) { // 当遇到比pivot小的数，停止搜索
				j--; // 向前移动指针
			}
			// 如果i的位置小于j的位置，则交换并缩小范围继续查询：
			// 将i指针向右，j指针向左分别移动1个索引位。因为i之前的元素已经确保比pivot小，j之后的元素已经确保比pivot大了
			// 比较关系必须是：<=
			if(i <= j) {
				compareCount++;
				exchangeNumbers(array, i, j);
                //move index to next position on both sides
                i++;
                j--;
			}
		}
		// 到此步，i > j (i已经在j的后面了)
		// 因此，现在可以将原始数组划分为左右两个数组，对它们分别进行排序:[lowerIndex, j], [i, higherIndex]
		// call quickSort() method recursively
        if (lowerIndex < j)
            quickSort(array, lowerIndex, j);
        if (i < higherIndex)
            quickSort(array, i, higherIndex);
	}
	private static void exchangeNumbers(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

	public static void main(String[] args) {
		QuickSort sorter = new QuickSort();
        int[] input = {2,49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
        sorter.sort(input);
        System.out.println(Arrays.toString(input));
        System.out.println(compareCount);
	}
}
