package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class BackAround implements CodingBatSolution {



/*
 * Given a string, take the last char and return a new string with the last char added at the front and back, so "cat"
 * yields "tcatt". The original string will be length 1 or more.
 *
 *
 * backAround("cat") → "tcatt"
 * backAround("Hello") → "oHelloo"
 * backAround("a") → "aaa"
 */
@Solution
public String backAround(String str) {
    return str.isEmpty() ? str : str.charAt(str.length() - 1) + str + str.charAt(str.length() - 1);
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "cat",
            "Hello",
            "a",
            "abc",
            "read",
            "boo",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "tcatt",
            "oHelloo",
            "aaa",
            "cabcc",
            "dreadd",
            "obooo",
        };
    }
}
