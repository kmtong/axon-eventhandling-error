package com.cwbase.axonerror;

import org.axonframework.config.Configurer;
import org.axonframework.config.DefaultConfigurer;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.eventhandling.SimpleEventHandlerInvoker;

import com.cwbase.axonerror.commandhandlers.LeakyCommandHandler;
import com.cwbase.axonerror.eventhandlers.ErrorThrowingEventHandler;
import com.cwbase.axonerror.jaxrs.LeakyResource;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;

public class DemoApplication extends Application<Configuration> {

	@Override
	public void run(Configuration configuration, Environment environment) throws Exception {

		// setup axon
		Configurer configurer = DefaultConfigurer.defaultConfiguration();

		// command handler
		configurer.registerCommandHandler((ax) -> new LeakyCommandHandler(ax.eventBus()));

		// event processing
		EventProcessingConfiguration eventProcConfig = new EventProcessingConfiguration();
		eventProcConfig.registerHandlerInvoker("group1",
				(ax) -> new SimpleEventHandlerInvoker(new ErrorThrowingEventHandler()));
		configurer.registerModule(eventProcConfig);

		// start
		org.axonframework.config.Configuration cfg = configurer.buildConfiguration();
		cfg.start();

		// inject to resource
		environment.jersey().register(new LeakyResource(cfg.commandGateway()));
	}

	public static void main(String[] args) throws Exception {
		new DemoApplication().run(args);
	}
}
