package jakarta.tutorial.encoder;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.inject.Alternative;

/** Alternative coder selected explicitly by Quarkus test or deployment profiles. */
@ApplicationScoped
@Alternative
public class TestCoderImpl implements Coder {

    @Override
    public String codeString(String s, int tval) {
        return "input string is " + s + ", shift value is " + tval;
    }
}
