package org.hristo.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.hristo.javabrains.messenger.database.DatabaseClass;
import org.hristo.javabrains.messenger.exception.DataNotFoundException;
import org.hristo.javabrains.messenger.model.Message;

public class MessageService {

	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1,"Hello World!","Hristo"));
		messages.put(2L, new Message(2,"Hello Jersey!","Hristo"));
	}
	
	public List<Message> getAllMessages(){
		return new ArrayList<Message>(messages.values());
	}
	
	public List<Message> getAllMessagesForYear(int year){ 
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for(Message message : messages.values()) {
			cal.setTime(message.getDate());
			if(cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size){ 
		ArrayList<Message> messagesPaginated = new ArrayList<Message>(messages.values());
		if((start + size) > messages.size()) {return new ArrayList<Message>();}
		//System.out.printf("%s",messagesPaginated.subList(start, start + size)+"I AM HERE");
		return messagesPaginated.subList(start, start + size);
	}
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if(message == null) {
			throw new DataNotFoundException("Message with id "+id+" not found.");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		if(message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	
}


/*public class MessageService implements IMessageService {
 private Map<Long,Message> mapMessages = new HashMap<Long,Message>();
 private AtomicLong lng = new AtomicLong(); 
 
 @Override
 public List<Message> getAllMessages() {
  return  new ArrayList<Message>(mapMessages.values());
 }

 @Override
 public Message getMessage(long messageId) {
  return mapMessages.get(messageId);
 }

 @Override
 public Message addMessage(Message msg) {
  Message msgNew = null;
  synchronized(this) {
   long id = lng.incrementAndGet();
   msgNew = new Message(id,msg.getMessageText(),msg.getAuthor());
   mapMessages.put(id, msgNew);
  }
  return msgNew;
 }
 @Override
 public Message updateMessage(Message msg) {
  // TODO Auto-generated method stub
  Message msgCurrent = getMessage(msg.getId());
  Message msgUpdated = null;
  synchronized(this) {
   msgUpdated = new Message(msgCurrent.getId(),msgCurrent.getMessageText(),msgCurrent.getAuthor());
   mapMessages.put(msg.getId(), msgUpdated);
  }
  return msgUpdated;
 }

 @Override
 public Message removeMessage(long messageId) {
  Message msgCurrent = getMessage(messageId);
  synchronized(this) {
   return mapMessages.remove(msgCurrent.getId());
  }
 }
 
}﻿*/
