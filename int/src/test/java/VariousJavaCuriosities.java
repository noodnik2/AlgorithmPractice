import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  Investigation and demonstration of various esoteric properties of Java...
 */
public class VariousJavaCuriosities {

    public static void allocatePopularMaps() {
        final Map insertionOrderedMap = new LinkedHashMap();
        final Map valueOrderedMap = new TreeMap();
        final Map unOrderedMap = new HashMap();
        final Map sychronizedMap = new Hashtable();
    }

    public static void doesReturnFromTryCallFinally() {
        try {
            log("in try");
            return; // NOTE: "finally" block *IS* called even if you "return" from within a "try" block
            // (as would be expected)
        } catch(final Exception e) {
            log("caught: " + e);
        } finally {
            log("in finally");
        }
    }

    // ex 13.7, pg. 168
    private interface Country {

        String getContinent();

        int getPopulation();

        default int getPopulation0(List<Country> inCountries, String inContinent) {
            int ptot = 0;
            for (final Country c : inCountries) {
                if (c.getContinent().equals(inContinent)) {
                    ptot += c.getPopulation();
                }
            }
            return ptot;
        }

        default int getPopulation(List<Country> inCountries, String inContinent) {
            return (
                inCountries.stream()
                .filter(c -> c.getContinent().equals(inContinent))
                .map(Country::getPopulation)
                .reduce(0, Integer::sum)
            );
        }

    }

    private static void log(final String inMessage) {
        System.out.println(inMessage);
    }
}
