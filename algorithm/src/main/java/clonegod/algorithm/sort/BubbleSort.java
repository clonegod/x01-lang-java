package clonegod.algorithm.sort;

import java.util.Arrays;

/**
基本思想：
	在要排序的一组数中，对当前还未排好序的范围内的全部数，自上而下对相邻的两个数依次进行比较和调整，让较大的数往下沉，较小的往上冒。
	即：每当两相邻的数比较后发现它们的排序与排序要求相反时，就将它们互换。
关键：
	每一次外层循环，都可以确定出一个该次最大的数，于是就可以定位到这个最大的数在数组中的角标位置。
	外层循环次数依次递减
 */
public class BubbleSort {
	
	public static void main(String[] args) {
		int[] nums = {2,49,38,65,97,76,13,27,49,78,34,12,64,5,4,62,99,98,54,56,17,18,23,34,15,35,25,53,51};  
		System.out.println("befoer:"+Arrays.toString(nums));
		
		int compareCount = 0;
		
		for(int i = 0; i < nums.length - 1; i++) { // 第n个数: i = 0. 外层循环每执行一遍就可以确定出一个最大的数
			for(int j = 0; j < nums.length-i-1; j++) { // 第n+1个数, j = 1
				compareCount++;
				if(nums[j] > nums[j+1]) { /** 比较内层循环中的两个相邻元素，前数大于后数，则交换 */
					swap(nums, j, j+1);
				}
			}
		}
		
		System.out.println("比较次数："+compareCount);
		System.out.println("after:"+Arrays.toString(nums));
	}

	private static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}
	
}
