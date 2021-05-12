package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class FrontTimes implements SolutionTest
{



/*
 * Given a string and a non-negative int n, we'll say that the front of the string is the first 3 chars, or whatever is
 * there if the string is less than length 3. Return n copies of the front;
 *
 *
 * frontTimes("Chocolate", 2) → "ChoCho"
 * frontTimes("Chocolate", 3) → "ChoChoCho"
 * frontTimes("Abc", 3) → "AbcAbcAbc"
 */
@Solution
public String frontTimes(String str, int n) {
    return new String(new char[n]).replace("\0", str.substring(0, Math.min(3, str.length())));
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"Chocolate", 2},
            {"Chocolate", 3},
            {"Abc", 3},
            {"Ab", 4},
            {"A", 4},
            {"", 4},
            {"Abc", 0},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "ChoCho",
            "ChoChoCho",
            "AbcAbcAbc",
            "AbAbAbAb",
            "AAAA",
            "",
            "",
        };
    }
}
