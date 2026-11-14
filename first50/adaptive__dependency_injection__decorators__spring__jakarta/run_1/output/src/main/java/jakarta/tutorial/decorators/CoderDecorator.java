package jakarta.tutorial.decorators;

import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.enterprise.inject.Any;
import jakarta.inject.Inject;

@Decorator
public abstract class CoderDecorator implements Coder {
    @Inject
    @Delegate
    @Any
    private Coder delegate;

    @Override
    public String codeString(String source, int shift) {
        return "\"" + source + "\" becomes \"" + delegate.codeString(source, shift)
                + "\", " + source.length() + " characters in length";
    }
}
