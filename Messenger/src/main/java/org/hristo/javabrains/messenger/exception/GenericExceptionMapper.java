package org.hristo.javabrains.messenger.exception;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hristo.javabrains.messenger.model.ErrorMessage;

import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.core.Response;

//import com.sun.research.ws.wadl.Response;

public class GenericExceptionMapper implements ExceptionMapper<Throwable>{
	
	@Override
	public Response toResponse(Throwable ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 500, "http://getrektm16.com");
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
				.entity(errorMessage)
				.build();
	}
	
}