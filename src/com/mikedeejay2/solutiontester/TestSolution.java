package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class TestSolution implements SolutionTest
{
    @Solution
    public String combineStrNum(String str, int num) {
        return str + num;
    }

    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {"hi!", 2},
                {"bye!", 4},
                {"How are you?", 1337}
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                "hi!2",
                "bye!4",
                "How are you?1337"
        };
    }
}
