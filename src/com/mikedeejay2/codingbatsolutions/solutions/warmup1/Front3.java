package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class Front3 implements CodingBatSolution {



/*
 * Given a string, we'll say that the front is the first 3 chars of the string. If the string length is less than 3,
 * the front is whatever is there. Return a new string which is 3 copies of the front.
 *
 *
 * front3("Java") → "JavJavJav"
 * front3("Chocolate") → "ChoChoCho"
 * front3("abc") → "abcabcabc"
 */
@Solution
public String front3(String str) {
    String front = str.length() < 3 ? str : str.substring(0, 3);
    return front + front + front;
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "Java",
            "Chocolate",
            "abc",
            "abcXYZ",
            "ab",
            "a",
            "",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "JavJavJav",
            "ChoChoCho",
            "abcabcabc",
            "abcabcabc",
            "ababab",
            "aaa",
            "",
        };
    }
}
