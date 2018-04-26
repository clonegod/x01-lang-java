package clonegod.myutils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class WindowsCommandRunner {
	
	private static String NEW_LINE = System.getProperty("line.separator");
	
	public static Object[] exec(String command) {
		// 根据操作系统判断使用哪种编码
		String encoding = System.getenv("os").matches("(?i)Windows.*")? "GBK" : "UTF-8";
		try {
			Process p = Runtime.getRuntime().exec(command);
			BufferedReader bufr = new BufferedReader(
									new InputStreamReader(
											p.getInputStream(), encoding));
			StringBuilder output = new StringBuilder();
			String executeResult = null;
			while((executeResult = bufr.readLine()) != null) {
				output.append(executeResult).append(NEW_LINE);
			}
			return new Object[] {p, output.toString()};
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public static void main(String[] args) {
		Object[] result = exec("cmd.exe /c ipconfig/all");
		Process p = (Process) result[0];
		String output = (String) result[1];
		System.out.println("Process exit value="+p.exitValue());
		System.out.println("-----------------------");
		System.out.println(output);
	}
}
