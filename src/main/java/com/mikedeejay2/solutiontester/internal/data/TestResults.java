package com.mikedeejay2.solutiontester.internal.data;

import com.mikedeejay2.solutiontester.internal.SolutionPrinter;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Container class for holding all test results. For a single test result, see the internal container class
 * {@link TestResult}. For printing this class, see {@link SolutionPrinter}.
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public class TestResults {
    /**
     * Whether all test results are successful
     *
     * @since 1.0.0
     */
    public final boolean success;

    /**
     * The total amount of tests
     *
     * @since 1.0.0
     */
    public final int total;

    /**
     * The amount of tests that passed
     *
     * @since 1.0.0
     */
    public final int passed;

    /**
     * The amount of tests that failed
     *
     * @since 1.0.0
     */
    public final int failed;

    /**
     * The list of IDs tested
     *
     * @since 1.0.0
     */
    public final List<String> ids;

    /**
     * The <code>Map</code> of IDs to {@link TestResult}
     *
     * @since 1.0.0
     */
    public final Map<String, TestResult> results;

    /**
     * Constructs a new <code>TestResults</code>
     *
     * @param success Whether all test results are successful
     * @param total   The total amount of tests
     * @param passed  The amount of tests that passed
     * @param failed  The amount of tests that failed
     * @param ids     The list of IDs tested
     * @param results The <code>Map</code> of IDs to {@link TestResult}
     * @since 1.0.0
     */
    public TestResults(
        boolean success,
        int total,
        int passed,
        int failed,
        @NotNull List<String> ids,
        @NotNull Map<String, TestResult> results) {
        Objects.requireNonNull(ids, "List<String> ids cannot be null");
        Objects.requireNonNull(results, "Map<String, TestResult> results");
        this.success = success;
        this.total = total;
        this.passed = passed;
        this.failed = failed;
        this.ids = Collections.unmodifiableList(ids);
        this.results = Collections.unmodifiableMap(results);
    }

    /**
     * Get whether all test results are successful
     *
     * @return Whether all test results are successful
     * @since 1.0.0
     */
    public boolean isSuccess() {
        return success;
    }

    /**
     * Get the total amount of tests
     *
     * @return The total amount of tests
     * @since 1.0.0
     */
    public int getTotal() {
        return total;
    }

    /**
     * Get the amount of tests that passed
     *
     * @return The amount of tests that passed
     * @since 1.0.0
     */
    public int getPassed() {
        return passed;
    }

    /**
     * Get the amount of tests that failed
     *
     * @return The amount of tests that failed
     * @since 1.0.0
     */
    public int getFailed() {
        return failed;
    }

    /**
     * Get the list of IDs tested
     *
     * @return The list of IDs tested
     * @since 1.0.0
     */
    public List<String> getIds() {
        return ids;
    }

    /**
     * Get the <code>Map</code> of IDs to {@link TestResult}
     *
     * @return The Map of IDs to <code>TestResults</code>
     * @since 1.0.0
     */
    public Map<String, TestResult> getResults() {
        return results;
    }

    /**
     * Gets a String from a new {@link SolutionPrinter}
     *
     * @return a string representation of the object.
     * @since 1.0.0
     */
    @Override
    public String toString() {
        return new SolutionPrinter(null).toFullMessage(this);
    }

    /**
     * Container class for a single test result.
     *
     * @author Mikedeejay2
     * @since 1.0.0
     */
    public static class TestResult {
        /**
         * Whether the entire test was successful
         *
         * @since 1.0.0
         */
        public final boolean success;

        /**
         * The total amount of tests
         *
         * @since 1.0.0
         */
        public final int total;

        /**
         * The amount of tests that passed
         *
         * @since 1.0.0
         */
        public final int passed;

        /**
         * The amount of tests that failed
         *
         * @since 1.0.0
         */
        public final int failed;

        /**
         * The ID of the test
         *
         * @since 1.0.0
         */
        public final String id;

        /**
         * The list of input object 2D arrays
         *
         * @since 1.0.0
         */
        public final List<Object[][]> inputs;

        /**
         * The expected results array
         *
         * @since 1.0.0
         */
        public final Object[] results;

        /**
         * The list of solution objects
         *
         * @since 1.0.0
         */
        public final List<Object> solutions;

        /**
         * The list of method names tested
         *
         * @since 1.0.0
         */
        public final List<String> methodNames;

        /**
         * List of passing or not passing result booleans
         *
         * @since 1.0.0
         */
        public final List<Boolean> hasPassed;

        /**
         * Execution time taken in nanoseconds for the test to complete
         *
         * @since 1.0.0
         */
        public final long timeNanos;

        /**
         * Execution time taken in milliseconds for the test to complete
         *
         * @since 1.0.0
         */
        public final double timeMS;

        /**
         * Construct a new <code>TestResult</code>
         *
         * @param success     Whether the entire test was successful
         * @param total       The total amount of tests
         * @param passed      The amount of tests that passed
         * @param failed      The amount of tests that failed
         * @param id          The ID of the test
         * @param inputs      The list of input object 2D arrays
         * @param results     The expected results array
         * @param solutions   The list of solution objects
         * @param methodNames The list of method names tested
         * @param hasPassed   List of passing or not passing result booleans
         * @param timeNanos   Execution time taken in nanoseconds for the test to complete
         * @param timeMS      Execution time taken in milliseconds for the test to complete
         * @since 1.0.0
         */
        public TestResult(
            boolean success,
            int total,
            int passed,
            int failed,
            String id,
            List<Object[][]> inputs,
            Object[] results,
            List<Object> solutions,
            List<String> methodNames,
            List<Boolean> hasPassed,
            long timeNanos,
            double timeMS) {
            this.success = success;
            this.total = total;
            this.passed = passed;
            this.failed = failed;
            this.id = id;
            this.inputs = Collections.unmodifiableList(inputs);
            this.results = results;
            this.solutions = Collections.unmodifiableList(solutions);
            this.methodNames = Collections.unmodifiableList(methodNames);
            this.hasPassed = Collections.unmodifiableList(hasPassed);
            this.timeNanos = timeNanos;
            this.timeMS = timeMS;
        }

        /**
         * Get whether the entire test was successful
         *
         * @return Whether the entire test was successful
         * @since 1.0.0
         */
        public boolean isSuccess() {
            return success;
        }

        /**
         * Get the total amount of tests
         *
         * @return The total amount of tests
         * @since 1.0.0
         */
        public int getTotal() {
            return total;
        }

        /**
         * Get the amount of tests that passed
         *
         * @return The amount of tests that passed
         * @since 1.0.0
         */
        public int getPassed() {
            return passed;
        }

        /**
         * Get the amount of tests that failed
         *
         * @return The amount of tests that failed
         * @since 1.0.0
         */
        public int getFailed() {
            return failed;
        }

        /**
         * Get the ID of the test
         *
         * @return The ID of the test
         * @since 1.0.0
         */
        public String getId() {
            return id;
        }

        /**
         * Get the list of input object 2D arrays
         *
         * @return The list of input object 2D arrays
         * @since 1.0.0
         */
        public List<Object[][]> getInputs() {
            return inputs;
        }

        /**
         * Get the expected results array
         *
         * @return The expected results array
         * @since 1.0.0
         */
        public Object[] getResults() {
            return results;
        }

        /**
         * Get the list of solution objects
         *
         * @return The list of solution objects
         * @since 1.0.0
         */
        public List<Object> getSolutions() {
            return solutions;
        }

        /**
         * Get the list of method names tested
         *
         * @return The list of method names tested
         * @since 1.0.0
         */
        public List<String> getMethodNames() {
            return methodNames;
        }

        /**
         * Get a List of passing or not passing result booleans
         *
         * @return List of passing or not passing result booleans
         * @since 1.0.0
         */
        public List<Boolean> getHasPassed() {
            return hasPassed;
        }

        /**
         * Get execution time taken in nanoseconds for the test to complete
         *
         * @return Execution time taken in nanoseconds for the test to complete
         * @since 1.0.0
         */
        public long getTimeNanos() {
            return timeNanos;
        }

        /**
         * Get execution time taken in milliseconds for the test to complete
         *
         * @return Execution time taken in milliseconds for the test to complete
         * @since 1.0.0
         */
        public double getTimeMS() {
            return timeMS;
        }

        /**
         * Auto-generated <code>toString()</code> method for debugging
         *
         * @return a string representation of the object.
         * @since 1.0.0
         */
        @Override
        public String toString() {
            return "TestResult{" +
                "success=" + success +
                ", total=" + total +
                ", passed=" + passed +
                ", failed=" + failed +
                ", id='" + id + '\'' +
                ", inputs=" + inputs +
                ", results=" + Arrays.toString(results) +
                ", solutions=" + solutions +
                ", methodNames=" + methodNames +
                ", hasPassed=" + hasPassed +
                ", timeNanos=" + timeNanos +
                ", timeMS=" + timeMS +
                '}';
        }
    }
}
