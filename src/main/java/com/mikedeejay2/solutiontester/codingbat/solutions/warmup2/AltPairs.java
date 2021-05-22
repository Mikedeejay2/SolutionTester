package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class AltPairs implements SolutionTest
{



/*
 * Given a string, return a string made of the chars at indexes 0,1, 4,5, 8,9 ... so "kittens" yields "kien".
 *
 *
 * altPairs("kitten") → "kien"
 * altPairs("Chocolate") → "Chole"
 * altPairs("CodingHorror") → "Congrr"
 */
@Solution
public String altPairs(String str) {
  StringBuilder res = new StringBuilder();
  for(int i = 0; i < str.length(); i += 4) {
    res.append(str, i, i == str.length() - 1 ? i + 1 : i + 2);
  }
  return res.toString();
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"kitten"},
            {"Chocolate"},
            {"CodingHorror"},
            {"yak"},
            {"ya"},
            {"y"},
            {""},
            {"ThisThatTheOther"},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "kien",
            "Chole",
            "Congrr",
            "ya",
            "ya",
            "y",
            "",
            "ThThThth",
        };
    }
}
