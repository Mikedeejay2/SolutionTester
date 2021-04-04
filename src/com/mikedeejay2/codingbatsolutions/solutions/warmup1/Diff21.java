package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class Diff21 implements CodingBatSolution {



    /*
     * Given an int n, return the absolute difference between n and 21, except return double the absolute difference
     * if n is over 21.
     *
     *
     * diff21(19) → 2
     * diff21(10) → 11
     * diff21(21) → 0
     */
    @Solution
    public int diff21(int n) {
        return Math.abs(n > 21 ? (21 - n) * 2 : 21 - n);
    }





    @Inputs
    public Object[] inputs() {
        return new Object[] {
                19,
                10,
                21,
                22,
                25,
                30,
                0,
                1,
                2,
                -1,
                -2,
                50,
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                2,
                11,
                0,
                2,
                8,
                18,
                21,
                20,
                19,
                22,
                23,
                58,
        };
    }
}
