package mdr.ap.patterns.singleton.util;

import java.io.Serializable;

import mdr.ap.util.NotNull;

/**
 *  An object who reports on lifecycle transitions.
 *  @author mross
 */
public class LifecycleReporter implements Runnable, Serializable {

    public LifecycleReporter(@NotNull final Class<?> inClass) {
        _forClass = inClass;
        log(this + " constructed");
    }

    @Override
    public void finalize() {
        log(this + " finalized");
    }

    @Override
    public void run() {
        log("running");
    }

    private void log(@NotNull final String inMessage) {
        System.out.println(inMessage + " for " + _forClass);
    }

    private static final long serialVersionUID = -36964825656152L;

    @NotNull
    private final Class<?> _forClass;

}
