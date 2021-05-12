package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class NearHundred implements SolutionTest
{



/*
 * Given an int n, return true if it is within 10 of 100 or 200. Note: Math.abs(num) computes the absolute value of a
 * number.
 *
 *
 * nearHundred(93) → true
 * nearHundred(90) → true
 * nearHundred(89) → false
 */
@Solution
public boolean nearHundred(int n) {
    return n >= 90 && n <= 110 || n >= 190 && n <= 210;
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
                93,
                90,
                89,
                110,
                111,
                121,
                -101,
                -209,
                190,
                209,
                0,
                5,
                -50,
                191,
                189,
                200,
                210,
                211,
                290,
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                true,
                false,
                true,
                false,
                false,
                false,
                false,
                true,
                true,
                false,
                false,
                false,
                true,
                false,
                true,
                true,
                false,
                false,
        };
    }
}
