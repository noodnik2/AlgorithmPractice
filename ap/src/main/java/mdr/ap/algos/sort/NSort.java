package mdr.ap.algos.sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import static mdr.ap.algos.sort.NSort.FlagColors.BLUE;
import static mdr.ap.algos.sort.NSort.FlagColors.RED;
import static mdr.ap.algos.sort.NSort.FlagColors.WHITE;

/**
 *  Handles several coding challenges encountered this month:
 *  <ul>
 *      <li>
 *          Autodesk coding challenge: sort array containing only -1, 0 and 1
 *      </li>
 *      <li>
 *          Coursera Algorithms challenge: "Dutch national flag":
 *          Given an array of n buckets, each containing a red, white, or blue pebble, sort them by color
 *      </li>
 *  </ul>
 *  @author Marty Ross
 */
public class NSort {

    //  implementation

    /**
     *  Sort an array with entries from a constrained (small) set of values
     *  <p />
     *  Complexity: CPU:~O(N), Memory:~O(1)
     *  @param <CT> constrained value type
     *  @param inArrayToSort array to sort in place
     */
    static <CT extends Comparable> void sort(List<CT> inArrayToSort) {

        // count number of values of each kind (~O(N))
        final SortedMap<CT, Integer> valueCounts = new TreeMap<>();
        for (final CT value : inArrayToSort) {
            final Integer count = valueCounts.get(value);
            valueCounts.put(value, count == null ? 1 : count + 1);
        }

        // suggested assertion:
        // assert valueCounts.size() <= MAX_CARDINALITY : "you're using the wrong sorting algorithm";

        // reconstruct list from counts (~O(N))
        inArrayToSort.clear();
        for (final Map.Entry<CT, Integer> valueCountsEntry : valueCounts.entrySet()) {
            for (int j = 0; j < valueCountsEntry.getValue(); j++) {
                inArrayToSort.add(valueCountsEntry.getKey());
            }
        }

    }

    // tests

    enum FlagColors { RED, WHITE, BLUE }

    public static void main(final String[] inArgs) {
        testSorting(List.of(0, -1, 1, 1, 1, -1, 0, -1, 0, 0, 1, 1, -1));
        testSorting(List.of(BLUE, RED, RED, BLUE, WHITE, BLUE, WHITE, RED, WHITE));
        testSorting(List.of(6, 2, 3, 5, 2, 1, 7, 4, 3, 88, 100001, -60));
    }

    private static boolean isSorted(final List<? extends Comparable> inList) {
        int i ;
        for (i = 0; i < inList.size() - 1; i++) {
             if (inList.get(i).compareTo(inList.get(i + 1)) > 0) break;
        }
        return i == inList.size() - 1;
    }

    private static <CT extends Comparable> void testSorting(final List<CT> inUnsorted) {

        assert !isSorted(inUnsorted) : "input is not unsorted";
        final List<CT> toSortList = new ArrayList<>(inUnsorted);
        NSort.sort(toSortList);
        System.out.format("%s <= %s\n", toSortList, inUnsorted);
        assert isSorted(toSortList) : "sorting failed";
    }

}
