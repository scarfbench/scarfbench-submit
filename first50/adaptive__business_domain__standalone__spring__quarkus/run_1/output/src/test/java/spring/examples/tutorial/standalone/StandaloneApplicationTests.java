package spring.examples.tutorial.standalone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.logging.Logger;

import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import io.quarkus.test.junit.QuarkusTest;
import quarkus.examples.tutorial.StandaloneBean;

@QuarkusTest
class StandaloneApplicationTests {

	private static final Logger logger = Logger.getLogger("standalone.service");

	@Inject
	StandaloneBean standaloneBean;

	@Test
	void contextLoads() {}

	@Test
	public void testReturnMessage() throws Exception {
		logger.info("Testing StandaloneBean.returnMessage()");
		String expResult = "Greetings!";
		String result = standaloneBean.returnMessage();
		assertEquals(expResult, result);
	}

}
