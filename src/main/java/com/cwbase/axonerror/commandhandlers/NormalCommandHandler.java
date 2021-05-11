package com.cwbase.axonerror.commandhandlers;

import org.axonframework.commandhandling.CommandHandler;

import com.cwbase.axonerror.commands.NormalCommand;

public class NormalCommandHandler {

	@CommandHandler
	public String normal(NormalCommand cmd) {
		return "Normal";
	}

}
