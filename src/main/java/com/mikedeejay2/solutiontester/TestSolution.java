package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class TestSolution implements SolutionTest
{
    @Solution
    public String[] combineStrNum(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {"hi!", new Integer[][]{{2}}},
                {"bye!", new Integer[][]{{4}}},
                {"How are you?", new Integer[][]{{1337}}}
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                new String[]{"hi!2"},
                new String[]{"bye!4"},
                new String[]{"How are you?1337"}
        };
    }
}
