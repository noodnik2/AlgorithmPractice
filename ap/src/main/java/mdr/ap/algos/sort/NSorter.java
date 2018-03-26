package mdr.ap.algos.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

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
@SuppressWarnings("Since15")
public class NSorter implements ISorter {

    //  implementation

    /**
     *  Sort an array with entries from a constrained (small) set of values
     *  <p />
     *  Complexity: CPU:~O(N), Memory:~O(1)
     *  @param <CT> constrained value type
     *  @param inOutArrayToSort array to sort in place
     *  @param comparator object comparator used to set ordering
     */
    public <CT> void sort(ArrayList<CT> inOutArrayToSort, Comparator<CT> comparator) {

        // count number of values of each kind (~O(N))
        final SortedMap<CT, Integer> valueCounts = new TreeMap<>(comparator);
        for (final CT value : inOutArrayToSort) {
            final Integer count = valueCounts.get(value);
            valueCounts.put(value, count == null ? 1 : count + 1);
        }

        // suggested assertion:
        // assert valueCounts.size() <= MAX_CARDINALITY : "you're using the wrong sorting algorithm";

        // reconstruct list from counts (~O(N))
        inOutArrayToSort.clear();
        for (final Map.Entry<CT, Integer> valueCountsEntry : valueCounts.entrySet()) {
            for (int j = 0; j < valueCountsEntry.getValue(); j++) {
                inOutArrayToSort.add(valueCountsEntry.getKey());
            }
        }

    }

}
