package mdr.ap.algos.sort;

import java.util.ArrayList;
import mdr.ap.util.NotNull;

import java.util.Comparator;

import static java.util.Comparator.naturalOrder;

public interface ISorter {

    <T> void sort(@NotNull ArrayList<T> inOutArrayToSort, @NotNull Comparator<T> tc);

    default <CT extends Comparable> void sort(@NotNull ArrayList<CT> inOutArrayToSort) {
        sort(inOutArrayToSort, naturalOrder());
    }

}
