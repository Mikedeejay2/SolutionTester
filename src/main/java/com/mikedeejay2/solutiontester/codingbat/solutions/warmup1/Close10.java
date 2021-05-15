package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Close10 implements SolutionTest
{



/*
 * Given 2 int values, return whichever value is nearest to the value 10, or return 0 in the event of a tie. Note that
 * Math.abs(n) returns the absolute value of a number.
 *
 *
 * close10(8, 13) → 8
 * close10(13, 8) → 8
 * close10(13, 7) → 0
 */
@Solution
public int close10(int a, int b) {
    int a1 = Math.abs(a - 10), b1 = Math.abs(b - 10);
    return a1 == b1 ? 0 : a1 < b1 ? a : b;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {8, 13},
            {13, 8},
            {13, 7},
            {7, 13},
            {9, 13},
            {13, 8},
            {10, 12},
            {11, 10},
            {5, 21},
            {0, 20},
            {10, 10},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            8,
            8,
            0,
            0,
            9,
            8,
            10,
            10,
            5,
            0,
            0,
        };
    }
}
