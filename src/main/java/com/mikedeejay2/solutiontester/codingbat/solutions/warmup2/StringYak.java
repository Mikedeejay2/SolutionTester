package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class StringYak implements SolutionTest
{



/*
 * Suppose the string "yak" is unlucky. Given a string, return a version where all the "yak" are removed, but the "a"
 * can be any char. The "yak" strings will not overlap.
 *
 *
 * stringYak("yakpak") → "pak"
 * stringYak("pakyak") → "pak"
 * stringYak("yak123ya") → "123ya"
 */
@Solution
public String stringYak(String str) {
  StringBuilder res = new StringBuilder();
  for(int i = 0; i < str.length(); ++i) {
    if(str.charAt(i) == 'y' && i + 2 < str.length() && str.charAt(i + 2) == 'k') i += 2;
    else res.append(str.charAt(i));
  }
  return res.toString();
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"yakpak"},
            {"pakyak"},
            {"yak123ya"},
            {"yak"},
            {"yakxxxyak"},
            {"HiyakHi"},
            {"xxxyakyyyakzzz"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "pak",
            "pak",
            "123ya",
            "",
            "xxx",
            "HiHi",
            "xxxyyzzz",
        };
    }
}
