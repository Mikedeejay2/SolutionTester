package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class IntMax implements SolutionTest
{



/*
 * Given three int values, a b c, return the largest.
 *
 *
 * intMax(1, 2, 3) → 3
 * intMax(1, 3, 2) → 3
 * intMax(3, 2, 1) → 3
 */
@Solution
public int intMax(int a, int b, int c) {
    return Math.max(Math.max(a, b), c);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {1, 2, 3},
            {1, 3, 2},
            {3, 2, 1},
            {9, 3, 3},
            {3, 9, 3},
            {3, 3, 9},
            {8, 2, 3},
            {-3, -1, -2},
            {6, 2, 5},
            {5, 6, 2},
            {5, 2, 6},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            3,
            3,
            3,
            9,
            9,
            9,
            8,
            -1,
            6,
            6,
            6,
        };
    }
}
