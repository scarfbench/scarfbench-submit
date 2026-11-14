package spring.examples.tutorial.standalone;

import static org.junit.jupiter.api.Assertions.assertEquals;

import jakarta.tutorial.standalone.ejb.StandaloneBean;
import java.util.logging.Logger;
import org.junit.jupiter.api.Test;

class StandaloneApplicationTests {
    private static final Logger logger = Logger.getLogger("standalone.bean");

    @Test
    void contextLoads() {
    }

    @Test
    void testReturnMessage() throws Exception {
        logger.info("Testing StandaloneBean.returnMessage()");
        String expResult = "Greetings!";
        String result = new StandaloneBean().returnMessage();
        assertEquals(expResult, result);
    }
}
