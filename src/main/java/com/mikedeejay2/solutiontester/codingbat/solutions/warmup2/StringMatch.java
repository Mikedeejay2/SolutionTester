package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class StringMatch implements SolutionTest
{



/*
 * Given 2 strings, a and b, return the number of the positions where they contain the same length 2 substring. So
 * "xxcaazz" and "xxbaaz" yields 3, since the "xx", "aa", and "az" substrings appear in the same place in both strings.
 *
 *
 * stringMatch("xxcaazz", "xxbaaz") → 3
 * stringMatch("abc", "abc") → 2
 * stringMatch("abc", "axc") → 0
 */
@Solution
public int stringMatch(String a, String b) {
  int count = 0;
  for(int i = 0; i < Math.min(a.length(), b.length()) - 1; ++i) {
    String subA = a.substring(i, i + 2), subB = b.substring(i, i + 2);
    if(subA.equals(subB)) ++count;
  }
  return count;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"xxcaazz", "xxbaaz"},
            {"abc", "abc"},
            {"abc", "axc"},
            {"hello", "he"},
            {"he", "hello"},
            {"h", "hello"},
            {"", "hello"},
            {"aabbccdd", "abbbxxd"},
            {"aaxxaaxx", "iaxxai"},
            {"iaxxai", "aaxxaaxx"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            3,
            2,
            0,
            1,
            1,
            0,
            0,
            1,
            3,
            3,
        };
    }
}
