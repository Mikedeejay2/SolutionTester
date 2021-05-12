package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class IcyHot implements SolutionTest
{



/*
 * Given two temperatures, return true if one is less than 0 and the other is greater than 100.
 *
 *
 * icyHot(120, -1) → true
 * icyHot(-1, 120) → true
 * icyHot(2, 120) → false
 */
@Solution
public boolean icyHot(int temp1, int temp2) {
    return temp1 < 0 == temp2 > 100;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {120, -1},
            {-1, 120},
            {2, 120},
            {-1, 100},
            {-2, -2},
            {120, 120},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            true,
            false,
            false,
            false,
            false,
        };
    }
}
