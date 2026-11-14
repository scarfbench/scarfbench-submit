package jakarta.tutorial.decorators;

import jakarta.annotation.Priority;
import jakarta.decorator.Decorator;
import jakarta.decorator.Delegate;
import jakarta.inject.Inject;

@Decorator
@Priority(10)
public class CoderDecorator implements Coder {
    @Inject
    @Delegate
    Coder delegate;

    @Override
    public String codeString(String input, int shift) {
        return "\"" + input + "\" becomes \"" + delegate.codeString(input, shift)
                + "\", " + input.length() + " characters in length";
    }
}
