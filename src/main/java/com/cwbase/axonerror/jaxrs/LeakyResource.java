package com.cwbase.axonerror.jaxrs;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.commons.lang3.RandomUtils;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cwbase.axonerror.commands.LeakyCommand;
import com.cwbase.axonerror.commands.NormalCommand;

@Path("/")
public class LeakyResource {

	static Logger logger = LoggerFactory.getLogger(LeakyResource.class);

	final CommandGateway cmdGw;

	public LeakyResource(CommandGateway cmdGw) {
		this.cmdGw = cmdGw;
	}

	@GET
	public Response root() {
		if (RandomUtils.nextBoolean()) {
			return normal();
		} else {
			return leaky();
		}
	}

	@GET
	@Path("/normal")
	public Response normal() {
		try {
			String response = cmdGw.sendAndWait(new NormalCommand());
			return Response.ok("No error: " + response).build();
		} catch (Throwable e) {
			logger.error("Error", e);
			return Response.ok("Error in Normal Call: " + e.getMessage()).build();
		}
	}

	@GET
	@Path("/leaky")
	public Response leaky() {
		try {
			String response = cmdGw.sendAndWait(new LeakyCommand());
			return Response.ok("No error: " + response).build();
		} catch (Throwable e) {
			logger.error("Error", e);
			return Response.ok("Error in Leaky Call: " + e.getMessage()).build();
		}
	}
}
