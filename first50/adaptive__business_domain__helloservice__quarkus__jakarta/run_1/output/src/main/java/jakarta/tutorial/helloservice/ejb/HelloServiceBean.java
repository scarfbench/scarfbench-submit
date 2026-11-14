package jakarta.tutorial.helloservice.ejb;

import jakarta.ejb.Stateless;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;

@WebService
@Stateless
public class HelloServiceBean {
    private final String message = "Hello, ";

    public HelloServiceBean() {
    }

    @WebMethod
    public String sayHello(String name) {
        return message + name + ".";
    }
}
