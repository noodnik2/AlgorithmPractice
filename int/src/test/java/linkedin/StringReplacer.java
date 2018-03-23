package linkedin;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  LinkedIn coding challenge: String Replacer
 *  @author Marty Ross
 */
@SuppressWarnings("WeakerAccess")
final class StringReplacer {

    // replace all instances of "find" in "org" with "repl"
    // no nulls will ever be encountered
    static String replace(String orig, String find, String repl) {
        if (orig.length() == 0 || find.length() == 0) {
            return orig;
        }
        StringBuilder result = new StringBuilder();
        int pos = 0;
        int findLen = find.length();
        for (;;) {
            int index = orig.indexOf(find, pos);
            if (index < 0) {
                break;
            }
            result.append(orig, pos, index);    // oops! fixed params
            result.append(repl);
            pos = index + findLen;
        }
        if (pos < orig.length()) {              // oops! added clause
            result.append(orig.substring(pos));
        }
        return result.toString();
    }

    @Test
    public void testLeftEdge() {
        assertReplacement("exchangeme", "changeme", "c", "exc");
    }

    @Test
    public void testRightEdge() {
        assertReplacement("changeyou", "changeme", "me", "you");
    }

    @Test
    public void testFindEmpty() {
        assertReplacement("changeme", "changeme", "", "you");
    }

    @Test
    public void testReplEmpty() {
        assertReplacement("change", "changeme", "me", "");
    }

    @Test
    public void testMidSeveral() {
        assertReplacement("thzxs zxs the orzxgzxnal strzxng",
                          "this is the original string", "i", "zx");
    }

    static void assertReplacement(String expected, String orig, String find, String repl) {
        final String actual = replace(orig, find, repl);
        System.out.printf(
            "\"%s\" => \"%s\" after replacing \"%s\" with \"%s\"",
            orig, actual, find, repl
        );
        assertEquals(expected, actual);
    }

}
