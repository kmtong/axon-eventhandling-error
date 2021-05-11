package com.cwbase.axonerror.commandhandlers;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventhandling.EventBus;
import org.axonframework.eventhandling.GenericEventMessage;

import com.cwbase.axonerror.commands.LeakyCommand;
import com.cwbase.axonerror.events.ErrorThrowingEvent;

public class LeakyCommandHandler {

	final EventBus eventBus;

	public LeakyCommandHandler(EventBus eventBus) {
		this.eventBus = eventBus;
	}

	@CommandHandler
	public String leakHere(LeakyCommand cmd) {
		eventBus.publish(GenericEventMessage.asEventMessage(new ErrorThrowingEvent()));
		return "Leaky";
	}

}
