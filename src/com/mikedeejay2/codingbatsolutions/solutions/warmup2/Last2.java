package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.SolutionTest;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class Last2 implements SolutionTest
{



/*
 * Given a string, return the count of the number of times that a substring length 2 appears in the string and also as
 * the last 2 chars of the string, so "hixxxhi" yields 1 (we won't count the end substring).
 *
 *
 * last2("hixxhi") → 1
 * last2("xaxxaxaxx") → 1
 * last2("axxxaaxx") → 2
 */
@Solution
public int last2(String str) {
    if(str.length() < 4) return 0;
    int count = 0;
    for(int i = 0; i < str.length() - 2; ++i) {
        if(str.startsWith(str.substring(str.length() - 2), i)) ++count;
    }
    return count;
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "hixxhi",
            "xaxxaxaxx",
            "axxxaaxx",
            "xxaxxaxxaxx",
            "xaxaxaxx",
            "xxxx",
            "13121312",
            "11212",
            "13121311",
            "1717171",
            "hi",
            "h",
            "",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            1,
            1,
            2,
            3,
            0,
            2,
            1,
            1,
            0,
            2,
            0,
            0,
            0,
        };
    }
}
