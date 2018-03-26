package mdr.ap.algos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import mdr.ap.util.NotNull;

/**
 *  MaxHeap of nodes
 *  @param <N> type of node being maintained in the heap
 *  <p />
 *  NOTE: does not handle {@code null} nodes or invalid keys
 *  @author Marty Ross
 */
public class MaxHeap<N> extends BinaryArrayTree<N> {


    //
    //  Private instance data
    //

    private final Comparator<N> comparator;


    //
    //  Private instance constructor
    //

    /**
     *  Constructs and manages an empty heap of nodes ordered by the
     *  specified {@code comparator}.
     *  @param comparator comparator of nodes managed by the heap
     */
    public MaxHeap(@NotNull final Comparator<N> comparator) {
        this(new ArrayList<>(), 0, comparator);
    }

    /**
     *  Constructs and manages a copy of {@code nodes} within a heap structure
     *  @param nodes list of nodes for the initial heap
     *  @param comparator comparator of nodes managed by the heap
     */
    public MaxHeap(
        @NotNull final List<N> nodes,
        @NotNull final Comparator<N> comparator
    ) {
        this(new ArrayList<>(nodes), nodes.size(), comparator);
    }

    /**
     *  Constructs a heap using the specified {@code nodes} array,
     *  ordered by the specified {@code comparator}.
     *  @param nodes array list of nodes for the initial heap
     *  @param comparator comparator of nodes managed by the heap
     */
    public MaxHeap(
        @NotNull final ArrayList<N> nodes,
        final int n,
        @NotNull final Comparator<N> comparator
    ) {
        super(nodes, n);
        this.comparator = comparator;
        heapify();
    }


    //
    //  Public instance methods
    //

    private void heapify() {
        for (int key = getLength() / 2; key >= 1; key--) {
            sink(key);
        }
    }

    private <T> void sink(final int startKey) {

        final int n = getLength();
        for (int key = startKey; 2 * key <= n;) {

            int childKey = 2 * key;
            if (childKey < n && !maxOrderingPreserved(childKey, childKey + 1)) {
                // switch to the right sibling who has greater value
                childKey++;
            }

            // leave once heap ordering is preserved
            if (maxOrderingPreserved(key, childKey)) {
                break;
            }

            // swap the child (having greater value) into the parent's position
            swapValues(key, childKey);

            // traverse down the path of (what was) the child)
            key = childKey;
        }
    }

    private void swapValues(final int key1, final int key2) {
        final N tmp = getNode(key1);
        setNode(key1, getNode(key2));
        setNode(key2, tmp);
    }

//    private <T> void swim(final int start) {
//        for (int k = start; k > 1  && maxOrderingPreserved(k / 2, k); k /= 2) {
//            swapValues(k, k / 2);
//        }
//    }

    private boolean maxOrderingPreserved(final int greaterKey, final int lesserKey) {
        return comparator.compare(getNode(greaterKey), getNode(lesserKey)) >= 0;
    }

}
