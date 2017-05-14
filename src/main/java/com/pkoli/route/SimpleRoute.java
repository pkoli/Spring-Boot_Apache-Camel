package com.pkoli.route;

/**
 * Created by Pavan on 14-05-2017.
 */
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SimpleRoute extends RouteBuilder {

    @Value("${simpleMessage}")
    private String message;

    @Override
    public void configure() throws Exception {
        from("spring-ws:rootqname:{http://pkoli.com}getCountryRequest?endpointMapping=#endpointMapping")
                .to("log:"+message)
                .process(new SimpleProcessor());
    }

    private static final class SimpleProcessor implements Processor{
        @Override
        public void process(Exchange exchange) throws Exception {
            String input = exchange.getIn().getBody().toString();
            exchange.getOut().setBody("<xml>Yeahhh, It's Working </xml>");
        }
    }
}