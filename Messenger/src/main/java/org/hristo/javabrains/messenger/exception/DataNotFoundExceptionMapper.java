package org.hristo.javabrains.messenger.exception;

import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.hristo.javabrains.messenger.model.ErrorMessage;

import javax.net.ssl.SSLEngineResult.Status;
import javax.ws.rs.core.Response;

//import com.sun.research.ws.wadl.Response;
@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException>{
	
	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http://facebook.com");
		return Response.status(Response.Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}
	
}
