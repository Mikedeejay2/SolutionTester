package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class CountXX implements SolutionTest
{



/*
 * Count the number of "xx" in the given string. We'll say that overlapping is allowed, so "xxx" contains 2 "xx".
 *
 *
 * countXX("abcxx") → 1
 * countXX("xxx") → 2
 * countXX("xxxx") → 3
 */
@Solution
int countXX(String str) {
    int count = 0;
    for(int i = 0; i < str.length() - 1; ++i) {
        if(str.startsWith("xx", i)) ++count;
    }
    return count;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"abcxx"},
            {"xxx"},
            {"xxxx"},
            {"abc"},
            {"Hello there"},
            {"Hexxo thxxe"},
            {""},
            {"Kittens"},
            {"Kittensxxx"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            1,
            2,
            3,
            0,
            0,
            2,
            0,
            0,
            2,
        };
    }
}
