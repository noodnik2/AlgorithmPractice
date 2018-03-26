package mdr.ap.algos.sort;

import java.util.ArrayList;
import java.util.Comparator;
import mdr.ap.algos.MaxHeap;
import mdr.ap.util.NotNull;

/**
 *  Simple implementation of a MaxHeap Sort
 */
public class HeapSorter implements ISorter {

    @Override
    public <T> void sort(
        @NotNull final ArrayList<T> inOutArrayToSort,
        @NotNull final Comparator<T> elementComparator
    ) {
        for (int n = inOutArrayToSort.size(); n > 1; n--) {
            new MaxHeap<>(inOutArrayToSort, n, elementComparator);
            final T tmp = inOutArrayToSort.get(0);
            inOutArrayToSort.set(0, inOutArrayToSort.get(n - 1));
            inOutArrayToSort.set(n - 1, tmp);
        }
    }

}
