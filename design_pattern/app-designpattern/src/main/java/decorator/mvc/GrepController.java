package decorator.mvc;

import java.io.IOException;
import java.io.StringReader;

/**
 * 控制器-
 * 	1. 负责调用模型处理任务
 *  2. 将结果传递给视图进行显示
 *
 */
public class GrepController {
	static GrepReaderMode gr;
	private static GrepView gv = new GrepView();
	
	public void handleRequest(String text, String target) throws Exception {
		gr = new GrepReaderMode(new StringReader(text), target);
		
		String line;
		for(;;) {
			line = gr.readLine();
			if(line == null) break;
			gv.println(gr.getLineNumber()+": "+line);
		}
		
		gr.close();
	}
	
}
