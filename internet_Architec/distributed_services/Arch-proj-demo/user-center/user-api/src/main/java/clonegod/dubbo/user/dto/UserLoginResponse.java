package clonegod.dubbo.user.dto;

import java.io.Serializable;

import clonegod.dubbo.user.abs.AbstractResponse;

public class UserLoginResponse extends AbstractResponse implements Serializable{
    private static final long serialVersionUID = -4595924096093523471L;
    
    private String realName;

    private String avatar;

    private String mobile;

    private String sex;
    
    public UserLoginResponse() {
		super();
	}

	public UserLoginResponse(String code, String memo) {
		super(code, memo);
	}

	public static UserLoginResponse fail(String code, String memo) {
    	return new UserLoginResponse(code, memo);
    }
	
	public static UserLoginResponse success() {
		return new UserLoginResponse("000000", "SUCCESS");
	}

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "UserLoginResponse{" +
                "realName='" + realName + '\'' +
                ", avatar='" + avatar + '\'' +
                ", mobile='" + mobile + '\'' +
                ", sex='" + sex + '\'' +
                "} " + super.toString();
    }

}
