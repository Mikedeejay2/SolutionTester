package com.mikedeejay2.codingbatsolutions.solutions.warmup2;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

public class StringBits implements CodingBatSolution {



/*
 * Given a string, return a new string made of every other char starting with the first, so "Hello" yields "Hlo".
 *
 *
 * stringBits("Hello") → "Hlo"
 * stringBits("Hi") → "H"
 * stringBits("Heeololeo") → "Hello"
 */
@Solution
public String stringBits(String str) {
    StringBuilder res = new StringBuilder();
    for(int i = 0; i < str.length(); i += 2) {
        res.append(str.charAt(i));
    }
    return res.toString();
}





    @Inputs
    public Object[] inputs() {
        return new Object[] {
            "Hello",
            "Hi",
            "Heeololeo",
            "HiHiHi",
            "",
            "Greetings",
            "Chocoate",
            "pi",
            "Hello Kitten",
            "hxaxpxpxy",
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "Hlo",
            "H",
            "Hello",
            "HHH",
            "",
            "Getns",
            "Coot",
            "p",
            "HloKte",
            "happy",
        };
    }
}
