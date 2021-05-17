package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class EndUp implements SolutionTest
{



/*
 * Given a string, return a new string where the last 3 chars are now in upper case. If the string has less than 3
 * chars, uppercase whatever is there. Note that str.toUpperCase() returns the uppercase version of a string.
 *
 *
 * endUp("Hello") → "HeLLO"
 * endUp("hi there") → "hi thERE"
 * endUp("hi") → "HI"
 */
@Solution
public String endUp(String str) {
  return str.length() < 3 ? str.toUpperCase() :
    str.substring(0, str.length() - 3) + str.substring(str.length() - 3).toUpperCase();
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"Hello"},
            {"hi there"},
            {"hi"},
            {"woo hoo"},
            {"xyz12"},
            {"x"},
            {""},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "HeLLO",
            "hi thERE",
            "HI",
            "woo HOO",
            "xyZ12",
            "X",
            "",
        };
    }
}
