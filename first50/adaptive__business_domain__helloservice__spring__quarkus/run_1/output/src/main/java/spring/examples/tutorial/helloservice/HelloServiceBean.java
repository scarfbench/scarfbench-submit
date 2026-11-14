package spring.examples.tutorial.helloservice;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;

@WebService(serviceName = "HelloServiceBeanService", portName = "HelloPort",
        targetNamespace = HelloServiceBean.NAMESPACE_URI)
public class HelloServiceBean {
    static final String NAMESPACE_URI = "http://ejb.helloservice.tutorial.jakarta/";

    @WebMethod(operationName = "sayHello")
    @WebResult(name = "return", targetNamespace = NAMESPACE_URI)
    public String sayHello(@WebParam(name = "arg0", targetNamespace = NAMESPACE_URI) String name) {
        return "Hello, " + name + ".";
    }
}
