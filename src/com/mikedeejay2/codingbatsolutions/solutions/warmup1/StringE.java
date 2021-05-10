package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class StringE implements CodingBatSolution {



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
    public Object[] inputs() {
        return new Object[] {
            "Hello",
            "Heelle",
            "Heelele",
            "Hll",
            "e",
            "",
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
