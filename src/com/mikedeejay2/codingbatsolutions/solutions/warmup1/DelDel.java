package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class DelDel implements CodingBatSolution {



/*
 * Given a string, if the string "del" appears starting at index 1, return a string where that "del" has been deleted.
 * Otherwise, return the string unchanged.
 *
 *
 * delDel("adelbc") → "abc"
 * delDel("adelHello") → "aHello"
 * delDel("adedbc") → "adedbc"
 */
@Solution
public String delDel(String str) {
    return str.length() < 4 ? str : str.indexOf("del") == 1 ? str.replaceFirst("del", "") : str;
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "adelbc",
            "adelHello",
            "adedbc",
            "abcdel",
            "add",
            "ad",
            "a",
            "",
            "del",
            "adel",
            "aadelbb",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "abc",
            "aHello",
            "adedbc",
            "abcdel",
            "add",
            "ad",
            "a",
            "",
            "del",
            "a",
            "aadelbb",
        };
    }
}
