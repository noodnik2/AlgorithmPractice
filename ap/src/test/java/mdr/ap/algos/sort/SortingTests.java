package mdr.ap.algos.sort;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

import static mdr.ap.algos.sort.SortingTests.FlagColors.BLUE;
import static mdr.ap.algos.sort.SortingTests.FlagColors.RED;
import static mdr.ap.algos.sort.SortingTests.FlagColors.WHITE;

@SuppressWarnings("Since15")
public class SortingTests {

    // Heapsort tests

    @Test
    public void testHeapSort4() {
        testSorting(new HeapSorter(), List.of(3, 2, 1, 2));
    }

    @Test
    public void testHeapSort7() {
        testSorting(new HeapSorter(), List.of(4, 6, 3, 1, 7, 2, 5));
    }

    @Test
    public void testHeapSort8() {
        testSorting(new HeapSorter(), List.of(4, 6, 3, 1, 7, 2, 5, 0));
    }


    // NSorter tests

    @Test
    public void testNSorterTripleInt() {
        testSorting(new NSorter(), List.of(0, -1, 1, 1, 1, -1, 0, -1, 0, 0, 1, 1, -1));
    }


    enum FlagColors { RED, WHITE, BLUE }

    @Test
    public void testNSorterColors() {
        testSorting(new NSorter(), List.of(BLUE, RED, RED, BLUE, WHITE, BLUE, WHITE, RED, WHITE));
    }

    @Test
    public void testNSorterSundry() {
        testSorting(new NSorter(), List.of(6, 2, 3, 5, 2, 1, 7, 4, 3, 88, 100001, -60));
    }


    private static <CT extends Comparable> boolean isSorted(final List<CT> inList) {
        int i ;
        for (i = 0; i < inList.size() - 1; i++) {
             if (inList.get(i).compareTo(inList.get(i + 1)) > 0) break;
        }
        return i == inList.size() - 1;
    }

    private static <CT extends Comparable> void testSorting(
        final ISorter inSorter,
        final List<CT> inUnsorted
    ) {

        assert !isSorted(inUnsorted) : "input is not unsorted";
        final ArrayList<CT> toSortList = new ArrayList<>(inUnsorted);
        inSorter.sort(toSortList);
        System.out.format("%s <= %s\n", toSortList, inUnsorted);
        assert isSorted(toSortList) : "sorting failed";
    }

}
