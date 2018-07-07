package org.hristo.javabrains.messenger.resources;

import java.net.URI;
import java.util.List;

import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.hristo.javabrains.messenger.model.Message;
import org.hristo.javabrains.messenger.resources.beans.MessageFilterBean;
import org.hristo.javabrains.messenger.service.MessageService;

import com.sun.research.ws.wadl.Response;

@Path("messages")
@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_XML})
public class MessageResource {
	
	MessageService messageService = new MessageService();
	
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Message> getJSONMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("JSON method called");
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() >= 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@GET
	@Produces(MediaType.TEXT_XML)
	public List<Message> getXMLMessages(@BeanParam MessageFilterBean filterBean) {
		System.out.println("XML method called");
		if(filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if(filterBean.getStart() >= 0 && filterBean.getSize() >= 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		return messageService.getAllMessages();
	}
	
	@POST
	public Message addMessage(Message message) {
		return messageService.addMessage(message);
	}
	
	@PUT
	@Path("{messageId}")	
	public Message updateMessage(@PathParam("messageId") long messageId, Message message) {
		message.setId(messageId);
		return messageService.updateMessage(message);
	}
	
	@DELETE
	@Path("{messageId}")
	public Message removeMessage(@PathParam("messageId") long messageId) {
		return messageService.removeMessage(messageId);
    }
	
	
	@GET
	@Path("{messageId}")
	public Message getMessage(@PathParam("messageId") long messageId, @Context UriInfo uriInfo) {
		Message message = messageService.getMessage(messageId);
		message.addLink(getUriForSelf(uriInfo, message), "self");
		message.addLink(getUriForProfile(uriInfo, message), "author");
		message.addLink(getUriForComments(uriInfo, message), "comments");
		return message;
    }
	
	private String getUriForComments(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
					.path(MessageResource.class)
					.path(MessageResource.class, "getCommentResource")
					.path(CommentResource.class)
					.resolveTemplate("messageId", message.getId())
					.build()
					.toString();
		return uri;
	}
	
	private String getUriForProfile(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
					.path(ProfileResource.class)
					.path(message.getAuthor())
					.build()
					.toString();
		return uri;
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
					.path(MessageResource.class)
					.path(Long.toString(message.getId()))
					.build()
					.toString();
		return uri;
	}
	
	@Path("{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
    }
	
}
