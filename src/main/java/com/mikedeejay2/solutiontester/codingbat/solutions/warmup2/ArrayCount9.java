package com.mikedeejay2.solutiontester.codingbat.solutions.warmup2;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class ArrayCount9 implements SolutionTest
{



/*
 * Given an array of ints, return the number of 9's in the array.
 *
 *
 * arrayCount9([1, 2, 9]) → 1
 * arrayCount9([1, 9, 9]) → 2
 * arrayCount9([1, 9, 9, 3, 9]) → 3
 */
@Solution
public int arrayCount9(int[] nums) {
  return (int) java.util.Arrays.stream(nums).filter(n -> n == 9).count();
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {new int[]{1, 2, 9}},
            {new int[]{1, 9, 9}},
            {new int[]{1, 9, 9, 3, 9}},
            {new int[]{1, 2, 3}},
            {new int[]{}},
            {new int[]{4, 2, 4, 3, 1}},
            {new int[]{9, 2, 4, 3, 1}},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            1,
            2,
            3,
            0,
            0,
            0,
            1,
        };
    }
}
