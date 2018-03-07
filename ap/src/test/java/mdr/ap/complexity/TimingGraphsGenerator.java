package mdr.ap.complexity;

import static mdr.ap.complexity.util.Plotter.plotDatasetToPngFile;
import static mdr.ap.complexity.util.TimingRunner.runSeries;

import java.io.File;
import java.util.Map;

import mdr.ap.util.NotNull;

/**
 *  Times algorithms run across a range of dataset sizes,
 *  and generates related performance graphs
 *  @author mross 
 */
public class TimingGraphsGenerator {

    /**
     * @param inArgs arguments (ignored)
     */
    public static void main(@NotNull final String[] inArgs) {
        
        final int[] sizes = createSizesOverRange(16, 100, 10000);
        
        plotDatasetToPngFile(
            new File("output/stringops_timings.png"), 
            sizes, 
            runSeries(createStringOpsRunners(sizes))
        );
        
    }
    
    @NotNull
    private static int[] createSizesOverRange(final int inNumSegments, final int inRangeMin, final int inRangeMax) {
        assert inRangeMin <= inRangeMax;
        final int[] sizes = new int[inNumSegments];
        final int rangeSize = inRangeMax - inRangeMin + 1;
        final int halfRangeSize = rangeSize >> 1;
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = inRangeMin + ((rangeSize * i) + halfRangeSize) / sizes.length; 
        }
        return sizes;
    }

    @NotNull
    private static Map<String, Runnable[]> createStringOpsRunners(@NotNull final int... inSizes) {
        final Runnable[] plusEqualRunners = new Runnable[inSizes.length];
        final Runnable[] stringBuilderRunners = new Runnable[inSizes.length];
        for (int i = 0; i < inSizes.length; i++) {
            final StringOps stringOpsForSize = new StringOps(inSizes[i]);
            plusEqualRunners[i] = () -> stringOpsForSize.appendViaPlusEquals();
            stringBuilderRunners[i] = () -> stringOpsForSize.appendViaStringBuilder();
        }
        final Map<String, Runnable[]> seriesRunners = Map.of(
            "plusEqual", plusEqualRunners,
            "stringBuilder", stringBuilderRunners
        );
        return seriesRunners;
    }

}
