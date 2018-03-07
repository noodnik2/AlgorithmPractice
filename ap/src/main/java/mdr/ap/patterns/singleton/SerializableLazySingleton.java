package mdr.ap.patterns.singleton;

import java.io.ObjectStreamException;
import java.io.Serializable;

import mdr.ap.patterns.singleton.util.LifecycleReporter;
import mdr.ap.util.NotNull;

/**
 *  Implementation of a singleton that is serializable. Note that we need to
 *  implement the "readResolved()" method of the "Serializable" interface to
 *  prevent a separate instance from being created upon object deserialization.
 *  @author mross
 */
public final class SerializableLazySingleton implements Runnable, Serializable {

    @NotNull
    public static SerializableLazySingleton getInstance() {
        return Helper.INSTANCE;
    }

    @Override
    public void run() {
        _lifecycleReporter.run();
    }

    /**
     * Hide constructor to "force" creation via public class "factory" method
     */
    private SerializableLazySingleton() {
        // construct thyself
    }

    private static final class Helper {
        private static final SerializableLazySingleton INSTANCE = new SerializableLazySingleton();
    }

    private Object readResolve() throws ObjectStreamException {
        // ignore request to deserialize from an underlying stream; just return the
        // singleton instance
        return getInstance();
    }

    private static final long serialVersionUID = -3697355169327523L;

    private LifecycleReporter _lifecycleReporter = new LifecycleReporter(SerializableLazySingleton.class);

}
