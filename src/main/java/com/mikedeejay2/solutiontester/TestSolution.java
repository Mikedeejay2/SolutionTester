package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class TestSolution implements SolutionTest
{
    @Solution
    public String[] combineStrNumGlobal(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Solution(ids = "test")
    public String[] combineStrNum(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Solution(ids = "test")
    public String[] combineStrNum2(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Inputs(id = "test")
    public Object[][] inputs() {
        return new Object[][] {
                {"hi!", new int[][]{{2}}},
                {"bye!", new int[][]{{4}}},
                {"How are you?", new int[][]{{1337}}}
        };
    }

    @Inputs(id = "test")
    public Object[][] inputs2() {
        return new Object[][] {
            {"hi!", new int[][]{{2}}},
            {"bye!", new int[][]{{4}}},
            {"How are you?", new int[][]{{1337}}}
        };
    }

    @Results(id = "test")
    public Object[] results() {
        return new Object[] {
                new String[]{"hi!2"},
                new String[]{"bye!4"},
                new String[]{"How are you?1337"}
        };
    }

    @Solution(ids = "random")
    public String[] combineStrNumRand(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Solution(ids = "random")
    public String[] combineStrNum2Rand(String str, int[][] num) {
        return new String[]{str + num[0][0]};
    }

    @Inputs(id = "random")
    public Object[][] inputsRand() {
        return new Object[][] {
            {"hi!", new int[][]{{2}}},
            {"bye!", new int[][]{{4}}},
            {"How are you?", new int[][]{{1337}}}
        };
    }

    @Results(id = "random")
    public Object[] resultsRand() {
        return new Object[] {
            new String[]{"hi!2"},
            new String[]{"bye!4"},
            new String[]{"How are you?1337"}
        };
    }
}
