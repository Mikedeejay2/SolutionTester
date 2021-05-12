package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class In1020 implements SolutionTest
{



/*
 * Given 2 int values, return true if either of them is in the range 10..20 inclusive.
 *
 *
 * in1020(12, 99) → true
 * in1020(21, 12) → true
 * in1020(8, 99) → false
 */
@Solution
public boolean in1020(int a, int b) {
    return a >= 10 && a <= 20 || b >= 10 && b <= 20;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {12, 99},
            {21, 12},
            {8, 99},
            {99, 10},
            {20, 20},
            {21, 21},
            {9, 9},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            true,
            false,
            true,
            true,
            false,
            false,
        };
    }
}
