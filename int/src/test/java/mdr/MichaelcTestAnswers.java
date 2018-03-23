package mdr;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *  Investigation and demonstration of various esoteric properties of Java...
 */
public class MichaelcTestAnswers {

    public static void allocatePopularMaps() {
        final Map insertionOrderedMap = new LinkedHashMap();
        final Map valueOrderedMap = new TreeMap();
        final Map unOrderedMap = new HashMap();
        final Map sychronizedMap = new Hashtable();
    }

    public static void doesReturnFromTryCallFinally() {
        try {
            log("in try");
            //noinspection UnnecessaryReturnStatement
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

    /**
     *  "Describe the behavior of this program"
     *  @return indication of which "catch" clause was invoked
     */
    private static String whichCatchInvoked() {

     try {
            String s = null;
         //noinspection ConstantConditions,UnusedAssignment
            s = s.substring(10);
        } catch (RuntimeException e) {
            return "RuntimeException";
        } catch (Exception e) {
            return "Exception";
        }
        //notreached
        return "none";
    }

    /**
     *  Describe the contents of ‘list’ and ‘copy’
     */
    private static void listAndCopy() {
        ArrayList<Point> list = new ArrayList<>();
        list.add(new Point(100,200));

        ArrayList<Point> copy = new ArrayList<>(list);
        copy.get(0).setLocation(150,250);

        System.out.println(list);
        System.out.println(copy);
    }

    /**
     *  "What is the output of this program?
     *  Comment on the difference between the two if statements."
     */
    private static void stringComparisons() {
        //noinspection RedundantStringConstructorCall
        String s1 = new String("Test");
        String s2 = "Test";
        //noinspection StringEquality
        if (s1 == s2) {
            System.out.println("Same");
        }
        if (s1.equals(s2)) {
            System.out.println("Equals");
        }
    }

    public static void main(String[] inArgs) {
//        System.out.println(whichCatchInvoked());
//        listAndCopy();
        stringComparisons();
    }

}
