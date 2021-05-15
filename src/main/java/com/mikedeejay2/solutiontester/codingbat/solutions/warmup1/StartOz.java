package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class StartOz implements SolutionTest
{



/*
 * Given a string, return a string made of the first 2 chars (if present), however include first char only if it is 'o'
 * and include the second only if it is 'z', so "ozymandias" yields "oz".
 *
 *
 * startOz("ozymandias") → "oz"
 * startOz("bzoo") → "z"
 * startOz("oxx") → "o"
 */
@Solution
public String startOz(String str) {
    return str.length() >= 2 && str.startsWith("oz") ? "oz" :
           str.length() >= 1 && str.charAt(0) == 'o' ? "o" :
           str.length() >= 2 && str.charAt(1) == 'z' ? "z" : "";
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "ozymandias",
            "bzoo",
            "oxx",
            "oz",
            "ounce",
            "o",
            "abc",
            "",
            "zoo",
            "aztec",
            "zzzz",
            "oznic",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "oz",
            "z",
            "o",
            "oz",
            "o",
            "o",
            "",
            "",
            "",
            "z",
            "z",
            "oz",
        };
    }
}
