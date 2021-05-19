package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Array123 implements SolutionTest
{



/*
 * Given an array of ints, return true if the sequence of numbers 1, 2, 3 appears in the array somewhere.
 *
 *
 * array123([1, 1, 2, 3, 1]) → true
 * array123([1, 1, 2, 4, 1]) → false
 * array123([1, 1, 2, 1, 2, 3]) → true
 */
@Solution
public boolean array123(int[] nums) {
  for(int i = 0; i < nums.length - 2; ++i) {
    if(nums[i] == 1 && nums[i + 1] == 2 && nums[i + 2] == 3) return true;
  }
  return false;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {new int[]{1, 1, 2, 3, 1}},
            {new int[]{1, 1, 2, 4, 1}},
            {new int[]{1, 1, 2, 1, 2, 3}},
            {new int[]{1, 1, 2, 1, 2, 1}},
            {new int[]{1, 2, 3, 1, 2, 3}},
            {new int[]{1, 2, 3}},
            {new int[]{1, 1, 1}},
            {new int[]{1, 2}},
            {new int[]{1}},
            {new int[]{}},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            true,
            false,
            true,
            false,
            true,
            true,
            false,
            false,
            false,
            false,
        };
    }
}
