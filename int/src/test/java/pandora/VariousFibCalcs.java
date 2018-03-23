package pandora;

import java.io.PrintStream;
import java.util.*;
import java.util.function.Supplier;

/**
 *  Pandora initial HR technical screen: performance of Fibonacci calculations
 *  Complexity comparison of several algorithms to calculate Fibonacci numbers
 *  @author Marty Ross
 */
@SuppressWarnings("WeakerAccess")
public class VariousFibCalcs {

    public static void main(String[] args) {
        new FibCalcComparer().run();
    }

    private static class FibCalcComparer {


        /**
         *  Run the comparison tests
         */
        public void run() {


            final Iterable<Supplier<FibCalculator>> fibCalcs = Arrays.asList(
                FibCalcFastDoubling::new,
                FibCalcNaiiveIterative::new,
                FibCalcNaiiveRecursive::new
            );

            final long seqMin = -10;
            final long seqMax = 40;
            System.out.format("computing fibonacci sequences from %s to %s...\n", seqMin, seqMax);

            for (long i = seqMin; i <= seqMax; i++) {

                for (final Supplier<FibCalculator> calcFactory : fibCalcs) {
                    final FibCalculator calculator = calcFactory.get();
                    calculator.calculate(i);
                    deliverResult(i, calculator);
                }

            }

            report(System.out);

        }

        /**
         *  Deliver results from calculation
         *  @param inSeqNo sequence number that was calculated
         *  @param inCalculator calculator in final state, with results already computed
         */
        private void deliverResult(final long inSeqNo, FibCalculator inCalculator) {
            _results.computeIfAbsent(inSeqNo, k -> new LinkedList<>()).add(inCalculator);
        }

        /**
         *  Report on the set of results obtained
         *  @param inOutStream stream to which to write results
         */
        private void report(final PrintStream inOutStream) {

            //noinspection LoopStatementThatDoesntLoop
            for (final Map.Entry<Long, List<FibCalculator>> fce : _results.entrySet()) {
                inOutStream.format("#");
                for (final FibCalculator fc : fce.getValue()) {
                    inOutStream.format("\t(%s)", fc.getClass().getSimpleName());
                }
                inOutStream.println();
                // only print this title row once
                break;
            }

            long disparityCount = 0L;
            for (final Map.Entry<Long, List<FibCalculator>> fce : _results.entrySet()) {
                inOutStream.format("%s", fce.getKey());
                Long answer = null;
                for (final FibCalculator fc : fce.getValue()) {
                    final long currentAnswer = fc.getAnswer();
                    String disparityFlag = "";
                    if (answer == null) {
                        answer = currentAnswer;
                    } else {
                        if (answer != currentAnswer) {
                            disparityCount++;
                            disparityFlag = "!";
                        }
                    }
                    inOutStream.format("\t(%s%s/%s)", disparityFlag, currentAnswer, fc.getOpCount());
                }
                inOutStream.println();
            }

            inOutStream.format("disparityCount = %s\n", disparityCount);
        }

        private Map<Long, List<FibCalculator>> _results = new TreeMap<>();

    }

    /**
     *  Common calculator logic
     */
    private static abstract class FibCalculator {

        /**
         *  @param inSeqNo fibonacci sequence number (which may be negative)
         *  @see <a href="http://research.allacademic.com/meta/p_mla_apa_research_citation/2/0/6/8/4/p206842_index.html">
         *  Negafibonacci Numbers and the Hyperbolic Plane</a>
         */
        public void calculate(final long inSeqNo) {
            _opCount = 0;
            if (inSeqNo >= 0) {
                _answer = posFib(inSeqNo);
                return;
            }
            final long intermediateAnswer = posFib(-inSeqNo);
            _answer = (inSeqNo % 2L) == 0L ? -intermediateAnswer : intermediateAnswer;
        }

        /**
         *  @return calculated result; number corresponding to the "inSeqNo"th position in the fibonacci sequence
         */
        public long getAnswer() {
            return _answer;
        }

        /**
         *  @return number of operations recorded
         */
        public long getOpCount() {
            return _opCount;
        }

        /**
         *  @param inPosSeqNo fibonacci sequence number (which must be non-negative)
         *  @return number corresponding to the "inN"th position in the fibonacci sequence
         */
        protected abstract long posFib(final long inPosSeqNo);

        /**
         *  Records an operation
         */
        protected void recordOp() {
            _opCount++;
        }

        private long _answer;
        private long _opCount;

    }

    /**
     *  Naiive recursive approach, exhibiting O(2^N) complexity
     */
    private static class FibCalcNaiiveRecursive extends FibCalculator {

        protected long posFib(final long inPosSeqNo) {

            assert inPosSeqNo >= 0L;

            recordOp();
            if (inPosSeqNo < 2L) {
                return inPosSeqNo;
            }

            return posFib(inPosSeqNo - 1L) + posFib(inPosSeqNo - 2L);
        }

    }

    /**
     *  Naiive iterative approach
     */
    private static class FibCalcNaiiveIterative extends FibCalculator {

        protected long posFib(final long inPosSeqNo) {

            assert inPosSeqNo >= 0L;

            if (inPosSeqNo < 2L) {
                recordOp();
                return inPosSeqNo;
            }

            long ans;
            long a = 0L;
            long b = 1L;
            long i = 1L;

            do {
                recordOp();
                ans = (a + b) % MOD;
                a = b;
                b = ans;
            } while(++i < inPosSeqNo);

            return ans;
        }


    }


    /**
     *  Implement "fast doubling" method, which takes advantage of formulae:
     *  <pre>
     *  F(2n) = F(n)[2*F(n+1) – F(n)]
     *  F(2n + 1) = F(n)^2 + F(n+1)^2
     *  </pre>
     */
    private static class FibCalcFastDoubling extends FibCalculator {


        protected long posFib(final long inPosSeqNo) {

            assert inPosSeqNo >= 0L;

            final long[] ans = new long[2];

            calcDoubleFibs(inPosSeqNo, ans);

            return ans[0];
        }

        /**
         *  @param inPosSeqNo fibonacci sequence number (which must be non-negative)
         *  @param inOutFnFnP1Vec two-element calculation state array; the first of which will contain F(n)
         */
        private void calcDoubleFibs(final long inPosSeqNo, final long[] inOutFnFnP1Vec) {

            assert inPosSeqNo >= 0L;

            recordOp();

            if (inPosSeqNo == 0) {
                inOutFnFnP1Vec[0] = 0;
                inOutFnFnP1Vec[1] = 1;
                return;
            }

            calcDoubleFibs(inPosSeqNo / 2, inOutFnFnP1Vec);

            long a = inOutFnFnP1Vec[0];             // F(n)
            long b = inOutFnFnP1Vec[1];             // F(n+1)
            long c = 2 * b - a;

            if (c < 0) {
                c += MOD;
            }

            c = (a * c) % MOD;      // F(2n)
            long d = (a * a + b * b) % MOD;  // F(2n + 1)

            if (inPosSeqNo % 2 == 0) {
                inOutFnFnP1Vec[0] = c;
                inOutFnFnP1Vec[1] = d;
            } else {
                inOutFnFnP1Vec[0] = d;
                inOutFnFnP1Vec[1] = c + d;
            }

        }

    }

    private static final long MOD = 1000000007L;    // first 10-digital prime number

}
