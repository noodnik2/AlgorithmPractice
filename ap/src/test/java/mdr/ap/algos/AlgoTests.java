package mdr.ap.algos;

import static mdr.ap.util.TestLogger.log;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

/**
 *  Tests of algorithm package implementations
 *  @author mross
 */
public class AlgoTests {
    
    
    //
    //  Public instance methods
    //
    
    @Test
    public void tesGcdNormalCases() {
        assertGcdNormalCase(2, 240, 46);
        assertGcdNormalCase(4, 8, 12);
        assertGcdNormalCase(1, 10, 3);
        assertGcdNormalCase(1, 3, 10);
        assertGcdNormalCase(5, 10, 5);
        assertGcdNormalCase(5, -10, 5);
        assertGcdNormalCase(5, -10, -5);
        assertGcdNormalCase(5, 10, -5);
    }
    
    @Test
    public void tesGcdExceptionCases() {
        assertGcdExceptionCase(1, 0);
        assertGcdExceptionCase(0, 1);
    }

    
    //
    //  Private instance methods
    //
    
    private void assertGcdExceptionCase(
        final long inN1,
        final long inN2
    ) {
        try {
            new EuclidsGcdFinder().findGcd(inN1,  inN2);
            fail("expected exception was not thrown");
        } catch(final IllegalArgumentException e) {
            log(String.format("findGcd(%s, %s); expected exception(%s) caught", inN1, inN2, e));
        }
    }
    
    private void assertGcdNormalCase(
        final long inExpectedGcd, 
        final long inN1, 
        final long inN2
    ) {
        final long gcd = new EuclidsGcdFinder().findGcd(inN1,  inN2);
        if (gcd != inExpectedGcd) {
            fail(String.format("findGcd(%s, %s); returned(%s), expected(%s)", inN1, inN2, gcd, inExpectedGcd));
            //notreached
        }
    }

}
