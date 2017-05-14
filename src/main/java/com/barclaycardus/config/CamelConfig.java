package com.barclaycardus.config;

import org.apache.camel.component.spring.ws.bean.CamelEndpointMapping;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.ws.server.EndpointAdapter;
import org.springframework.ws.server.endpoint.adapter.MessageEndpointAdapter;

/**
 * Created by Pavan on 14-05-2017.
 */
@Configuration
@PropertySource("classpath:simple-camel.properties")
public class CamelConfig {

    @Bean
    public CamelEndpointMapping endpointMapping() {
        return new CamelEndpointMapping();
    }

    @Bean
    public EndpointAdapter messageEndpointAdapter() {
        return new MessageEndpointAdapter();

    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    /*@Bean
    @Primary
    public static BridgePropertyPlaceholderConfigurer bridgePropertyPlaceholderConfigurer(){
        BridgePropertyPlaceholderConfigurer propertyPlaceholderConfigurer = new BridgePropertyPlaceholderConfigurer();
        Resource[] resources = new ClassPathResource[]
                { new ClassPathResource( "simple-camel.properties" ) };
        propertyPlaceholderConfigurer.setLocations(resources);
        return propertyPlaceholderConfigurer;
    }*/

}
