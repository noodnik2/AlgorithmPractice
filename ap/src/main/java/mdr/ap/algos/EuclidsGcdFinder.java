package mdr.ap.algos;

/**
 *  Example implementation of Euclid's Algorithm for finding Greatest Common Divisor (GCD)
 *  @author mross
 */
public class EuclidsGcdFinder {

    /**
     *  Calculates the greatest common divisor (gcd) of two or more non-zero integers, which
     *  is the largest positive integer that divides each of the integers.   The greatest common
     *  divisor is also known as the greatest common factor (gcf), highest common factor (hcf),
     *  greatest common measure (gcm), or highest common divisor.
     *  @param inN1 one number
     *  @param inN2 another number
     *  @return greatest common divisor of {@code inN1} and {@code inN2}
     *  is the largest positive integer that divides each of the integers
     *  @throws IllegalArgumentException if either argument is zero
     */
    public long findGcd(final long inN1, final long inN2) throws IllegalArgumentException {
    
        if (inN1 == 0L || inN2 == 0L) {
            throw new IllegalArgumentException("zero values not allowed");
        }
        
        long nLarger, nSmaller;
        if (inN1 > inN2) {
            nLarger = inN1;
            nSmaller = inN2;
        } else {
            nLarger = inN2;
            nSmaller = inN1;
        }        
        
        long nRemainder;
        while((nRemainder = nLarger % nSmaller) != 0) { 
            nLarger = nSmaller;
            nSmaller = nRemainder;
        }
        
        return Math.abs(nSmaller);
    }
    
}
