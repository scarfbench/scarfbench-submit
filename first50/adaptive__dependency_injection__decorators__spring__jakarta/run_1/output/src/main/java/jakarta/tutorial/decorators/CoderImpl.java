package jakarta.tutorial.decorators;

import jakarta.enterprise.context.Dependent;

@Dependent
@Logged
public class CoderImpl implements Coder {
    @Override
    public String codeString(String source, int shift) {
        StringBuilder result = new StringBuilder(source);
        for (int i = 0; i < result.length(); i++) {
            int codePoint = result.codePointAt(i);
            int shifted = codePoint + shift;
            if (codePoint == ' ') {
                shifted = ' ';
            } else if (codePoint >= 'A' && codePoint <= 'Z') {
                if (shifted > 'Z') shifted -= 26;
            } else if (codePoint >= 'a' && codePoint <= 'z') {
                if (shifted > 'z') shifted -= 26;
            } else {
                shifted = codePoint;
            }
            result.setCharAt(i, (char) shifted);
        }
        return result.toString();
    }
}
