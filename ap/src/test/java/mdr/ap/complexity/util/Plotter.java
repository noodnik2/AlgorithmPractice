package mdr.ap.complexity.util;

import static mdr.ap.util.TestLogger.log;
import static org.jfree.chart.plot.PlotOrientation.VERTICAL;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import mdr.ap.util.NotNull;

/**
 *  Utility for plotting results
 *  @author mross
 */
public class Plotter {
    
    /**
     *  @param inOutPngFile identifies the output file to write to
     *  @param inSizes array of sizes (to be displayed along the horizontal axis)
     *  @param inTimings array of timings (to be displayed along the vertical axis)
     */
    public static void plotDatasetToPngFile(
        @NotNull final File inOutPngFile, 
        @NotNull final int[] inSizes, 
        @NotNull final Map<String, long[]> inTimings
    ) {

        final XYDataset seriesDataset = createSeriesDataset(inSizes, inTimings);
        
        // Create chart
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Complexity", 
            "N", 
            "Time", 
            seriesDataset, 
            VERTICAL, 
            true, 
            true, 
            false
        );

        try {
            ChartUtilities.saveChartAsPNG(inOutPngFile, chart, 980, 550);
        } catch (final IOException ex) {
            throw new RuntimeException("error writing to file", ex);
        }

        log(String.format("wrote(%s)", inOutPngFile));
    }

    @NotNull
    private static XYDataset createSeriesDataset(
        @NotNull final int[] inSizes, 
        @NotNull final Map<String, long[]> inTimings
    ) {

        final XYSeriesCollection seriesDataset = new XYSeriesCollection();

        inTimings.entrySet().forEach(
            e -> {
                final XYSeries series = new XYSeries(e.getKey());
                final long[] values = e.getValue();
                assert inSizes.length == values.length;
                for (int i = 0; i < inSizes.length; i++) {
                    series.add(inSizes[i], values[i]);
                }
                seriesDataset.addSeries(series);
            }
        );

        return seriesDataset;
    }
    
}
