package mdr.ap.patterns.singletonfactory;

import mdr.ap.util.NotNull;

/**
 *  Common interface for all singleton factories in this package
 *  @author mross
 *  @param <T> type of singleton to be managed
 */
public interface SingletonFactory<T> {

    /**
     * @return the singleton instance
     */
    @NotNull
    T getInstance();

}
