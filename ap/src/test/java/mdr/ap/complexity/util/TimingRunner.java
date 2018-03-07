package mdr.ap.complexity.util;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import mdr.ap.util.NotNull;

/**
 *  Utility to help time the execution of {@link Runnable}s 
 *  @author mross
 */
public class TimingRunner {
    
    /**
     *  @param inRunnables {@link Runnable}s to execute
     *  @return array containing execution time(s) of each (corresponding) {@link Runnable} supplied
     */
    @NotNull
    public static long[] getRunnableExecutionTimesMsec(@NotNull final Runnable... inRunnables) {
        final long[] executionTimes = new long[inRunnables.length];
        for (int i = 0; i < executionTimes.length; i++) {
            final long startTime = System.currentTimeMillis();
            inRunnables[i].run();
            executionTimes[i] = System.currentTimeMillis() - startTime;
        }
        return executionTimes;
    }

    /**
     *  @param inSeriesRunners map of "series name" to an array of {@link Runnable}s to be executed for that series
     *  @return map of "series name" to execution time(s) for each (corresponding) {@link Runnable} specified for that series
     */
    @NotNull
    public static Map<String, long[]> runSeries(@NotNull final Map<String, Runnable[]> inSeriesRunners) {        
        
        final Map<String, long[]> timings = (
            inSeriesRunners
            .entrySet()
            .stream()
            .collect(
                Collectors.toMap(Map.Entry::getKey, e -> getRunnableExecutionTimesMsec(e.getValue()))
            )
        );
        
        timings
        .entrySet()
        .stream()
        .forEach(
            e -> System.out.println(String.format("timing(%s -> %s)", e.getKey(), Arrays.toString(e.getValue()))) 
        );
        
        return timings;
    }   

}
