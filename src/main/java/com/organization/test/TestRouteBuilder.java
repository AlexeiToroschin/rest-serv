package com.organization.test;

import org.apache.camel.ExchangePattern;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class TestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {


        onException(Exception.class)
                .handled(true)
                //.to("log:ERROR in ${routeId}.\n Exception.message: ${exception.message}.\n Exception_stacktrace: ${exception.stacktrace}?showHeaders=true")
                .log(LoggingLevel.ERROR, "ERROR in ${routeId}. ${headers}\n Exception.message: ${exception.message}.\n Exception_stacktrace: ${exception.stacktrace}.")
                .stop();

        restConfiguration()
                // to use spark-rest component and run on port 9090
                .component("http-jetty").port("{{port}}")
                // enable api-docs
                .apiContextPath("api-doc")
                // enable CORS on rest services so they can be called from swagger UI
                .enableCORS(true)
                // enable CORS in the api-doc as well so the swagger UI can view it
                .apiProperty("cors", "true")
                .setScheme("http")
        ;

        // ping rest service
        rest("/test/healthcheck").get().route().log("${routeId} IN ${in.header.CamelServletContextPath} JMSTYPE:${in.header.JMSType}. ORG:${in.header.HOrganization}. ID: ${in.header.breadcrumbId}").bean(Operation.class,"getProjectName");


/*
        rest("/test/checkDenyList").post()
                .route()
                .routeId("SRV.DIRECTORY.IBSO.CHECKDENYLIST")
                .log(LoggingLevel.INFO,"${routeId} IN ${in.header.CamelServletContextPath} JMSTYPE:${in.header.JMSType}. ORG:${in.header.HOrganization}. ID: ${in.header.breadcrumbId}")
                .log("ID: ${in.header.breadcrumbId}, BODY: ${body}" )
                .log(LoggingLevel.DEBUG, "${routeId} ${in.header.breadcrumbId} body: ${body}")
                .bean("operation","getInnFromBody")
                .to("sql:select * from DEED where DENY_INN=:#inn?outputHeader=qInn")
                .log("ID: ${in.header.breadcrumbId}, FINISH BODY: ${body}" );

*/




    }

}
