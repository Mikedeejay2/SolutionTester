package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Front22 implements SolutionTest
{



/*
 * Given a string, take the first 2 chars and return the string with the 2 chars added at both the front and back, so
 * "kitten" yields"kikittenki". If the string length is less than 2, use whatever chars are there.
 *
 *
 * front22("kitten") → "kikittenki"
 * front22("Ha") → "HaHaHa"
 * front22("abc") → "ababcab"
 */
@Solution
public String front22(String str) {
  String front = str.length() <= 2 ? str : str.substring(0, 2);
  return front + str + front;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"kitten"},
            {"Ha"},
            {"abc"},
            {"ab"},
            {"a"},
            {""},
            {"Logic"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "kikittenki",
            "HaHaHa",
            "ababcab",
            "ababab",
            "aaa",
            "",
            "LoLogicLo",
        };
    }
}
