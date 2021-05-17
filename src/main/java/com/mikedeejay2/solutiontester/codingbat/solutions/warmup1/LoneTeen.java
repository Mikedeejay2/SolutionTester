package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class LoneTeen implements SolutionTest
{



/*
 * We'll say that a number is "teen" if it is in the range 13..19 inclusive. Given 2 int values, return true if one or
 * the other is teen, but not both.
 *
 *
 * loneTeen(13, 99) → true
 * loneTeen(21, 19) → true
 * loneTeen(13, 13) → false
 */
@Solution
public boolean loneTeen(int a, int b) {
  return (a >= 13 && a <= 19) ^ (b >= 13 && b <= 19);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {13, 99},
            {21, 19},
            {13, 13},
            {14, 20},
            {20, 15},
            {16, 17},
            {16, 9},
            {16, 18},
            {13, 19},
            {13, 20},
            {6, 18},
            {99, 13},
            {99, 99},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            true,
            false,
            true,
            true,
            false,
            true,
            false,
            false,
            true,
            true,
            true,
            false,
        };
    }
}
