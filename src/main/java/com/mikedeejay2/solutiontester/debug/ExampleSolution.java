package com.mikedeejay2.solutiontester.debug;

import com.mikedeejay2.solutiontester.SolutionTest;
import com.mikedeejay2.solutiontester.annotations.Inputs;
import com.mikedeejay2.solutiontester.annotations.Results;
import com.mikedeejay2.solutiontester.annotations.Solution;

/**
 * A simple example test that adds two ints together.\
 *
 * @author Mikedeejay2
 * @since 1.0.0
 */
public class ExampleSolution implements SolutionTest {
    /*
     * Solution method that adds int a and int b together. Method will receive inputs from inputs method below and be
     * compared with the expected results from below as well.
     */
    @Solution
    public int addNums(int a, int b) {
        return a + b;
    }

    /*
     * The inputs method, returning 3 sets of arguments, each set consisting of two ints because the addNums method
     * requires two ints as arguments
     */
    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {2, 2},
            {1, 2},
            {5, 5}
        };
    }

    /*
     * The results method, returning 3 ints as the expected results. Since 3 input sets are specified, 3 results should
     * be specified as well.
     */
    @Results
    public Object[] results() {
        return new Object[] {
            4,
            3,
            10
        };
    }
}
