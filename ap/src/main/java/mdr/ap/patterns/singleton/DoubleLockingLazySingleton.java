package mdr.ap.patterns.singleton;

import mdr.ap.patterns.singleton.util.LifecycleReporter;
import mdr.ap.util.NotNull;

/**
 *  Classic "double checked locking" implementation of a singleton. NOTE: Only
 *  works with Java 5+, due to threading model changes in that release.
 *  @author mross
 */
public final class DoubleLockingLazySingleton implements Runnable {

    @NotNull
    public static DoubleLockingLazySingleton getInstance() {
        if (INSTANCE == null) {
            synchronized (DoubleLockingLazySingleton.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DoubleLockingLazySingleton();
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void run() {
        _lifecycleReporter.run();
    }

    /**
     * Hide constructor to "force" creation via public class "factory" method
     */
    private DoubleLockingLazySingleton() {
        // construct thyself
    }

    private LifecycleReporter _lifecycleReporter = new LifecycleReporter(EnumSingleton.class);

    // NOTE: the "volatile" keyword here is important - it prevents the
    // generation of code that could cause different threads from "seeing"
    // different states of this class field
    private static volatile DoubleLockingLazySingleton INSTANCE;

}
