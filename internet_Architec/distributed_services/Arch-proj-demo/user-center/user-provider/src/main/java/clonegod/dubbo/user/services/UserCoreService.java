package clonegod.dubbo.user.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;

import clonegod.dubbo.user.api.IUserCoreService;
import clonegod.dubbo.user.constants.Constants;
import clonegod.dubbo.user.constants.ResponseCodeEnum;
import clonegod.dubbo.user.dal.entity.User;
import clonegod.dubbo.user.dal.persistence.UserMapper;
import clonegod.dubbo.user.dto.DebitRequest;
import clonegod.dubbo.user.dto.DebitResponse;
import clonegod.dubbo.user.dto.UserLoginRequest;
import clonegod.dubbo.user.dto.UserLoginResponse;
import clonegod.dubbo.user.dto.UserRegisterRequest;
import clonegod.dubbo.user.dto.UserRegisterResponse;
import clonegod.dubbo.user.exception.ExceptionUtil;
import clonegod.dubbo.user.exception.ServiceException;
import clonegod.dubbo.user.validator.UserValidator;

@Service("userLoginService")
public class UserCoreService implements IUserCoreService {

    Logger logger= LoggerFactory.getLogger(UserCoreService.class);

    @Autowired
    UserMapper userMapper;

    public UserLoginResponse login(UserLoginRequest userLoginRequest) {
        logger.info("begin UserCoreService.login,request:【"+userLoginRequest+"】");
        UserLoginResponse response=new UserLoginResponse();
        try{
        	UserValidator.beforeValidate(userLoginRequest);

            User user=userMapper.getUserByUserName(userLoginRequest.getUsername());
            if(user==null||!user.getPassword().equals(userLoginRequest.getPassword())){
                response.setCode(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getCode());
                response.setMsg(ResponseCodeEnum.USER_OR_PASSWORD_ERROR.getMsg());
                return response;
            }
            //todo 判断用户状态
            response.setAvatar(user.getAvatar());
            response.setMobile(user.getMobile());
            response.setRealName(user.getRealname());
            response.setSex(user.getSex());

            response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
            response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
            return response;
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("login response:【"+response+"】");
        }
        return response;
    }

    public UserRegisterResponse register(UserRegisterRequest userRegisterRequest) {
        logger.info("begin UserCoreService.register,request:【"+userRegisterRequest+"】");

        UserRegisterResponse response=new UserRegisterResponse();
        try{
        	UserValidator.beforeRegisterValidate(userRegisterRequest);

            User user=new User();
            user.setUsername(userRegisterRequest.getUsername());
            user.setPassword(userRegisterRequest.getPassword());
            user.setStatus(Constants.FORZEN_USER_STATUS);
            user.setCreateTime(new Date());

            int effectRow=userMapper.insertSelective(user);
            if(effectRow>0){
                response.setCode(ResponseCodeEnum.SYS_SUCCESS.getCode());
                response.setMsg(ResponseCodeEnum.SYS_SUCCESS.getMsg());
                return  response;
            }
            response.setCode(ResponseCodeEnum.DATA_SAVE_ERROR.getCode());
            response.setMsg(ResponseCodeEnum.DATA_SAVE_ERROR.getMsg());
            return  response;
        }catch (DuplicateKeyException e){
            //TODO 用户名重复
        }catch(Exception e){
            ServiceException serviceException=(ServiceException) ExceptionUtil.handlerException4biz(e);
            response.setCode(serviceException.getErrorCode());
            response.setMsg(serviceException.getErrorMessage());
        }finally {
            logger.info("register response:【"+response+"】");
        }

        return response;
    }

	@Override
	public DebitResponse debit(DebitRequest request) {
		DebitResponse response=new DebitResponse();
//        userDao.updateUser(request);
        response.setCode("000000");
        response.setMsg("成功");
        return response;
	}


}
