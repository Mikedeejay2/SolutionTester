package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class MissingChar implements SolutionTest
{



/*
 * Given a non-empty string and an int n, return a new string where the char at index n has been removed. The value of
 * n will be a valid index of a char in the original string (i.e. n will be in the range 0..str.length()-1 inclusive).
 *
 *
 * missingChar("kitten", 1) → "ktten"
 * missingChar("kitten", 0) → "itten"
 * missingChar("kitten", 4) → "kittn"
 */
@Solution
public String missingChar(String str, int n) {
    return str.substring(0, n) + str.substring(n + 1);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {"kitten", 1},
                {"kitten", 0},
                {"kitten", 4},
                {"Hi", 0},
                {"Hi", 1},
                {"code", 0},
                {"code", 1},
                {"code", 2},
                {"code", 3},
                {"chocolate", 8},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                "ktten",
                "itten",
                "kittn",
                "i",
                "H",
                "ode",
                "cde",
                "coe",
                "cod",
                "chocolat",
        };
    }
}
