package com.mikedeejay2.solutiontester.debug;

import com.mikedeejay2.solutiontester.SolutionTest;
import com.mikedeejay2.solutiontester.annotations.Inputs;
import com.mikedeejay2.solutiontester.annotations.Results;
import com.mikedeejay2.solutiontester.annotations.Solution;

/**
 * A <strong>debug</strong> solution for testing the testing system features.
 *
 * This class should be ignored.
 */
public class DebugSolution implements SolutionTest {
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
