package quarkus.examples.tutorial;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.Test;

import spring.examples.tutorial.standalone.StandaloneApplication;
import spring.examples.tutorial.standalone.service.StandaloneService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.logging.Logger;

@SpringBootTest(classes = StandaloneApplication.class)
class StandaloneBeanTest {
    private static final Logger logger = Logger.getLogger("standalone");

    @Autowired
    private StandaloneService standaloneService;

    @Test
    public void testReturnMessage() throws Exception {
        logger.info("Testing StandaloneBean.returnMessage()");
        String expResult = "Greetings!";
        String result = standaloneService.returnMessage();
        assertEquals(expResult, result);
    }
}
