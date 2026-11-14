package jakarta.tutorial.decorators;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;

public class CoderBeanTest {
    @Test
    public void testEncodeFlow() {
        CoderDecorator decorator = new CoderDecorator() { };
        decorator.coder = new CoderImpl();

        CoderBean coderBean = new CoderBean();
        coderBean.coder = decorator;
        coderBean.setInputString("aa2");
        coderBean.setTransVal(2);
        coderBean.encodeString();

        String responseHtml = coderBean.getCodedString().replace("\"", "&quot;");
        assertThat(responseHtml,
                containsString("&quot;aa2&quot; becomes &quot;cc2&quot;, 3 characters in length"));
    }
}
