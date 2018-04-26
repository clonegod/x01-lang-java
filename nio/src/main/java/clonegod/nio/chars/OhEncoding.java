package clonegod.nio.chars;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import clonegod.myutils.BinaryUtil;
import clonegod.myutils.FileUtil;
/**
 * 计算机只能识别二进制，如 10010001 8bit=1byte 多个字节需要用字节数组来存储-byte[]
 * 为了将字符转换为计算机可识别的二进制，因此需要为每个字符分配对应的二进制，由此产生了编码表
 * 编码表：字符与二进制的映射表
 * 	ASCII 美国标准信息交换代码，只用了1个字节中的7位；
 * 	ISO8859-1 欧洲，1个字节的8个比特位都用了；
 * 	GB2312 GBK GB18030 中文编码表，1个中文字符用2个字节存储 ；
 * 	UNICODE(UTF-8,UTF-16...) 容纳世界上所有文字和符号的字符编码
 * 		UTF-8 英文字符用1个字节，中文字符用3个字节表示
 * 
 * 判断文件编码类型：
 * 	中文 GBK编码 1个汉字2个字节
 *  中文 UTF8编码 1个汉字3个字节
 *  右键看文件大小便可判断文件的使用的编码类型
 */
public class OhEncoding {

	/**
	 * 得到字符对于的二进制序列/16进制序列
	 * @throws IOException 
	 */
	@Test
	public void char2BitsTest() {
		System.out.println(BinaryUtil.getBits((byte)0));
		System.out.println(BinaryUtil.getBits((byte)10));
		System.out.println(BinaryUtil.getBits((byte)255));
	}
	
	@Test
	public void urlEncodeTest() throws Exception {
		String str = "我";
		
		String gbkStr = URLEncoder.encode(str, "GBK");
		System.out.println(gbkStr); // %CE%D2
		
		String utf8Str = URLEncoder.encode(str, "UTF-8");
		System.out.println(utf8Str); // %E6%88%91
	}

	/**
	 * 测试相同字符使用不同编码表的时候，得到的字节数组/字节序列
	 */
	@Test
	public void basicTest() throws UnsupportedEncodingException {
		// 字符 -> 字节数组 【编码】
		
		// 使用平台默认编码表
		byte[] defaultBytes = "我".getBytes();
		// 1个中文字符用GBK编码表转换为二进制，需用2个字节
		printBytes(defaultBytes); //-26 -120 -111
		
		// 相同字符在不同编码表中，对应的二进制是不同的
		byte[] gbkBytes = "我".getBytes("GBK");
		// 1个中文字符用GBK编码表转换为二进制，需用2个字节
		printBytes(gbkBytes); //-50 -46
		
		
		byte[] utf8Bytes = "我".getBytes("UTF-8");
		// 1个中文字符用UTF-8编码表转换为二进制，需用3个字节
		printBytes(utf8Bytes); //-26 -120 -111 
		
		// 字节数组 - > 字符  【解码】
		String str1 = new String(gbkBytes, "GBK");
		System.out.println(str1);
		
		String str2 = new String(utf8Bytes, "UTF-8");
		System.out.println(str2);
	}
	
	/**
	 * 编码、解码不一致导致的错误问题
	 * 	可恢复的情况：
	 * 	GBK编码->ISO8859-1解码->ISO8859-1编码->GBK解码
	 * 	可恢复的原因：ISO8859-1使用单字节编码，是可逆的，所以可恢复解码之间的字节数组
	 * 
	 *  不可恢复：
	 * 	GBK编码->UTF-8解码->错误的字符，将造成原始数据丢失，无法再恢复
	 */
	@Test
	public void errorDecondingHandleTest01() throws Exception {
		String srcText = "你好";
		
		byte[] srcBytes = srcText.getBytes("GBK");
		
		// Tomcat服务器按ISO8859-1进行解码处理
		
		String decodeByServer = new String(srcBytes, "ISO8859-1");

		System.out.println(decodeByServer);
		
		// 恢复原始字节数组：使用ISO8859-1得到Tomcat解码前的字节数组
		byte[] recoverBytes = decodeByServer.getBytes("ISO8859-1"); 
		
		String recoverStr = new String(recoverBytes, "GBK");
		System.out.println(recoverStr);
		
	}
	
