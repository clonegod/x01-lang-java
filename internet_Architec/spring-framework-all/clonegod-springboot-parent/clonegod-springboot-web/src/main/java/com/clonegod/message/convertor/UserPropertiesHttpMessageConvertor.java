package com.clonegod.message.convertor;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.clonegod.api.User;

public class UserPropertiesHttpMessageConvertor extends AbstractHttpMessageConverter<User> {
	
	public UserPropertiesHttpMessageConvertor() {
		super(MediaType.valueOf("application/properties+user"));
		setDefaultCharset(StandardCharsets.UTF_8);
	}

	/**
	 * 返回该converter是否支持处理当前的POJO类型
	 */
	@Override
	protected boolean supports(Class<?> clazz) {
		return clazz.isAssignableFrom(User.class);
	}

	/**
	 * 读取HTTP请求中的内容， 并且成想要的POJO对象
	 * 
	 * 将 传入的properties格式文本转换为User对象
	 * 
	 * @param clazz
	 * @param inputMessage
	 * @return
	 * @throws IOException
	 * @throws HttpMessageNotReadableException
	 */
	@Override
	protected User readInternal(Class<? extends User> clazz, HttpInputMessage inputMessage)
			throws IOException, HttpMessageNotReadableException {
		
		InputStream inStream = inputMessage.getBody();
		
		Properties userProp = new Properties();
		userProp.load(new InputStreamReader(inStream, getDefaultCharset()));
		
		User user = User.builder()
						.id(Long.parseLong(userProp.getProperty("user.id")))
						.name(userProp.getProperty("user.name"))
						.build();
		
		return user;
	}

	/**
	 * 将POJO的内容序列化成文本内容输出
	 * 
	 * 将User对象转换为Properties格式的文本，返回给客户端
	 * 
	 * @param t
	 * @param outputMessage
	 * @throws IOException
	 * @throws HttpMessageNotWritableException
	 */
	@Override
	protected void writeInternal(User user, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		
		OutputStream outStream = outputMessage.getBody();
		
		Properties props = new Properties();
		props.setProperty("user.id", user.getId()+"");
		props.setProperty("user.name", user.getName());
		
		props.store(new OutputStreamWriter(outStream, getDefaultCharset()), "Written by CloneGod application server");
	}

}
