package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class StringSplosion implements CodingBatSolution {



/*
 * Given a non-empty string like "Code" return a string like "CCoCodCode".
 *
 *
 * stringSplosion("Code") → "CCoCodCode"
 * stringSplosion("abc") → "aababc"
 * stringSplosion("ab") → "aab"
 */
@Solution
public String stringSplosion(String str) {
    StringBuilder res = new StringBuilder();
    for(int i = 0; i <= str.length(); ++i) {
        res.append(str, 0, i);
    }
    return res.toString();
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "Code",
            "abc",
            "ab",
            "x",
            "fade",
            "There",
            "Kitten",
            "Bye",
            "Good",
            "Bad",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "CCoCodCode",
            "aababc",
            "aab",
            "x",
            "ffafadfade",
            "TThTheTherThere",
            "KKiKitKittKitteKitten",
            "BByBye",
            "GGoGooGood",
            "BBaBad",
        };
    }
}
