package com.cwbase.axonerror.eventhandlers;

import org.axonframework.eventhandling.EventHandler;

import com.cwbase.axonerror.events.ErrorThrowingEvent;

public class ErrorThrowingEventHandler {

	@EventHandler
	public void on(ErrorThrowingEvent event) {
		// throw non-exception throwable cause leakage
		throw new Error();
	}
}
