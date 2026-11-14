package jakarta.tutorial.decorators;

import jakarta.enterprise.context.Dependent;

@Dependent
public class CoderImpl implements Coder {
    @Logged
    @Override
    public String codeString(String input, int shift) {
        StringBuilder result = new StringBuilder(input);
        for (int index = 0; index < result.length(); index++) {
            int original = result.codePointAt(index);
            int shifted = original + shift;
            if (original == ' ') {
                shifted = ' ';
            } else if (original >= 'A' && original <= 'Z') {
                if (shifted > 'Z') shifted -= 26;
            } else if (original >= 'a' && original <= 'z') {
                if (shifted > 'z') shifted -= 26;
            } else {
                shifted = original;
            }
            result.setCharAt(index, (char) shifted);
        }
        return result.toString();
    }
}
