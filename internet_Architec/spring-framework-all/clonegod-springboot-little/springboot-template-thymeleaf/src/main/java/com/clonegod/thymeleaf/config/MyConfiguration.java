package com.clonegod.thymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;

import com.clonegod.thymeleaf.model.Message;
import com.clonegod.thymeleaf.repository.InMemoryMessageRepository;
import com.clonegod.thymeleaf.repository.MessageRepository;

@Configuration
public class MyConfiguration {

	@Bean
	public MessageRepository messageRepository() {
		return new InMemoryMessageRepository();
	}

	@Bean
	public Converter<String, Message> messageConverter() {
		return new Converter<String, Message>() {
			@Override
			public Message convert(String id) {
				return messageRepository().findMessage(Long.valueOf(id));
			}
		};
	}
	
}
