package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class HasTeen implements SolutionTest
{



/*
 * We'll say that a number is "teen" if it is in the range 13..19 inclusive. Given 3 int values, return true if 1 or
 * more of them are teen.
 *
 *
 * hasTeen(13, 20, 10) → true
 * hasTeen(20, 19, 10) → true
 * hasTeen(20, 10, 13) → true
 */
@Solution
public boolean hasTeen(int a, int b, int c) {
  return a >= 13 && a <= 19 || b >= 13 && b <= 19 || c >= 13 && c <= 19;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {13, 20, 10},
            {20, 19, 10},
            {20, 10, 13},
            {1, 20, 12},
            {19, 20, 12},
            {12, 20, 19},
            {12, 9, 20},
            {12, 18, 20},
            {14, 2, 20},
            {4, 2, 20},
            {11, 22, 22},
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
            true,
            false,
            true,
            true,
            false,
            false,
        };
    }
}
