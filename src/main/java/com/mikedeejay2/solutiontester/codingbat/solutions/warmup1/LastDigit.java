package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class LastDigit implements SolutionTest
{



/*
 * Given two non-negative int values, return true if they have the same last digit, such as with 27 and 57. Note that
 * the % "mod" operator computes remainders, so 17 % 10 is 7.
 *
 *
 * lastDigit(7, 17) → true
 * lastDigit(6, 17) → false
 * lastDigit(3, 113) → true
 */
@Solution
public boolean lastDigit(int a, int b) {
  return a % 10 == b % 10;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {7, 17},
            {6, 17},
            {3, 113},
            {114, 113},
            {114, 4},
            {10, 0},
            {11, 0},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            false,
            true,
            false,
            true,
            true,
            false,
        };
    }
}
