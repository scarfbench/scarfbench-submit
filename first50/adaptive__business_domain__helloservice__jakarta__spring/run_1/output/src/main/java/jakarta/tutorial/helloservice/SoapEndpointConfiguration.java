package jakarta.tutorial.helloservice;

import jakarta.xml.ws.Endpoint;
import jakarta.tutorial.helloservice.ejb.HelloServiceBean;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SoapEndpointConfiguration {
    @Bean
    Endpoint helloServiceEndpoint(Bus bus, HelloServiceBean helloServiceBean) {
        EndpointImpl endpoint = new EndpointImpl(bus, helloServiceBean);
        endpoint.publish("/");
        return endpoint;
    }
}
