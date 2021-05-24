package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class Array667 implements SolutionTest
{



/*
 * Given an array of ints, return the number of times that two 6's are next to each other in the array. Also count instances where the second "6" is actually a 7.
 *
 *
 * array667([6, 6, 2]) → 1
 * array667([6, 6, 2, 6]) → 1
 * array667([6, 7, 2, 6]) → 1
 */
@Solution
public int array667(int[] nums) {
  int count = 0;
  for(int i = 0; i < nums.length - 1; ++i) {
    if(nums[i] == 6 && (nums[i + 1] == 6 || nums[i + 1] == 7)) ++count;
  }
  return count;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {new int[]{6, 6, 2}},
            {new int[]{6, 6, 2, 6}},
            {new int[]{6, 7, 2, 6}},
            {new int[]{6, 6, 2, 6, 7}},
            {new int[]{1, 6, 3}},
            {new int[]{6, 1}},
            {new int[]{}},
            {new int[]{3, 6, 7, 6}},
            {new int[]{3, 6, 6, 7}},
            {new int[]{6, 3, 6, 6}},
            {new int[]{6, 7, 6, 6}},
            {new int[]{1, 2, 3, 5, 6}},
            {new int[]{1, 2, 3, 6, 6}},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            1,
            1,
            1,
            2,
            0,
            0,
            0,
            1,
            2,
            1,
            2,
            0,
            1,
        };
    }
}
