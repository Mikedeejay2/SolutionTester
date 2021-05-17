package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class SumDouble implements SolutionTest
{



/*
 * Given two int values, return their sum. Unless the two values are the same, then return double their sum.
 *
 *
 * sumDouble(1, 2) → 3
 * sumDouble(3, 2) → 5
 * sumDouble(2, 2) → 8
 */
@Solution
public int sumDouble(int a, int b) {
  return a == b ? (a + b) * 2 : a + b;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {1, 2},
                {3, 2},
                {2, 2},
                {-1, 0},
                {3, 3},
                {0, 0},
                {0, 1},
                {3, 4},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                3,
                5,
                8,
                -1,
                12,
                0,
                1,
                7,
        };
    }
}
