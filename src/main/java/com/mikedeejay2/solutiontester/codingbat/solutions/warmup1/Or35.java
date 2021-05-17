package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Or35 implements SolutionTest
{



/*
 * Return true if the given non-negative number is a multiple of 3 or a multiple of 5. Use the % "mod" operator -- see
 * Introduction to Mod
 *
 *
 * or35(3) → true
 * or35(10) → true
 * or35(8) → false
 */
@Solution
public boolean or35(int n) {
    return n >= 0 && (n % 3 == 0 || n % 5 == 0);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {3},
            {10},
            {8},
            {15},
            {5},
            {9},
            {4},
            {7},
            {6},
            {17},
            {18},
            {29},
            {20},
            {21},
            {22},
            {45},
            {99},
            {100},
            {101},
            {121},
            {122},
            {123},
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
            true,
            false,
            false,
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
            false,
            false,
            false,
            true,
        };
    }
}
