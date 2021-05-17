package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class PosNeg implements SolutionTest
{



/*
 * Given 2 int values, return true if one is negative and one is positive. Except if the parameter "negative" is true,
 * then return true only if both are negative.
 *
 *
 * posNeg(1, -1, false) → true
 * posNeg(-1, 1, false) → true
 * posNeg(-4, -5, true) → true
 */
@Solution
public boolean posNeg(int a, int b, boolean negative) {
  return negative ? a < 0 && b < 0 : a < 0 && b > 0 || a > 0 && b < 0;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {1, -1, false},
                {-1, 1, false},
                {-4, -5, true},
                {-4, -5, false},
                {-4, 5, false},
                {-4, 5, true},
                {1, 1, false},
                {-1, -1, false},
                {1, -1, true},
                {-1, 1, true},
                {1, 1, true},
                {-1, -1, true},
                {5, -5, false},
                {-6, 6, false},
                {-5, -6, false},
                {-2, -1, false},
                {1, 2, false},
                {-5, 6, true},
                {-5, -5, true},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                true,
                true,
                false,
                true,
                false,
                false,
                false,
                false,
                false,
                false,
                true,
                true,
                true,
                false,
                false,
                false,
                false,
                true,
        };
    }
}
