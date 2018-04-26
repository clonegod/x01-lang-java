package com.aysnclife.dataguru.jvm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

/**
 * 打印整数的二进制表示
 *
 */
public class JVM01 {

	@Test
	public void testToBinaryString() {
		int a = 133;
		System.out.println(toBinaryStr(a));
		System.out.println(Integer.toBinaryString(a));
	}

	/**
	 * 写一个Java程序，将100.2转成IEEE745 二进制表示 ，给出程序和结果。
	 */
	@Test
	public void testIEEE754() {
		final Float f = 100.2f;

		String s = f > 0 ? "0" : "1"; // 确定符号位
		String e = "";
		String m = "";

		String[] array = f.toString().split("\\.");
		String beforeDotStr = array[0];
		String afterDotStr = new DigitalToBinary(Float.valueOf("0." + array[1])).toBinaryStr();

		String bstr = toBinaryStr(Integer.parseInt(beforeDotStr)) + "." + afterDotStr;
		System.out.println(bstr);

		// 转为整数将前面重复的0清除掉
		int beforeDot = Integer.parseInt(toBinaryStr(Integer.parseInt(beforeDotStr)));
		String bstr2 = beforeDot + "." + afterDotStr;

		System.out.println(bstr2);

		// 如果目标数大于1，则将小数点往左移,直到小数点的左边只有一个“1”为止
		// 如果目标数小于1，则将小数点往由移,直到小数点的左边只有一个“1”为止
		int mvCount = 0;
		if (f >= 1) {
			mvCount = String.valueOf(beforeDot).length() - 1;
			e = Integer.parseInt(toBinaryStr(127 + mvCount)) + "";

			// 移动小数点，重构二进制字符串
			String newStr = String.valueOf(beforeDot).substring(0, 1) + "." + String.valueOf(beforeDot).substring(1)
					+ afterDotStr;
			System.out.println(newStr);

			final int mLen = 23;
			String afterDotStrNew = newStr.split("\\.")[1];
			for (int i = afterDotStrNew.length(); i < mLen; i++) {
				afterDotStrNew += "0";
			}
			m = afterDotStrNew;

		} else {
			// 目标数小于1的情况
		}

		System.out.println(String.format(f + "转成IEEE745 二进制表示：%s %s %s", s, e, m));
		// 100.2转成IEEE745 二进制表示：0 10000101 10010000110011000000000
	}

	/**
	 * 整数部分转二进制
	 */
	String toBinaryStr(int num) {
		StringBuilder buf = new StringBuilder();
		for (int i = 0; i < 32; i++) {
			int t = (num & 0x80000000 >>> i) >>> (31 - i);
			buf.append(t);
		}
		return buf.toString();
	}

	/**
	 * 小数部分转二进制
	 */
	class DigitalToBinary {

		private float f;

		public DigitalToBinary(float f) {
			this.f = f;
		}

		public String toBinaryStr() {
			StringBuffer buf = new StringBuffer();
			for (int i : toBin())
				buf.append(i);
			return buf.toString();
		}

		private Integer[] toBin() {
			if (f >= 1 || f <= 0)
				return null;
			List<Integer> list = new ArrayList<Integer>();
			Set<Float> set = new HashSet<Float>();
			int MAX = 8; // 小数部分转二进制按最多8位处理

			int bits = 0;
			while (true) {
				f = calc(f, set, list);
				bits++;
				if (f == -1 || bits >= MAX)
					break;
			}

			return (Integer[]) list.toArray(new Integer[0]);
		}

		private float calc(float f, Set<Float> set, List<Integer> list) {
			if (f == 0 || set.contains(f))
				return -1;
			float t = f * 2;
			if (t >= 1) {
				list.add(1);
				return t - 1; // 返回乘2减1后的小数部分
			} else {
				list.add(0);
				return t;
			}
		}
	}

}
