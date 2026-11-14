package jakarta.tutorial.encoder;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AlternateCoderBeanTest {

    @Test
    public void testEncodeFlow() {
        CoderBean coderBean = new CoderBean();
        coderBean.coder = new TestCoderImpl();
        assertThat("CoderBean should not be null", coderBean, notNullValue());
        coderBean.setInputString("aa2");
        coderBean.setTransVal(2);
        coderBean.encodeString();

        assertThat(coderBean.getCodedString(),
                containsString("input string is aa2, shift value is 2"));
    }

}
