package mdr.ap.patterns.singleton;

import mdr.ap.patterns.singleton.util.LifecycleReporter;
import mdr.ap.util.NotNull;

/**
 *  Josh Bloch's (post Java 5) suggestion of using an "enum" type to efficiently
 *  implement a threadsafe "singleton" class. Java guarantees that "enum" object
 *  instances will be instantiated at most once per JVM class-loader instance. 
 *  I believe (though have not verified) that the creational behavior is lazy.
 *  @author mross
 */
public enum EnumSingleton implements Runnable {

    INSTANCE;

    @NotNull
    public static EnumSingleton getInstance() {
        return INSTANCE;
    }

    @Override
    public void run() {
        _lifecycleReporter.run();
    }

    private LifecycleReporter _lifecycleReporter = new LifecycleReporter(EnumSingleton.class);

}
