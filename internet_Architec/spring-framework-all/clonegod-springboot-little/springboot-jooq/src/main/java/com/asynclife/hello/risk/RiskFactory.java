package com.asynclife.hello.risk;

import java.util.ArrayList;
import java.util.List;

public class RiskFactory {

	/**
	 * 构建'无序'的Risk
	 * 		后台接收之后需要根据Risk的level进行排序
	 * 		每个Risk内部的Filter根据Formula的sequece属性进行排序
	 * @return
	 */
	public static List<Risk> createRisksWithOrderless() {
		List<Risk> risks = new ArrayList<Risk>();

		List<Formular> f1 = new ArrayList<Formular>();
		List<Formular> f2 = new ArrayList<Formular>();
		List<Formular> f3 = new ArrayList<Formular>();

		f1.add(new Formular("002", "name", "name.length() >= 2"));
		f1.add(new Formular("001", "age", "age >= 18"));

		f2.add(new Formular("004", "name", "name.length() >= 2"));
		f2.add(new Formular("003", "age", "age >= 36"));

		f3.add(new Formular("009", "name", "name.length() >= 2"));
		f3.add(new Formular("005", "cars", "cars <= 1"));
		f3.add(new Formular("008", "age", "age >= 36"));
		f3.add(new Formular("006", "debt", "debt >= 10000"));
		f3.add(new Formular("007", "set", "sex == 0"));

		Risk r1 = new Risk("A01", "损失", f1);
		Risk r2 = new Risk("A02", "可疑", f2);
		Risk r3 = new Risk("A03", "关注", f3);

		risks.add(r3);
		risks.add(r2);
		risks.add(r1);

		return risks;
	}
}
