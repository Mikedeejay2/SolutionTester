package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.example.ExampleSolution;
import com.mikedeejay2.solutiontester.internal.SolutionPrinter;
import com.mikedeejay2.solutiontester.internal.SolutionTestSolver;
import com.mikedeejay2.solutiontester.internal.SolutionTester;
import com.mikedeejay2.solutiontester.internal.data.TestResults;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * A solution test for use with the solution tester system. The default testing method is
 * {@link SolutionTest#testJUnit()}. JUnit Jupiter is the default testing environment used. Most IDEs have built in
 * JUnit compatibility. In Intellij IDEA for example, running a unit test on a solution is as simple as clicking a
 * green run array to the left of the class declaration.
 * <p>
 * For implementing a test, three annotations must be used on their respective methods:
 * <ul>
 *     <li>
 *         {@link Inputs} - The inputs annotation for specifying inputs to the solution. The inputs method must return
 *         an <code>Object[][]</code>, depth 1 being each individual array of parameters (aka 1 test), depth 2 being
 *         each individual argument that will be sent to the <code>Solution</code> annotation.
 *     </li>
 *     <li>
 *         {@link Results} - The results annotation for specifying the results to the solution. The results method must
 *         return an <code>Object[]</code>, each array index being the expected result of the index of the inputs.
 *     </li>
 *     <li>
 *         {@link Solution} - The solution annotation for specifying the methods that will solve the inputs to match
 *         the results. The amount of arguments of the solution should match the length of all of the depth 2 input
 *         arrays, since <code>Inputs[n/a][X]</code> is the input of argument #<code>X</code>.
 *     </li>
 * </ul>
 * Please view the Javadocs for the annotations on detailed implementation guides and uses.
 * <p>
 * Example code of a simple addition test:
 * <pre>
 *  public class ExampleSolution implements SolutionTest {
 *      // Solution method that adds int a and int b together. Method will
 *      // receive inputs from inputs method below and be compared
 *      // with the expected results from below as well.
 *      &#064;Solution
 *      public int addNums(int a, int b) {
 *          return a + b;
 *      }
 *
 *      // The inputs method, returning 3 sets of arguments, each set consisting
 *      // of two ints because the addNums method requires two ints as arguments
 *      &#064;Inputs
 *      public Object[][] inputs() {
 *          return new Object[][]{ {2, 2}, {1, 2}, {5, 5} };
 *      }
 *
 *      // The results method, returning 3 ints as the expected results. Since 3
 *      // input sets are specified, 3 results should be specified as well.
 *      &#064;Results
 *      public Object[] results() {
 *          return new Object[]{ 4, 3, 10 };
 *      }
 *  }
 * </pre>
 * Running this class would result in text being printed to the console:
 * <pre>
 *         Expected        Run
 * │ addNums([2, 2]) → 4  │ 4  │ OK │
 * │ addNums([1, 2]) → 3  │ 3  │ OK │
 * │ addNums([5, 5]) → 10 │ 10 │ OK │
 *
 * ✓ All Correct
 * </pre>
 * For this example as a class, see {@link ExampleSolution}
 * <p>
 * If needed, an ID system is available with all annotations wherein IDs can be specified to specific
 * annotations allowing for multiple separate tests to occur in the same class. This is useful for cases where multiple
 * tests need to occur in one class but don't coincide with each other.
 * <p>
 * The following methods can be overridden for customization in this interface:
 * <ul>
 *     <li>
 *         {@link SolutionTest#testJUnit()} - Method for testing with JUnit. Can be modified in the case of changing
 *         how tests occur.
 *     </li>
 *     <li>
 *         {@link SolutionTest#_toSolver()} - Method for getting a {@link SolutionTestSolver} for this class. Can be
 *         modified in the case of specifying a custom test solver.
 *     </li>
 *     <li>
 *         {@link SolutionTest#_toTester()} - Method for getting a {@link SolutionTester} for this class. Can be
 *         modified in the case of specifying a custom tester or changing what or how the {@link TestResults} for
 *         the test are analyzed and printed after processing. See the
 *         {@link SolutionTester#SolutionTester(SolutionPrinter)} constructor for specifying a custom
 *         {@link SolutionPrinter}.
 *     </li>
 * </ul>
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public interface SolutionTest
{
    /**
     * Run a JUnit unit test for this class.
     *
     * @throws ExecutionException   If the operation was cancelled
     * @throws InterruptedException If the thread was interrupted while waiting
     * @since 1.0.0
     */
    @Test
    default void testJUnit() throws ExecutionException, InterruptedException {
        CompletableFuture<TestResults> resultsFuture = _toTester().apply(this);
        TestResults results = resultsFuture.get();
        assertTrue(results.success, "Some tests failed. View above for test results.");
    }

    /**
     * Get a {@link SolutionTestSolver} for this object.
     *
     * @return The <code>SolutionTestSolver</code>
     * @since 1.0.0
     */
    default SolutionTestSolver _toSolver() {
        return new SolutionTestSolver(this);
    }

    /**
     * Get a {@link SolutionTester} for this object
     *
     * @return The <code>SolutionTester</code>
     * @since 1.0.0
     */
    default SolutionTester _toTester() {
        return new SolutionTester();
    }
}