	/**
	 * 解码错误后，无法恢复的情况
	 * 	UTF-8 编码对不识别的编码统一用[-17 -65 -67]来表示
	 *  UTF-8的特点：
	 *  	1个字符可能用1个字节进行编码，也可能是2个字节，也可能是3个字节
	 */
	@Test
	public void errorDecondingHandleTest02() throws Exception {
		String srcText = "你好";
		//String srcText = "谢谢";
		
		byte[] srcBytes = srcText.getBytes("GBK");
		
		// Tomcat服务器按UTF-8进行解码处理，将造成数据丢失：UTF-8对所有不之别的字符都用[-17 -65 -67]来表示了
		String decodeByServer = new String(srcBytes, "UTF-8");
		
		System.out.println(decodeByServer);
		
		// 由于GBK编码->UTF-8解码造成了数据丢失，此时已无法还原UTF-8解码后的字节数组了
		byte[] recoverBytes = decodeByServer.getBytes("UTF-8"); 
		printBytes(recoverBytes);
		
		// 原始字节已丢失，解码得到错误的字符
		String recoverStr = new String(recoverBytes, "GBK");
		System.out.println(recoverStr);
		
	}
	
	/**
'联通'按GBK进行二进制编码：
11000001
10101010

11001101
10101000

这2个字符GBK编码后得到的字节数组:110xxxxx 10xxxxxx  
由于它们都符合UTF-8的编码规则，在解码的时候都被错误的认为是按UTF-8编码的，再按UTF-8进行解码就导致了错了。
	 * @throws Exception
	 */
	@Test
	public void test联通() throws Exception {
		String str = "联通";
		byte[] bytes = str.getBytes("GBK");
		for(byte b : bytes) {
			System.out.println(BinaryUtil.getBits2(b));
		}
	}
	
	@Test
	public void writeTest() throws Exception {
		String text = "abc你好def";
		
		// write utf-8
		byte[] bytes1 = text.getBytes("UTF-8");
		OutputStream os1 = new FileOutputStream("target/utf8.txt");
		IOUtils.write(bytes1, os1);
		
		// write utf-8
		byte[] bytes2 = text.getBytes("GBK");
		OutputStream os2 = new FileOutputStream("target/gbk.txt");
		IOUtils.write(bytes2, os2);
		
	}
	
	
	@Test
	public void readTest() throws Exception {
		// 输入流数据是GBK编码，将其按UTF8编码进行存储
		FileUtil.copyByCharset(
				new FileInputStream("target/gbk.txt"), "GBK", 
				new FileOutputStream("target/gbk2utf8.txt"), "UTF-8");
		
		// 输入流数据是UTF-8编码，将其按GBK编码进行存储
		FileUtil.copyByCharset(
				new FileInputStream("target/utf8.txt"), "UTF-8", 
				new FileOutputStream("target/utf82gbk.txt"), "GBK");
		
	}
	
	@Test
	public void testByteArrayStream() {
		// 操作内存中的数组
		byte[] source = "hello".getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(source);
		
		// 让内存中的数组按流的方式进行处理
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		int ch = 0;
		while((ch=bais.read()) != -1) {
			baos.write(ch);
		}
		System.out.println(baos.toString());
		// baos.writeTo(out);
	}
	
	private static void printBytes(byte[] bytes) {
		for(byte b : bytes) {
			System.out.print(b + "\t");
		}
		System.out.println();
		
		for(byte b : bytes) {
			System.out.print(BinaryUtil.getBits(b) + "\t");
		}
		System.out.println();
		
		System.out.println(BinaryUtil.bytesToHexString(bytes));
	}
	
}
