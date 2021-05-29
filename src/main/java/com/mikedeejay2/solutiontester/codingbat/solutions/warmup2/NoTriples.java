package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class NoTriples implements SolutionTest
{



/*
 * Given an array of ints, we'll say that a triple is a value appearing 3 times in a row in the array. Return true if the array does not contain any triples.
 *
 *
 * noTriples([1, 1, 2, 2, 1]) → true
 * noTriples([1, 1, 2, 2, 2, 1]) → false
 * noTriples([1, 1, 1, 2, 2, 2, 1]) → false
 */
@Solution
public boolean noTriples(int[] nums) {
  int num = -1, count = 0;
    for(int i : nums) {
      if(i != num) {
        if(count == 3) return false;
        num = i;
        count = 0;
      }
      ++count;
    }
    return count != 3;
}






    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {new int[]{1, 1, 2, 2, 1}},
            {new int[]{1, 1, 2, 2, 2, 1}},
            {new int[]{1, 1, 1, 2, 2, 2, 1}},
            {new int[]{1, 1, 2, 2, 1, 2, 1}},
            {new int[]{1, 2, 1}},
            {new int[]{1, 1, 1}},
            {new int[]{1, 1}},
            {new int[]{1}},
            {new int[]{}},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            false,
            false,
            true,
            true,
            false,
            true,
            true,
            true,
        };
    }
}
