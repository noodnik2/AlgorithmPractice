package pandora;

import java.util.HashMap;
import java.util.Map;

/**
 *  Dropbox interview question:
 *  <pre>
 *      Given a pattern and a string input - find if the string follows the same
 *      pattern and return 0 or 1.
 *
 *      Examples:
 *      1) Pattern : "abba", input: "redblueredblue" should return 1.
 *      2) Pattern: "aaaa", input: "asdasdasdasd" should return 1.
 *      3) Pattern: "aabb", input: "xyzabcxzyabc" should return 0.
 *  </pre>
 */
public class DropboxStringPattern {

    /**
     *
     * def wordpattern(pattern, input_string, mapping = None):
     *     if mapping is None: mapping = {}
     *     if not len(pattern) and not len(input_string): return 1
     *     for idx, _ in enumerate(input_string):
     *         sub = input_string[0:idx + 1]
     *         saved = mapping.get(pattern[0])
     *         if saved:
     *             # We are in the call stack that first set the key and its time to try the next possibility
     *             if saved[1] == len(pattern): mapping[pattern[0]][0] = sub
     *             # sub does not meet expected string. Could add early return if
     *             # len(saved[0]) &gt; len(sub)
     *             if sub != saved[0]: continue
     *         else:
     *             mapping[pattern[0]] = [sub, len(pattern)]
     *
     *         if wordpattern(pattern[1:], input_string[idx + 1:], mapping):
     *             return 1
     *     return 0
     */

    static class ContextElement {
        ContextElement(String s, int l) { this.s = s; this.l = l; }
        String s;
        int l;
    }

    /**
     *  @param inPattern pattern to be matched (e.g., {@code "abba"})
     *  @param inString string to match (e.g., {@code "redbluebluered"})
     *  @param inContext recursive context, or {@code null} for {@code root}
     *  @return {@code true} iff string matches pattern
     */
    static boolean isMatch(
        final String inPattern,
        final String inString,
        final Map<String, ContextElement> inContext
    ) {

        if (inPattern.isEmpty() && inString.isEmpty()) {
            return true;
        }

        final Map<String, ContextElement> context = inContext != null ? inContext : new HashMap<>();

        final String patternFirstC = inPattern.substring(0, 1);
        final String patternRest = inPattern.substring(1);
        final int patternLength = inPattern.length();
        for (int idx = 0; idx < inString.length(); idx++) {
            final String sub = inString.substring(0, idx + 1);
            final ContextElement ce = context.get(patternFirstC);
            if (ce != null) {
                // We're in the call stack that first set the key; it's time to try the next possibility
                if (ce.l == patternLength) {
                    ce.s = sub;
                }
                // sub does not meet expected string.
                if (!sub.equals(ce.s)) {
                    continue;
                }
            }
            else {
                context.put(patternFirstC, new ContextElement(sub, patternLength));
            }
            if (isMatch(patternRest, inString.substring(idx + 1), context)) {
                return true;
            }
        }

        return false;
    }


    public static void main(final String[] inArgs) {
        testIsMatch("abba", "abba");
        testIsMatch("abba", "redbluebluered");
        testIsMatch("abba", "redbluebluebluered");
        testIsMatch("abba", "redbluered");
    }

    private static void testIsMatch(final String inPattern, final String inString) {
        final boolean answer = isMatch(inPattern, inString, null);
        System.out.format("(%s,%s) => %s\n", inPattern, inString, answer);
    }

}
