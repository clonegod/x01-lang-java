package clonegod.myutils.format;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

public class StringFormat {
	
	/**
	 * %[index$][标识][最小宽度]转换符
	 * 可用标识：
   		-，在最小宽度内左对齐，右边用空格补上。
		可用转换符(用于限制对应入参的数据类型)：
   			s，字符串类型。
   			c，字符类型，实参必须为char或int、short等可转换为char类型的数据类型，否则抛IllegalFormatConversionException异常。
   			b，布尔类型，只要实参为非false的布尔类型，均格式化为字符串true，否则为字符串false。
   			n，平台独立的换行符（与通过 System.getProperty("line.separator") 是一样的） 
	 */
	@Test
	public void testFormatString() {
		String text1 = String.format("Hello %1$s %2$s %n Good Bye!", "John", "007"); // 基于索引的格式化
		sop(text1);
		
		String text2 = String.format("%1$s %2$d%%", "折扣为", 97); // %% 转移表示 %
		System.out.println(text2);
		
		String text3 = String.format("%1$-7s", "hello"); // - 左对齐补空格
		sop(text3);
		
		String text4 = String.format("%1$7s", "hello"); // 右对齐补空格
		sop(text4);
		
		String str = "129018";
		String str2 = String.format("%10s", str).replace(' ', '0');
		System.out.println(str2);
		
		String str3 = StringUtils.leftPad(str2, 10, '0');
		System.out.println(str3);
	}
	
	/**
	 *%[index$][标识]*[最小宽度]转换符 
	 *可用标识：
		-，在最小宽度内左对齐,不可以与0标识一起使用。
		0，若内容长度不足最小宽度，则在左边用0来填充。
		#，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
		+，结果总包含一个+或-号。
		空格，正数前加空格，负数前加-号。
		,，只用与十进制，每3位数字间用,分隔。
		(，若结果为负数，则用括号括住，且不显示符号。
	可用转换符：
		b，布尔类型，只要实参为非false的布尔类型，均格式化为字符串true，否则为字符串false。
		d，整数类型（十进制）。
		x，整数类型（十六进制）。
		o，整数类型（八进制）
		n，平台独立的换行符, 也可通过System.getProperty("line.separator")获取
	 */
	@Test
	public void testFormatNum() {
		int num = 20150101;
		String str = String.format("%1$012d", num); // 最小宽度12位，不足则前面补0，参数类型为数字
		sop(str);
		
		long l = new Random().nextLong();
		String lStr = String.format("%032x", l); //0000000000000000c5d37380873bf075
		System.out.println(lStr);
	}

	/**
	 *  %[index$][标识]*[最小宽度][.精度]转换符 
	 */
	@Test
	public void testFormatDouble() {
		double num = 123.4567899;
		sop(String.format("%.2f", num)); // 123.46 四舍五入
		
		double total = 21732178.12988;
		sop(String.format("%,.4f", total)); // 整数部分用逗号分组，小数部分保留4位，四舍五入
	}
	
	/**
	 * %tX
	 * X可以是：
	 * 
	 * F，2007-10-27
	 * T，14:28:16
	 * 
	 * y, 年后两位（不足两位补零）
	 * m, 月份（不足两位补零）
	 * d, 日期（不足两位补零）
	 * 
	 * H, 24小时制的小时（不足两位补零）
	 * M, 分钟（不足两位补零）
	 * S, 秒（不足两位补零）
	 * L, 毫秒（不足三位补零
	 * 
	 * s, 自1970-1-1 00:00:00起经过的秒数
	 * Q, 自1970-1-1 00:00:00起经过的豪秒
	 */
	@Test
	public void testFormatDate() {
		Date now = new Date();
		sop(String.format("%tF", now));
		sop(String.format("%1$tF %2$tT", now, now));
		sop(String.format("%1$tC%2$ty-%3$tm-%4$td %5$tT", now, now, now, now, now));
		sop(String.format("%1$tF %2$tT.%3tL", now, now, now));
		sop(String.format("%ts", now));
		sop(String.format("%tQ", now));
		sop(System.currentTimeMillis()+"");
		
	}
	
	/**
	 *  <，用于格式化前一个转换符所描述的参数-即：使用前一个转换符所使用的参数，从而不需要通过index$指定参数的索引
	 */
	@Test
	public void testFormatPre() {
		double salary = 20000.88217;
		String str = String.format("%.4f %n %<,.2f%%", salary);
		sop(str);
	}
	
	private void sop(String text) {
		System.err.println(text);
	}
	
}
