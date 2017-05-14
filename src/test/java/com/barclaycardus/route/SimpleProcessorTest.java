package com.barclaycardus.route;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.spring.javaconfig.SingleRouteCamelConfiguration;
import org.apache.camel.test.spring.CamelSpringDelegatingTestContextLoader;
import org.apache.camel.test.spring.CamelSpringJUnit4ClassRunner;
import org.apache.camel.test.spring.MockEndpoints;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by Pavan on 15-05-2017.
 */
@RunWith(CamelSpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = {SimpleProcessorTest.TestConfig.class},
        loader = CamelSpringDelegatingTestContextLoader.class
)
@MockEndpoints
public class SimpleProcessorTest/* extends CamelTestSupport*/{
/*
*send and *request difference

    @Override
    public boolean isCreateCamelContextPerClass() {
        // we override this method and return true, to tell Camel test-kit that
        // it should only create CamelContext once (per class), so we will
        // re-use the CamelContext between each test method in this class
        return true;
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("ftp://host/data/inbox").
                        routeId("main").
                        to("file:data/outbox");
            }
        };
    }

    @Override
    public boolean isUseAdviceWith() {
        // Indicates we are using advice with, which allows us to advise the route
        // before Camel is started
        return true;
    }

    @Test
    public void TestMe() throws Exception {
        // alter the original route
        context.getRouteDefinition("main").adviceWith(context,
                new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() throws Exception {
                        replaceFromWith("direct:input");
                        interceptSendToEndpoint("file:data/outbox")
                                .skipSendToOriginalEndpoint()
                                .to("mock:done");
                    }
                });
        context.start();

        // write unit test following AAA (Arrange, Act, Assert)
        String bodyContents = "Hello world";
        MockEndpoint endpoint = getMockEndpoint("mock:done");
        endpoint.expectedMessageCount(1);
        endpoint.expectedBodiesReceived(bodyContents);

        template.sendBody("direct:input", bodyContents);

        assertMockEndpointsSatisfied();
    }
*/
    @EndpointInject(uri = "mock:direct:end")
    protected MockEndpoint endEndpoint;

    @EndpointInject(uri = "mock:direct:error")
    protected MockEndpoint errorEndpoint;

    @Produce(uri = "direct:test")
    protected ProducerTemplate testProducer;

    @Configuration
    public static class TestConfig extends SingleRouteCamelConfiguration {
        @Bean
        @Override
        public RouteBuilder route() {
            return new RouteBuilder() {
                @Override
                public void configure() throws Exception {
                    from("direct:test").errorHandler(deadLetterChannel("direct:error")).to("direct:end");

                    from("direct:error").log("Received message on direct:error endpoint.");

                    from("direct:end").log("Received message on direct:end endpoint.");
                }
            };
        }
    }

    @Test
    public void testRoute() throws InterruptedException {
        endEndpoint.expectedMessageCount(1);
        errorEndpoint.expectedMessageCount(0);

        testProducer.sendBody("<name>test</name>");

        endEndpoint.assertIsSatisfied();
        errorEndpoint.assertIsSatisfied();
    }
}