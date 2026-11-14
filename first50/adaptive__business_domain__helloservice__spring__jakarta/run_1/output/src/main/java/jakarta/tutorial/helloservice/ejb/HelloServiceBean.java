package jakarta.tutorial.helloservice.ejb;

import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;

@Stateless
@WebService(
        serviceName = "HelloServiceBean",
        portName = "HelloPort",
        targetNamespace = "http://ejb.helloservice.tutorial.jakarta/")
public class HelloServiceBean {

    @WebMethod
    public String sayHello(@WebParam(name = "arg0") String name) {
        return "Hello, " + name + ".";
    }
}
