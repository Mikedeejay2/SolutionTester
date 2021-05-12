package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class DoubleX implements SolutionTest
{



/*
 * Given a string, return true if the first instance of "x" in the string is immediately followed by another "x".
 *
 *
 * doubleX("axxbb") → true
 * doubleX("axaxax") → false
 * doubleX("xxxxx") → true
 */
@Solution
boolean doubleX(String str) {
    return str.contains("x") && str.indexOf("x") != str.length() - 1 && str.charAt(str.indexOf("x") + 1) == 'x';
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "axxbb",
            "axaxax",
            "xxxxx",
            "xaxxx",
            "aaaax",
            "",
            "abc",
            "x",
            "xx",
            "xax",
            "xaxx",
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
            false,
            false,
            true,
            false,
            false,
        };
    }
}
