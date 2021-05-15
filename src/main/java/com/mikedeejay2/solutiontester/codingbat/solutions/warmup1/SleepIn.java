package com.mikedeejay2.solutiontester.codingbat.solutions.warmup1;

import com.mikedeejay2.solutiontester.internal.SolutionTest;
import com.mikedeejay2.solutiontester.internal.annotations.Inputs;
import com.mikedeejay2.solutiontester.internal.annotations.Results;
import com.mikedeejay2.solutiontester.internal.annotations.Solution;

public class SleepIn implements SolutionTest
{



/*
 * The parameter weekday is true if it is a weekday, and the parameter vacation is true if we are on vacation.
 * We sleep in if it is not a weekday or we're on vacation. Return true if we sleep in.
 *
 *
 * sleepIn(false, false) → true
 * sleepIn(true, false) → false
 * sleepIn(false, true) → true
 */
@Solution
public boolean sleepIn(boolean weekday, boolean vacation) {
    return !weekday || vacation;
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {false, false},
                {true, false},
                {false, true},
                {true, true},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                false,
                true,
                true
        };
    }
}
