package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class FrontBack implements SolutionTest
{



/*
 * Given a string, return a new string where the first and last chars have been exchanged.
 *
 *
 * frontBack("code") → "eodc"
 * frontBack("a") → "a"
 * frontBack("ab") → "ba"
 */
@Solution
public String frontBack(String str) {
    return str.length() < 2 ? str : str.charAt(str.length() - 1) + str.substring(1, str.length() - 1) + str.charAt(0);
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "code",
            "a",
            "ab",
            "abc",
            "",
            "Chocolate",
            "aavJ",
            "hello",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "eodc",
            "a",
            "ba",
            "cba",
            "",
            "ehocolatC",
            "Java",
            "oellh",
        };
    }
}
