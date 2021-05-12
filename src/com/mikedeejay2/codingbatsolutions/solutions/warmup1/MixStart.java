package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class MixStart implements SolutionTest
{



/*
 * Return true if the given string begins with "mix", except the 'm' can be anything, so "pix", "9ix" .. all count.
 *
 *
 * mixStart("mix snacks") → true
 * mixStart("pix snacks") → true
 * mixStart("piz snacks") → false
 */
@Solution
public boolean mixStart(String str) {
    return str.indexOf("ix") == 1;
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "mix snacks",
            "pix snacks",
            "piz snacks",
            "nix",
            "ni",
            "n",
            "",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            true,
            false,
            true,
            false,
            false,
            false,
        };
    }
}
