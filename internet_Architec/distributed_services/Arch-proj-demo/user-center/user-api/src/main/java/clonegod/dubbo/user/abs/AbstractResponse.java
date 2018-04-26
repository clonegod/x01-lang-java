package clonegod.dubbo.user.abs;

import java.io.Serializable;

public abstract class AbstractResponse implements Serializable{


    private static final long serialVersionUID = 8122890624127584187L;
    /**
     * 返回码（请求）
     */
    private String code;

    private String msg;
    
    
    public AbstractResponse() {
		super();
	}

	public AbstractResponse(String code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "AbstractResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                '}';
    }
}
