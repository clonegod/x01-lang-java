package template.coffee;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TeaWithHook extends AbstractCaffeinBeverage {
	
	/**
	 * 通过钩子函数控制父类的算法步骤
	 */
	@Override
	protected boolean customerWantsCondiments() {
		String answer = getUserInput();
		return answer.toUpperCase().startsWith("Y");
	}

	private String getUserInput() {
		String answer = null;
		
		System.out.println("Would you like some lemo (y/n)?");
		
		BufferedReader bufr = new BufferedReader(new InputStreamReader(System.in));
		try {
			answer = bufr.readLine();
		} catch (IOException e) {
			System.out.println("IO error trying to read your answer");
		} finally {
			try {
				bufr.close();
			} catch (IOException e) {
			}
		}
		
		if(answer == null) {
			return "NO";
		}
		
		return answer;
	}

	@Override
	protected void brew() {
		System.out.println("Steeping the tea");
	}

	@Override
	protected void addCondiments() {
		System.out.println("Adding Lemon");
	}

}
