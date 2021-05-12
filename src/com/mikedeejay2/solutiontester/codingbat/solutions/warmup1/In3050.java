package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class In3050 implements SolutionTest
{



/*
 * Given 2 int values, return true if they are both in the range 30..40 inclusive, or they are both in the range 40..50
 * inclusive.
 *
 *
 * in3050(30, 31) → true
 * in3050(30, 41) → false
 * in3050(40, 50) → true
 */
@Solution
public boolean in3050(int a, int b) {
    return a >= 30 && a <= 40 && b >= 30 && b <= 40 || a >= 40 && a <= 50 && b >= 40 && b <= 50;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {30, 31},
            {30, 41},
            {40, 50},
            {40, 51},
            {39, 50},
            {50, 39},
            {40, 39},
            {49, 48},
            {50, 40},
            {50, 51},
            {35, 36},
            {35, 45},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            false,
            true,
            false,
            false,
            false,
            true,
            true,
            true,
            false,
            true,
            false,
        };
    }
}
