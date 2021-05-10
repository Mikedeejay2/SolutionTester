package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class StringTimes implements CodingBatSolution {



/*
 * Given a string and a non-negative int n, return a larger string that is n copies of the original string.
 *
 *
 * stringTimes("Hi", 2) → "HiHi"
 * stringTimes("Hi", 3) → "HiHiHi"
 * stringTimes("Hi", 1) → "Hi"
 */
@Solution
public String stringTimes(String str, int n) {
    return new String(new char[n]).replace("\0", str);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"Hi", 2},
            {"Hi", 3},
            {"Hi", 1},
            {"Hi", 0},
            {"Hi", 5},
            {"Oh Boy!", 2},
            {"x", 4},
            {"", 4},
            {"code", 2},
            {"code", 3},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "HiHi",
            "HiHiHi",
            "Hi",
            "",
            "HiHiHiHiHi",
            "Oh Boy!Oh Boy!",
            "xxxx",
            "",
            "codecode",
            "codecodecode",
        };
    }
}
