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
		System.out.println(String.format("\nindex of %d in array is %d", target, index));
	}
	
	public static int search(int[] sortedArray, int key) {
		int lower = 0;
		int upper = sortedArray.length - 1;
		int mid = 0;
		while(true) {
			mid = (lower + upper) / 2;
			if(lower > upper) {
				System.out.print("\nElement=" + key + " not found");
                return -1; // 没有找到，返回-1
			} else if(sortedArray[mid] == key) {
				System.out.print("\nElement=" + key + " found at position="   + mid);
                return mid;
			} else if(sortedArray[mid] < key) {
				lower = mid + 1; // 中间数小于要查找的数，则mid+1作为新的lower
			} else {
				upper = mid - 1; // 中间数大于要查找的数，则mid-1作为新的upper
			}
		}
	}
}
