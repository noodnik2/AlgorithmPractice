import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *  Pandora implementation challenge: digital palindrome finder
 */
public class DigitPalindromeFinder {

    // Feb 6th Pandora interview challenge
    public static boolean isDigitPalindrome(final int inNumber) {
        final char[] numberAsCa = String.valueOf(inNumber).toCharArray();
        final int length = numberAsCa.length;
        for (int i = 0; i < length / 2; i++) {
            if (numberAsCa[i] != numberAsCa[length - i - 1]) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test101() {
        Assertions.assertTrue(isDigitPalindrome(101));
    }

    @Test
    public void test1010() {
        Assertions.assertFalse(isDigitPalindrome(1010));
    }

    @Test
    public void test10101() {
        Assertions.assertTrue(isDigitPalindrome(10101));
    }

}
