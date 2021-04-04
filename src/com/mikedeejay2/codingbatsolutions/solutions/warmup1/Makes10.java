package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class Makes10 implements CodingBatSolution {



    /*
     * Given 2 ints, a and b, return true if one if them is 10 or if their sum is 10.
     *
     *
     * makes10(9, 10) → true
     * makes10(9, 9) → false
     * makes10(1, 9) → true
     */
    @Solution
    public boolean makes10(int a, int b) {
        return a + b == 10 || a == 10 || b == 10;
    }





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {9, 10},
                {9, 9},
                {1, 9},
                {10, 1},
                {10, 10},
                {8, 2},
                {8, 3},
                {10, 42},
                {12, -2},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                false,
                true,
                true,
                true,
                true,
                false,
                true,
                true,
        };
    }
}
