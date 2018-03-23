package pandora;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.MULTILINE;

/**
 *  Pandora interview challenge: implement a simple Regex parser
 */
public class RegexParser {

    private static class RegexTestCase {

        RegexTestCase(final String inRegex, final String inText, boolean inIsMatch) {
            _regex = inRegex;
            _text = inText;
            _isMatch = inIsMatch;
        }

        public String getRegex() {
            return _regex;
        }

        public String getText() {
            return _text;
        }

        boolean isMatch() {
            return _isMatch;
        }

        private final String _regex;
        private final String _text;
        private final boolean _isMatch;
    }

    /**
     *  @param inArgs args
     */
    public static void main(final String[] inArgs) {

//        final String[] res = {
//            "ab",
//            "aba",
//            "abb",
//            "a.d",
//            "a..d",
//            "a.*d",
//            "bd",
//            "ab*d",
//        };
//        final String[] texts = {
//            "ab",
//            "aba",
//            "abb",
//            "abc",
//            "abd",
//            "abbd",
//        };

        final RegexTestCase[] testCases = new RegexTestCase[] {
            new RegexTestCase("^abc", "abcd", true),
            new RegexTestCase("bcd$", "abcd", true),
            new RegexTestCase("^bc", "abcd", false),
            new RegexTestCase("abc$", "abcd", false),
            new RegexTestCase("a.*d", "abbd", true),
            new RegexTestCase("ab*d", "abbd", true)
        };

        //  to fix:
        //      re(a.*d, abbd) => false
        //      re(ab*d, abbd) => false
        //
        final RegexEvaluator kpRe = new ReconstructionOfKernighanAndPike1999Article();
        final RegexEvaluator jRe = new JavasRegex();
        int nFailures = 0;
        for (final RegexTestCase rtc : testCases) {
            final boolean kpIsMatch = kpRe.isMatch(rtc.getRegex(), rtc.getText());
            final boolean jIsMatch = jRe.isMatch(rtc.getRegex(), rtc.getText());
            final String result = String.format("re(%s, %s) => %s", rtc.getRegex(), rtc.getText(), kpIsMatch);
            if (kpIsMatch != jIsMatch) {
                System.err.println("error: " + result + " (we thought: " + rtc.isMatch() + ")");
                nFailures++;
                continue;
            }
            System.out.println("ok: " + result);
        }

        System.out.println(String.format("nFailures = %s", nFailures));
//        for (final String re : res) {
//            for (final String text : texts) {
//                final boolean match = kpRe.isMatch(re, text);
//                System.out.println(String.format("re(%s, %s) => %s", re, text, match));
//            }
//        }

    }

    /**
     *  @param inExpectedTrue value, which is expected to be {@code true}
     *  @param inMessage failure message to print when {@code inExpectedTrue} parameter value is not {@code true}
     *  @throws Exception exception to throw when {@code inExpectedTrue} parameter value is not {@code true}
     */
    private static void assertTrue(final boolean inExpectedTrue, final String inMessage) throws Exception {
        if (inExpectedTrue) {
            return;
        }
        System.out.println(inMessage);
        throw new Exception(String.format("assertion failure: %s", inMessage));
    }

    private interface RegexEvaluator {

        boolean isMatch(char[] inExpr, char[] inText);

        default boolean isMatch(String inExpr, String inText) {
            return isMatch(inExpr.toCharArray(), inText.toCharArray());
        }
    }

    public static class JavasRegex implements RegexEvaluator {

        @Override
        public boolean isMatch(char[] inExpr, char[] inText) {
            final Pattern compile = Pattern.compile(new String(inExpr), MULTILINE);
            final Matcher matcher = compile.matcher(new String(inText));
            return matcher.matches();
        }
    }

    public static class ReconstructionOfKernighanAndPike1999Article implements RegexEvaluator {

        /**
         *  From the Kernhigan & Pike article:
         *  <pre>
         *  // match: search for re anywhere in text
         *  int match(char *re, char *text)
         *  {
         *      if (re[0] == '^')
         *          return matchhere(re+1, text);
         *      do {  // must look at empty string
         *          if (matchhere(re, text))
         *              return 1;
         *      } while (*text++ != '\0');
         *      return 0;re(ab*d, abbd) => false
         *  }
         *  @param re needle
         *  @param text haystack
         *  @return true or false
         */
        public boolean isMatch(final char[] re, final char[] text) {

            if (re.length > 0 && re[0] == '^') {
                return isMatchHere(Arrays.copyOfRange(re, 1, re.length), text);
            }

            int i = 0;
            do {    // must look at empty stringimplements RegexEvaluator
                if (isMatchHere(re, Arrays.copyOfRange(text, i, text.length))) {
                    return true;
                }
            } while(i++ < text.length);

            return false;
        }

