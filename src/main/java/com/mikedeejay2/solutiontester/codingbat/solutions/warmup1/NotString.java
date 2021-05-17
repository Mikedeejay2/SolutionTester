package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class NotString implements SolutionTest
{



/*
 * Given a string, return a new string where "not " has been added to the front. However, if the string already begins
 * with "not", return the string unchanged. Note: use .equals() to compare 2 strings.
 *
 *
 * notString("candy") → "not candy"
 * notString("x") → "not x"
 * notString("not bad") → "not bad"
 */
@Solution
public String notString(String str) {
    return str.startsWith("not") ? str : "not " + str;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"candy"},
            {"x"},
            {"not bad"},
            {"bad"},
            {"not"},
            {"is not"},
            {"no"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                "not candy",
                "not x",
                "not bad",
                "not bad",
                "not",
                "not is not",
                "not no",
        };
    }
}
