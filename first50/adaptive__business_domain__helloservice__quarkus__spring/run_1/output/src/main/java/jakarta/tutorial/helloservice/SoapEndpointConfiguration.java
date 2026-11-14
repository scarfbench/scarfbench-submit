package jakarta.tutorial.helloservice;

import jakarta.tutorial.helloservice.ejb.HelloServiceBean;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapEndpointConfiguration {
    @Bean
    public Endpoint helloServiceEndpoint(Bus bus, HelloServiceBean helloService) {
        EndpointImpl endpoint = new EndpointImpl(bus, helloService);
        endpoint.publish("/");
        return endpoint;
    }
}
