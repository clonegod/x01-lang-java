package clonegod.algorithm.search;

import java.util.Arrays;

import clonegod.algorithm.sort.QuickSort;

/**
 * 算法描述：
 * 	折半查找的前提条件是在一个有序的序列中。
 * 	首先确定待查记录所在的区间，然后逐步的缩小范围区间直到找到或者找不到该记录为止。
 * 
 */
public class BinarySearch {
	public static void main(String[] args) {
		int[] input = {2,49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};
		
		QuickSort.quickSort(input, 0, input.length-1);
		
		int[] sortedArray = input;
		System.out.println(Arrays.toString(sortedArray));
		
		int target = 27;
		int index = search(sortedArray, target);
		System.out.println(String.format("index of %d in array is %d", target, index));
	}
	
	public static int search(int[] sortedArray, int targetElement) {
		int lower = 0;
		int upper = sortedArray.length - 1;
		while(lower <= upper) {
			int mid = (lower + upper) / 2;
			if(sortedArray[mid] < targetElement) {
				lower = mid + 1;
			}
			else if(sortedArray[mid] > targetElement) {
				upper = mid - 1;
			}
			else {
				return mid;
			}
		}
		return -1;
	}
}
