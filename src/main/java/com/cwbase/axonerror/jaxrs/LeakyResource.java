package com.cwbase.axonerror.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwbase.axonerror.commands.LeakyCommand;

@Path("/")
public class LeakyResource {

	static Logger logger = LoggerFactory.getLogger(LeakyResource.class);

	final CommandGateway cmdGw;

	public LeakyResource(CommandGateway cmdGw) {
		this.cmdGw = cmdGw;
	}

	@GET
	public Response root() {
		try {
			cmdGw.sendAndWait(new LeakyCommand());
		} catch (Throwable e) {
			logger.error("Error", e);
			return Response.ok("Error: " + e.getMessage()).build();
		}
		return Response.ok("No error!").build();
	}
}
