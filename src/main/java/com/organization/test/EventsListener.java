package com.organization.test;

import org.apache.camel.main.MainListenerSupport;
import org.apache.camel.main.MainSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventsListener extends MainListenerSupport {

    private static Logger logger = LoggerFactory.getLogger(EventsListener.class);

    @Override
    public void afterStart(final MainSupport main) {
        logger.info("All Camel contexts started !!!");
        main.getCamelContexts().forEach(context -> {
            context.getRoutes().forEach(route -> {
                if (route.getId().toUpperCase().endsWith(".READINESS")) {
                    try {
                        logger.info("Starting route [{}] context [{}]", route.getId(), context.getName());
                        context.startRoute(route.getId());
                    } catch (Exception e) {
                        logger.error("Error starting route [{}] context [{}] error [{}]",
                                route.getId(), context.getName(), e.getMessage());
                    }
                }
            });
        });
    }

}

