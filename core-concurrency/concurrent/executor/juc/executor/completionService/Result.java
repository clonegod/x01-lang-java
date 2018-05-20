package juc.executor.completionService;
/**
 * 任务执行后需要返回的结果对象
 */
class Result {
	
	public String executeThreadName;
	public long randNumber;
	
	@Override
	public String toString() {
		return "Result [executeThreadName=" + executeThreadName + ", randNumber=" + randNumber + "]";
	}
	
}