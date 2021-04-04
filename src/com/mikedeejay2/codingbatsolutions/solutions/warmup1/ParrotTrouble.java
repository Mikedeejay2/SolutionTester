package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class ParrotTrouble implements CodingBatSolution {



    /*
     * We have a loud talking parrot. The "hour" parameter is the current hour time in the range 0..23. We are in
     * trouble if the parrot is talking and the hour is before 7 or after 20. Return true if we are in trouble.
     *
     *
     * parrotTrouble(true, 6) → true
     * parrotTrouble(true, 7) → false
     * parrotTrouble(false, 6) → false
     */
    @Solution
    public boolean parrotTrouble(boolean talking, int hour) {
        return talking && (hour < 7 || hour > 20);
    }





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {true, 6},
                {true, 7},
                {false, 6},
                {true, 21},
                {false, 21},
                {false, 20},
                {true, 23},
                {false, 23},
                {true, 20},
                {false, 12},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                false,
                false,
                true,
                false,
                false,
                true,
                false,
                false,
                false
        };
    }
}
