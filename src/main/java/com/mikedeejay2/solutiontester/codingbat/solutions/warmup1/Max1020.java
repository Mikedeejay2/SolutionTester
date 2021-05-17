package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Max1020 implements SolutionTest
{



/*
 * Given 2 positive int values, return the larger value that is in the range 10..20 inclusive, or return 0 if neither
 * is in that range.
 *
 *
 * max1020(11, 19) → 19
 * max1020(19, 11) → 19
 * max1020(11, 9) → 11
 */
@Solution
public int max1020(int a, int b) {
  return a > 20 || a < 10 ? b > 20 || b < 10 ? 0 : b : b > 20 || b < 10 ? a : Math.max(a, b);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {11, 19},
            {19, 11},
            {11, 9},
            {9, 21},
            {10, 21},
            {21, 10},
            {9, 11},
            {23, 10},
            {20, 10},
            {7, 20},
            {17, 16},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            19,
            19,
            11,
            0,
            10,
            10,
            11,
            10,
            20,
            20,
            17,
        };
    }
}
