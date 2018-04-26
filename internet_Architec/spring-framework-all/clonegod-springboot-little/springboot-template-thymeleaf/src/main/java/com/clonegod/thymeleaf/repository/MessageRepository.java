package com.clonegod.thymeleaf.repository;

import com.clonegod.thymeleaf.model.Message;

public interface MessageRepository {

	Iterable<Message> findAll();

	Message save(Message message);

	Message findMessage(Long id);

	void deleteMessage(Long id);

}
