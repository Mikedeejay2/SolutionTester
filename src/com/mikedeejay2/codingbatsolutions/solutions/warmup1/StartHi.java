package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class StartHi implements CodingBatSolution {



/*
 * Given a string, return true if the string starts with "hi" and false otherwise.
 *
 *
 * startHi("hi there") → true
 * startHi("hi") → true
 * startHi("hello hi") → false
 */
@Solution
public boolean startHi(String str) {
    return str.startsWith("hi");
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "hi there",
            "hi",
            "hello hi",
            "he",
            "h",
            "",
            "ho hi",
            "hi ho",
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
            false,
            true,
        };
    }
}
