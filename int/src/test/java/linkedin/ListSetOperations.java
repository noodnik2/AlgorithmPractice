package linkedin;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 *  LinkedIn coding challenge: Basic Set operations on Lists
 *  @author Marty Ross
 */
@SuppressWarnings({"Since15", "WeakerAccess"})
class ListSetOperations {


    //
    //  Union
    //

    // union of lists 'a' and 'b'
    // (nulls will never be encountered)
    static List<Integer> union(List<Integer> a, List<Integer> b) {
        List<Integer> result = new ArrayList<>(a.size() + b.size());
        Iterator<Integer> ai = a.iterator();
        Iterator<Integer> bi = b.iterator();
        Integer aval = ai.hasNext() ? ai.next() : null;
        Integer bval = bi.hasNext() ? bi.next() : null;
        while(aval != null || bval != null) {
            if (aval != null && (bval == null || aval <= bval)) {
                result.add(aval);
                aval = ai.hasNext() ? ai.next() : null;
            }
            if (bval != null && (aval == null || bval <= aval)) {
                result.add(bval);
                bval = bi.hasNext() ? bi.next() : null;
            }
        }
        return result;
    }


    @Test
    public void testUnionEmpty() {
        assertUnion(List.of(), List.of(), List.of());
    }

    @Test
    public void testUnionLeftEmpty() {
        assertUnion(List.of(4, 5, 6), List.of(), List.of(4, 5, 6));
    }

    @Test
    public void testUnionRightEmpty() {
        assertUnion(List.of(1, 2, 3), List.of(1, 2, 3), List.of());
    }

    @Test
    public void testUnionSimpleAppend() {
        assertUnion(List.of(1, 2, 3, 4, 5, 6), List.of(1, 2, 3), List.of(4, 5, 6));
    }

    void assertUnion(
        final List<Integer> expected,
        final List<Integer> lhs,
        final List<Integer> rhs
    ) {
        final List<Integer> actual = union(lhs, rhs);
        System.out.printf("union(%s,%s) => %s\n", lhs, rhs, actual);
        assertEquals(expected, actual);
    }


    //
    //  Intersection
    //

    //  intersection of lists 'a' and 'b'
    //  (nulls will never be encountered)
    static List<Integer> intersection(List<Integer> a, List<Integer> b) {
        List<Integer> result = new ArrayList<>(a.size() + b.size());
        Iterator<Integer> ai = a.iterator();
        Iterator<Integer> bi = b.iterator();
        Integer aval = ai.hasNext() ? ai.next() : null;
        Integer bval = bi.hasNext() ? bi.next() : null;
        while(aval != null && bval != null) {
            if (aval < bval) {
                aval = ai.hasNext() ? ai.next() : null;
                continue;
            }
            if (bval < aval) {
                bval = bi.hasNext() ? bi.next() : null;
                continue;
            }
            result.add(aval);
            aval = ai.hasNext() ? ai.next() : null;
            bval = bi.hasNext() ? bi.next() : null;
        }
        return result;
    }


    @Test
    public void testIntersectionEmpty() {
        assertIntersection(List.of(), List.of(), List.of());
    }

    @Test
    public void testIntersectionLeftEmpty() {
        assertIntersection(List.of(), List.of(), List.of(4, 5, 6));
    }

    @Test
    public void testIntersectionRightEmpty() {
        assertIntersection(List.of(), List.of(1, 2, 3), List.of());
    }

    @Test
    public void testIntersectionSimpleAppend() {
        assertIntersection(List.of(), List.of(1, 2, 3), List.of(4, 5, 6));
    }

    @Test
    public void testIntersectionDuplicates() {
        assertIntersection(List.of(2, 4), List.of(1, 2, 3, 4, 5, 6), List.of(2, 4));
    }


    void assertIntersection(
        final List<Integer> expected,
        final List<Integer> lhs,
        final List<Integer> rhs
    ) {
        final List<Integer> actual = intersection(lhs, rhs);
        System.out.printf("intersection(%s,%s) => %s\n", lhs, rhs, actual);
        assertEquals(expected, actual);
    }

}
