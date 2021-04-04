package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class MonkeyTrouble implements CodingBatSolution {



/*
 * We have two monkeys, a and b, and the parameters aSmile and bSmile indicate if each is smiling. We are in
 * trouble if they are both smiling or if neither of them is smiling. Return true if we are in trouble.
 *
 *
 * monkeyTrouble(true, true) → true
 * monkeyTrouble(false, false) → true
 * monkeyTrouble(true, false) → false
 */
@Solution
public boolean monkeyTrouble(boolean aSmile, boolean bSmile) {
    return (aSmile && bSmile) || (!aSmile && !bSmile);
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
                {true, true},
                {false, false},
                {true, false},
                {false, true},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
                true,
                true,
                false,
                false,
        };
    }
}