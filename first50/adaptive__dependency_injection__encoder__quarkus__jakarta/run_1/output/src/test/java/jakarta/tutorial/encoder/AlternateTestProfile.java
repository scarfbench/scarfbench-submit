package jakarta.tutorial.encoder;

import java.util.Set;

/**
 * Jakarta EE equivalent of the source framework's alternate test profile.
 *
 * <p>The target runtime selects CDI alternatives through bean archive metadata,
 * so this helper describes the alternative used by the supplied test without
 * retaining a dependency on the Quarkus test runtime.</p>
 */
public final class AlternateTestProfile {

    public Set<Class<? extends Coder>> getAlternatives() {
        return Set.of(TestCoderImpl.class);
    }
}
