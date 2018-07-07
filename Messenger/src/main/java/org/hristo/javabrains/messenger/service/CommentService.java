package org.hristo.javabrains.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hristo.javabrains.messenger.database.DatabaseClass;
import org.hristo.javabrains.messenger.model.Comment;
import org.hristo.javabrains.messenger.model.ErrorMessage;
import org.hristo.javabrains.messenger.model.Message;

public class CommentService {

	//we keep it as type Message, because the database class only has that
	//add a comment equivalent later
	private static Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public List<Comment> getAllComments(long messageId){
		Map <Long, Comment> comments = messages.get(messageId).getComments();
		return new ArrayList<Comment>(comments.values());
	}
	
	public Comment getComment(long messageId, long commentId) {
		ErrorMessage errorMessage = new ErrorMessage("Not Found", 404, "http://facebook.com");
		Response response = Response.status(Response.Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
		
		Message message = messages.get(messageId);
		if(message == null) {
			throw new WebApplicationException(response);
		}
		Map <Long, Comment> comments = messages.get(messageId).getComments();
		Comment comment = comments.get(commentId);
		if(comment == null) {
			throw new WebApplicationException(response);
		}
		return comment;
	}
	
	public Comment addComment(long messageId, Comment comment) {
		Map <Long, Comment> comments = messages.get(messageId).getComments();
		comment.setId(comments.size() + 1);
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment updateComment(long messageId, Comment comment) {
		Map <Long, Comment> comments = messages.get(messageId).getComments();
		if(comment.getId() <= 0) {
			return null;
		}
		comments.put(comment.getId(), comment);
		return comment;
	}
	
	public Comment removeComment(long messageId, long commentId) {
		Map <Long, Comment> comments = messages.get(messageId).getComments();
		return comments.remove(commentId);
	}
	
}

/*	public List<Message> getAllMessagesForYear(int year){ 
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
	}*/
