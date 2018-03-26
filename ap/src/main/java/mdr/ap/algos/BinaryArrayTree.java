package mdr.ap.algos;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import mdr.ap.util.NotNull;

/**
 *  @param <N> type of node being maintained in the tree
 *  <p />
 *  Caller Beware: no parameter validation is performed
 *  @author Marty Ross
 */
public class BinaryArrayTree<N> {


    //
    //  Private instance data
    //

    private final ArrayList<N> nodes;
    private final int n;


    //
    //  Private instance constructor
    //

    /**
     *  Constructs a heap using the specified {@code nodes} array,
     *  ordered by the specified {@code comparator}.
     *  @param nodes array list of nodes for the initial heap
     *  @param n number of array elements to be maintained in the tree
     */
    public BinaryArrayTree(@NotNull final ArrayList<N> nodes, final int n) {
        this.nodes = nodes;
        this.n = n;
    }

    /**
     *  Constructs and manages a copy of {@code nodes} within a heap structure
     *  @param nodes list of nodes for the initial heap
     */
    public BinaryArrayTree(@NotNull final List<N> nodes) {
        this(new ArrayList<>(nodes), nodes.size());
    }

    /**
     *  Constructs and manages an empty heap of nodes ordered by the
     *  specified {@code comparator}.
     */
    public BinaryArrayTree() {
        this(new ArrayList<>(), 0);
    }

    /**
     *  @return the number of elements being maintained in the array
     */
    public int getLength() {
        return n;
    }


    //
    //  Public instance methods
    //

    /**
     *  @param key key of a node within the heap
     *  @return the node corresponding to the given key
     */
    @NotNull
    public N getNode(final int key) {
        if (key < 1 || key > n) {
            throw new NoSuchElementException();
        }
        return nodes.get(key - 1);
    }

    /**
     *  @param key key of a parent node within the heap
     *  @return the "left child" of the given parent
     */
    @NotNull
    public N getLeftChild(final int key) {
        return nodes.get((key * 2) - 1);
    }

    /**
     *  @param key key of a parent node within the heap
     *  @return the "right child" of the given parent
     */
    @NotNull
    public N getRightChild(final int key) {
        return nodes.get(key * 2);
    }

    /**
     *  @return copy of the array backing the heap
     */
    @NotNull
    public ArrayList<N> getArrayCopy() {
        return new ArrayList<>(nodes);
    }


    //
    //  Protected instance methods
    //

    /**
     *  @param key key of a node within the heap
     *  @param node new node value to set for the given {@code key}
     */
    @NotNull
    protected void setNode(final int key, @NotNull final N node) {
        if (key < 1 || key > n) {
            throw new NoSuchElementException();
        }
        nodes.set(key - 1, node);
    }

}
