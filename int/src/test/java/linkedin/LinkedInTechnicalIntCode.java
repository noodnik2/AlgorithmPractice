package linkedin;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *  LinkedIn technical interview coding challenge
 */
@SuppressWarnings("WeakerAccess")
public class LinkedInTechnicalIntCode {


    // Problem #1: "can place flowers"

    @Test
    public void testCanPlaceFlowers1() {
        assertCanPlaceFlowers(
            new int[] {1,0,0,0,0,0,1,0,0},
            new int[] {3},
            new int[] {4}
        );
    }

    @Test
    public void testCanPlaceFlowers2() {
        assertCanPlaceFlowers(
            new int[] {1,0,0,1,0,0,1,0,0},
            new int[] {1},
            new int[] {2}
        );
    }

    @Test
    public void testCanPlaceFlowers3() {
        assertCanPlaceFlowers(
            new int[] {0},
            new int[] {1},
            new int[] {2}
        );
    }

    static void assertCanPlaceFlowers(
        final int[] input,
        final int[] trueValues,
        final int[] falseValues
    ) {
        final List<Boolean> methodInput = new ArrayList<>(input.length);
        for (final int inputVal : input) {
            methodInput.add(inputVal != 0);
        }
        int nFailures = 0;
        for (final int trueValue : trueValues) {
            if (!canPlaceFlowers(methodInput, trueValue)) {
                System.out.format("false for(%s) with(%s)\n", trueValue, methodInput);
                nFailures++;
            }
        }
        for (final int falseValue : falseValues) {
            if (canPlaceFlowers(methodInput, falseValue)) {
                System.out.format("true for(%s) with(%s)\n", falseValue, methodInput);
                nFailures++;
            }
        }
        if (nFailures > 0) {
            Assertions.fail(String.format("%s produced %s failure(s)", methodInput, nFailures));
        }
    }

    // -----
    // Input: [1,0,0,0,0,0,1,0,0]
    //
    //    3 => true
    //    4 => false
    //
    // Input: [1,0,0,1,0,0,1,0,0]
    //
    //    1 => true
    //    2 => false
    //
    // Input: [0]
    //
    //    1 => true
    //    2 => false

    static boolean canPlaceFlowers(List<Boolean> flowerbed, int numberToPlace) {
        if (numberToPlace <= 0) {
            return true;
        }
        int count = 0;
        final int flowerbedSize = flowerbed.size();
        for (int index = 0; index < flowerbedSize; index++) {
            if (
                !flowerbed.get(index)   // already a flower there
             && (index == 0 || !flowerbed.get(index - 1)) // edge or empty
             && (index == flowerbedSize - 1 || !flowerbed.get(index + 1))
            ) {
                if (++count == numberToPlace) {
                    return true;
                }
                index++;    // skip one space on the right to obey rule
            }
        }
        return false;
    }



    // Problem #2: "TwoSum"

    //
    //  Public test methods
    //

    @Test
    public void testMartysTwoSum() {
        assertTwoSumCorrectness(new MartysTwoSum());
    }

    @Test
    public void testMartysSecondTwoSum() {
        assertTwoSumCorrectness(new MartysSecondTwoSum());
    }


    //
    //  Private class methods
    //

    static void assertTwoSumCorrectness(final TwoSum ts) {

        // For example, if the numbers 1, -2, 3, and 6 had been stored,
        for (final int i : new int[] { 1, -2, 3, 6 }) {
            ts.store(i);
        }

        int nFailures = 0;

        // the method should return true for 4, -1, and 9
        for (final int i : new int[] { 4, -1, 9 }) {
            if (!ts.test(i)) {
                System.out.format("%s failed for(%s)\n", ts, i);
                nFailures++;
            }

        }

        // but false for 10, 5, and 0
        for (final int i : new int[] { 10, 5, 0 }) {
            if (ts.test(i)) {
                System.out.format("%s failed for(%s)\n", ts, i);
                nFailures++;
            }
        }

        if (nFailures > 0) {
            Assertions.fail(String.format("%s produced %s failure(s)", ts, nFailures));
        }
    }

    // Given interface

    interface TwoSum {

        /**
         *  Stores @param input in an internal data structure.
         */
        void store(int input);

        /**
         *  Returns true if there is any pair of numbers in the internal data structure which
         *  have sum @param val, and false otherwise.
         *  For example, if the numbers 1, -2, 3, and 6 had been stored,
         *  the method should return true for 4, -1, and 9, but false for 10, 5, and 0
         */
        boolean test(int val);
    }

    // Implementations

    static class MartysTwoSum implements TwoSum {

        private final Set<Integer> _storedValues = new HashSet<>();

        // O(1)
        @Override
        public void store(final int input) {
            _storedValues.add(input);
        }

        // O(N)
        @Override
        public boolean test(final int val) {
            for (final int consider : _storedValues) {
                // looking for pair consider + lookingFor = val;
                // so, lookingFor = val - consider
                final int lookingFor = val - consider;
                if (_storedValues.contains(lookingFor)) {
                    return true;
                }
            }
            return false;
        }

    }

    static class MartysSecondTwoSum implements TwoSum {

        Set<Integer> _storedInputs = new HashSet<>();
        Set<Integer> _storedSums = new HashSet<>();

        // O(N)
        @Override
        public void store(int input) {
            _storedInputs.add(input);
            for (final int storedVal : _storedInputs) {
                if (input == storedVal) {
                    continue;
                }
                _storedSums.add(input + storedVal);
            }
        }

        // O(1)
        @Override
        public boolean test(int val) {
            return _storedSums.contains(val);
        }

    }

}
