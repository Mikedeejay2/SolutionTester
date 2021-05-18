package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class ArrayFront9 implements SolutionTest
{



/*
 * Given an array of ints, return true if one of the first 4 elements in the array is a 9. The array length may be less
 * than 4.
 *
 *
 * arrayFront9([1, 2, 9, 3, 4]) → true
 * arrayFront9([1, 2, 3, 4, 9]) → false
 * arrayFront9([1, 2, 3, 4, 5]) → false
 */
@Solution
public boolean arrayFront9(int[] nums) {
    for(int i = 0; i < Math.min(nums.length, 4); ++i) {
        if(nums[i] == 9) return true;
    }
    return false;
}



/* Solution using Streams

@Solution
public boolean arrayFront92(int[] nums) {
  return java.util.Arrays
    .stream(java.util.Arrays.copyOfRange(nums, 0, Math.min(nums.length, 4)))
    .filter(n -> n == 9)
    .count() > 0;
}

 */

    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {new int[]{1, 2, 9, 3, 4}},
            {new int[]{1, 2, 3, 4, 9}},
            {new int[]{1, 2, 3, 4, 5}},
            {new int[]{9, 2, 3}},
            {new int[]{1, 9, 9}},
            {new int[]{1, 2, 3}},
            {new int[]{1, 9}},
            {new int[]{5, 5}},
            {new int[]{2}},
            {new int[]{9}},
            {new int[]{}},
            {new int[]{3, 9, 2, 3, 3}},
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
            false,
            false,
            true,
            false,
            true,
        };
    }
}
