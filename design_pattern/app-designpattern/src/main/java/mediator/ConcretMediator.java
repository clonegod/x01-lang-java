package mediator;

import java.util.HashSet;
import java.util.Set;

public class ConcretMediator extends Mediator {
	
	private Set<Colleague> colleagues = new HashSet<Colleague>();
	
	// 注册需要被调停的同事对象
	public void addConcretColleague(Colleague c) {
		colleagues.add(c);
	}
	
	 // 事件方法 - 将更新作用到所有同事对象
	@Override
	public void colleagueChanged(Colleague c) {
		System.out.println("Colleague changed :"+c.getClass().getName());
		
		for(Colleague colleague : colleagues) {
			if(colleague == c) {
				System.out.println("跳过事件源对象，不发通知");
				continue;
			}
			colleague.action();
		}
	}
	
	
	
}
