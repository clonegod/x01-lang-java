package spittr.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * 如果资源没有找到的话， HTTP状态码404是最为精确的响应状态码。
 *
 */
@ResponseStatus(value=HttpStatus.NOT_FOUND, // 将异常映射为HTTP404状态码
				reason="Spittle Not Found")
public class SpittleNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8085216628718506910L;

	long spittleId;

	public SpittleNotFoundException() {
		super();
	}
	
	public SpittleNotFoundException(long spittleId) {
		super();
		this.spittleId = spittleId;
	}

	public long getSpittleId() {
		return spittleId;
	}

}
