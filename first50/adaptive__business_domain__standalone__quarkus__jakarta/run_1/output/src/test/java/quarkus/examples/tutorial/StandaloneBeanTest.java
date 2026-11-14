package quarkus.examples.tutorial;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.logging.Logger;

import jakarta.tutorial.standalone.ejb.StandaloneBean;
import org.junit.jupiter.api.Test;

class StandaloneBeanTest {
    private static final Logger logger = Logger.getLogger("standalone");

    @Test
    void testReturnMessage() {
        logger.info("Testing StandaloneBean.returnMessage()");
        String expResult = "Greetings!";
        String result = new StandaloneBean().returnMessage();
        assertEquals(expResult, result);
    }
}
