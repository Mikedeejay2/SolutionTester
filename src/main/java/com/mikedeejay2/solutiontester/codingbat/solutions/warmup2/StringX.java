package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class StringX implements SolutionTest
{



/*
 * Given a string, return a version where all the "x" have been removed. Except an "x" at the very start or end should
 * not be removed.
 *
 *
 * stringX("xxHxix") → "xHix"
 * stringX("abxxxcd") → "abcd"
 * stringX("xabxxxcdx") → "xabcdx"
 */
@Solution
public String stringX(String str) {
  return str.length() < 3 ? str :
    str.charAt(0) + str.substring(1, str.length() - 1).replace("x", "") +
    str.charAt(str.length() - 1);
}






    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"xxHxix"},
            {"abxxxcd"},
            {"xabxxxcdx"},
            {"xKittenx"},
            {"Hello"},
            {"xx"},
            {"x"},
            {""},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "xHix",
            "abcd",
            "xabcdx",
            "xKittenx",
            "Hello",
            "xx",
            "x",
            "",
        };
    }
}
