package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class StringE implements SolutionTest
{



/*
 * Return true if the given string contains between 1 and 3 'e' chars.
 *
 *
 * stringE("Hello") → true
 * stringE("Heelle") → true
 * stringE("Heelele") → false
 */
@Solution
public boolean stringE(String str) {
  long count = str.chars().filter(c -> c == 'e').count();
  return count >= 1 && count <= 3;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"Hello"},
            {"Heelle"},
            {"Heelele"},
            {"Hll"},
            {"e"},
            {""},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            true,
            false,
            false,
            true,
            false,
        };
    }
}
