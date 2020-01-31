package com.organization.test;


import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.component.properties.PropertiesComponent;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.impl.SimpleRegistry;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.custommonkey.xmlunit.XMLAssert;
import org.custommonkey.xmlunit.XMLUnit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class TestTest extends CamelTestSupport {

    private static String outputQueue;
    private static final String FILE_PATH = "src/test/resources/example/";

    @BeforeClass
    public static void SetUpClass() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setNormalizeWhitespace(true);
    }


    @AfterClass
    public static void tearDownClass() throws Exception {
    }



    @Override
    public CamelContext createCamelContext() throws Exception {
        SimpleRegistry simpleRegistry = new SimpleRegistry();

        PropertiesComponent properties = new PropertiesComponent();
        properties.setLocation("file:src/test/resources/app.properties");
        properties.setIgnoreMissingLocation(false);
        simpleRegistry.put("properties", properties);

/*        Base64Util base64Util = new Base64Util();
        simpleRegistry.put("base64Util", base64Util);*/

        Operation operation = new Operation();
        //Operation operation = new Operation(properties);
        simpleRegistry.put("operation", operation);

       // String mqName = properties.parseUri("mqname");
       // String queueOutTermReq = properties.parseUri("queue_out_termreq");
       // outputQueue = mqName + ":queue:" + queueOutTermReq;

        return new DefaultCamelContext(simpleRegistry);
    }


/*
    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new TerminationInquiryRequestRoute();
    }
*/


    @Override
    public boolean isCreateCamelContextPerClass() {
        return true;
    }


/*
    @Override
    public String isMockEndpointsAndSkip() { return outputQueue; }
*/


/*

    @Test
    public void testTermReqRoute() throws Exception {
        TerminateResponseType mockTerminateResponse = getMockTerminateResponse();

        // instead of real api invocation we route request to mock endpoint and get mock response
        context.getRouteDefinition("SRV.MASTERCARD.TERMREQ").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveById("requestTerminatedMerchants").replace().to("mock:beforeOperation").setBody(constant(mockTerminateResponse));
            }
        });

        String sentXmlRequest = readTextFromFile(FILE_PATH + "TermInqReq_Request.xml","UTF-8");
        template.sendBody("{{mqname}}:queue:{{queue_in_termreq}}", sentXmlRequest);

        // for testing the first half of the route
        MockEndpoint beforeOperationEndpoint = getMockEndpoint("mock:beforeOperation");
        // for testing the second half of the route
        MockEndpoint outputQueueEndpoint = getMockEndpoint("mock:" + outputQueue);

        beforeOperationEndpoint.expectedMessageCount(1);
        outputQueueEndpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();

        Exchange beforeOperationExchange = beforeOperationEndpoint.getExchanges().get(beforeOperationEndpoint.getExchanges().size() - 1);
        assertNotNull(beforeOperationExchange);
        TerminateRequestType receivedBeforeOperationRequest = beforeOperationExchange.getIn().getBody(TerminateRequestType.class);
        TerminateRequestType expectedBeforeOperationRequest = getExpectedTerminateRequest();
        assertEquals(expectedBeforeOperationRequest, receivedBeforeOperationRequest);

        Exchange outputQueueExchange = outputQueueEndpoint.getExchanges().get(outputQueueEndpoint.getExchanges().size() - 1);
        assertNotNull(outputQueueExchange);
        String receivedOutputQueueXmlResponse = outputQueueExchange.getIn().getBody(String.class);
        String expectedOutputQueueXmlResponse = readTextFromFile(FILE_PATH + "TermInqReq_Response.xml","UTF-8");
        XMLAssert.assertXMLEqual(expectedOutputQueueXmlResponse, receivedOutputQueueXmlResponse);
    }
*/


/*
    @Test
    public void testChangeHeadersRoute() throws Exception {
        context.getRouteDefinition("SRV.MASTERCARD.CHANGE.HEADERS").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveAddLast().to("mock:finish");
            }
        });

        String messageId = "1234567890";
        String sender = "sender";
        String receiver = "receiver";

        Map<String, Object> headers = new HashMap<>();
        headers.put("HMessageID", messageId);
        headers.put("HSender", sender);
        headers.put("HReceiver", receiver);

        template.sendBodyAndHeaders("direct:changeHeaders", null, headers);

        MockEndpoint finishEndpoint = getMockEndpoint("mock:finish");
        finishEndpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();

        Exchange exchange = finishEndpoint.getExchanges().get(finishEndpoint.getExchanges().size() - 1);
        assertNotNull(exchange);

        String hCorrelationMsgID = exchange.getMessage().getHeader("HCorrelationMsgID", String.class);
        String hMessageID = exchange.getMessage().getHeader("HMessageID", String.class);
        assertEquals(messageId, hCorrelationMsgID);
        assertNotEquals(messageId, hMessageID);

        String hSender = exchange.getMessage().getHeader("HSender", String.class);
        String hReceiver = exchange.getMessage().getHeader("HReceiver", String.class);
        assertEquals(receiver, hSender);
        assertEquals(sender, hReceiver);
    }
*/


/*


    @Test
    public void testBPRoutingRoute() throws Exception {
        context.getRouteDefinition("SRV.MASTERCARD.BPROUTING").adviceWith(context, new AdviceWithRouteBuilder() {
            @Override
            public void configure() {
                weaveAddLast().to("mock:finish");
            }
        });

        int routeHop = 1;

        template.sendBodyAndHeader("direct:BPRouting", null, "HROUTE_HOP", routeHop);

        MockEndpoint finishEndpoint = getMockEndpoint("mock:finish");
        finishEndpoint.expectedMessageCount(1);
        assertMockEndpointsSatisfied();

        Exchange exchange = finishEndpoint.getExchanges().get(finishEndpoint.getExchanges().size() - 1);
        assertNotNull(exchange);

        String hRouteTo = exchange.getMessage().getHeader("HRoute_To", String.class);
        assertEquals(context.resolvePropertyPlaceholders("{{queue_out_termreq}}"), hRouteTo);

        String hRouteHop = exchange.getMessage().getHeader("HROUTE_HOP", String.class);
        assertEquals(String.valueOf(++routeHop), hRouteHop);
    }

*/

    @Test
    public void testTest() throws Exception {
        assertTrue(true);
    }


/*
    private String readTextFromFile(String path, String charsetName) throws Exception {
        File file = new File(path);
        byte[] fileBytes = Files.readAllBytes(file.toPath());
        return new String(fileBytes, charsetName);
    }
*/




}
