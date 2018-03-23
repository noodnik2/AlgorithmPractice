package pandora;

/**
 *  Jan 30 Pandora interview challenge: parse a number from a string
 */
class SimpleString2NumberParser {

    public static void main(String[] args) {
        System.out.println("Hello, world! - " + convertToFloat("234.567"));
    }

    private static double convertToFloat(final String inInput) {
        final String[] parts = inInput.split("\\.");
        if (parts.length == 1) {
            return (double) convertToNumber(inInput);
        }
        if (parts.length != 2) {
            throw new IllegalArgumentException(); // add meaningful message
        }
        final long lhs = convertToNumber(parts[0]);
        final long rhs = convertToNumber(parts[1]);
        return (double) lhs + ((double) rhs / Math.pow(10, parts[1].length()));
    }

    private static long convertToNumber(final String inInput) {
        long a = 0L;
        for (final char c : inInput.toCharArray()) {
            a *= 10;
            a = a + (long) (c - '0');
        }
        return a;
    }

}