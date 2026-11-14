package jakarta.tutorial.encoder;

import jakarta.enterprise.context.ApplicationScoped;

/** Default coder implementation that shifts ASCII letters. */
@ApplicationScoped
public class CoderImpl implements Coder {

    @Override
    public String codeString(String s, int tval) {
        final int space = 32;
        final int capitalA = 65;
        final int capitalZ = 90;
        final int smallA = 97;
        final int smallZ = 122;

        StringBuilder result = new StringBuilder(s);
        for (int i = 0; i < result.length(); i++) {
            int codePoint = result.codePointAt(i);
            int shifted = codePoint + tval;
            if (codePoint == space) {
                shifted = space;
            }
            if (codePoint >= capitalA && codePoint <= capitalZ) {
                if (shifted > capitalZ) {
                    shifted -= 26;
                }
            } else if (codePoint >= smallA && codePoint <= smallZ) {
                if (shifted > smallZ) {
                    shifted -= 26;
                }
            } else {
                shifted = codePoint;
            }
            result.setCharAt(i, (char) shifted);
        }
        return result.toString();
    }
}