        /**
         *  From the Kernhigan & Pike article:
         *  <pre>
         *  int matchhere(char *re, char *text)
         *  {
         *      if (re[0] == '\0')
         *          return 1;
         *      if (re[1] == '*')
         *          return matchstar(re[0], re + 2, text);
         *      if (re[0] == '$' && re[1] == '\0')
         *          return *text == '\0';
         *      if (*text != '\0' && (re[0] == '.' || re[0] == * text))
         *          return matchhere(re + 1, text + 1);
         *      return 0;
         *  }
         *  </pre>
         *  @param re needle
         *  @param text haystack
         *  @return true or false
         */
        public boolean isMatchHere(final char[] re, final char[] text) {
            if (re.length == 0) {
                return true;
            }
            if (re.length > 1 && re[1] == '*') {
                return isMatchStar(re[0], Arrays.copyOfRange(re, 2, re.length), text);
            }
            if (re.length == 1 && re[0] == '$') {
                return text.length == 0;
            }
            if (text.length != 0 && (re[0] == '.' || re[0] == text[0])) {
                return isMatchHere(Arrays.copyOfRange(re, 1, re.length), Arrays.copyOfRange(text, 1, text.length));
            }
            return false;
        }

        /**
         *  <pre>
         *  int matchstar(int c, char *re, char *text)
         *  {
         *      do {  // a '*' matches zero or more instances
         *          if (matchhere(re, text))
         *              return 1;
         *      } while (*text!='\0' && (*text++==c || c=='.'));
         *      return 0;
         *  }
         *  </pre>
         *  @param c character to consider
         *  @param re regular expression to match
         *  @param text text to be matched
         *  @return {@code true} iff matches
         */
        private boolean isMatchStar(char c, char[] re, char[] text) {
            int i = 0;
            do { // a '*' matches zero or more instances
                if (isMatchHere(re, text)) {
                    return true;
                }
            } while(i < text.length && (text[i++] == c || c == '.'));
            return false;
        }

    // another version of "matchstar"
//        /* matchstar: leftmost longest search for c*re */
//        int matchstar(int c, char *re, char *text)
//        {
//            char *t;
//
//            for (t = text; *t != '\0' && (*t == c || c == '.'); t++)
//            ;
//            do {   /* * matches zero or more */
//                if (matchhere(re, t))
//                    return 1;
//            } while (t-- > text);
//            return 0;
//        }

    }


    public static class MartysFirstAttemptAtPandora implements RegexEvaluator {

        @Override
        public boolean isMatch(char[] inExpr, char[] inText) {
            return isMatchInternal(new String(inExpr), new String(inText));
        }

        public boolean isMatchInternal(final String inNeedle, final String inHaystack) {

            int offset = 0;
            while(offset < inHaystack.length()) {
                for (final String token : tokenize(inNeedle)) {
                    final char[] op = parseOp(token);
                    if (op != null) {
                        final int afterRunOffset = advancePastRun(inHaystack, op[0], offset);
                        switch (op[1]) {
                            case '*':
                                break;
                            case '+':
                                break;
                            default:
                                opError(op);
                                break;
                        }
                    }
                    final int nextOffset = getOffsetOf(inHaystack, token, offset);
                    if (nextOffset < 0) {
                        return false;
                    }
                    offset = nextOffset + token.length();
                }
            }

            return false;
        }

        private int advancePastRun(String inHaystack, char c, int offset) {
            return 0;
        }

        private void opError(char[] op) {
        }


        private char[] parseOp(String token) {
            return null;
        }

        /**
         *
         * @param inHaystack source string to match
         * @param inToken token to look for within {@code inHaystack}
         * @param inOffset offset (within {@code inHaystack} )to start looking
         * @return -1 if not found, otherwise offset within the string starting at inOffset
         */
        private int getOffsetOf(final String inHaystack, final String inToken, final int inOffset) {
            // reimplement later
            return 0;
        }

        /**
         *
         * @param inNeedle supported regexp expression
         * @return tokens with literals separated from operations
         */
        private String[] tokenize(final String inNeedle) {
            return new String[0];
        }

    }

}
