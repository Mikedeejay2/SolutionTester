package com.mikedeejay2.codingbatsolutions.solutions.warmup1;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatSolution;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Inputs;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Results;
import com.mikedeejay2.codingbatsolutions.internal.annotations.Solution;

import java.util.stream.Collectors;

public class EveryNth implements CodingBatSolution {



/*
 * Given a non-empty string and an int N, return the string made starting with char 0, and then every Nth char of the
 * string. So if N is 3, use char 0, 3, 6, ... and so on. N is 1 or more.
 *
 *
 * everyNth("Miracle", 2) → "Mrce"
 * everyNth("abcdefg", 2) → "aceg"
 * everyNth("abcdefg", 3) → "adg"
 */
@Solution
public String everyNth(String str, int n) {
    StringBuilder res = new StringBuilder();
    for(int i = 0; i < str.length(); i += n) {
        res.append(str.charAt(i));
    }
    return res.toString();
}





    @Inputs
    public Object[][] inputs() {
        return new Object[][] {
            {"Miracle", 2},
            {"abcdefg", 2},
            {"abcdefg", 3},
            {"Chocolate", 3},
            {"Chocolates", 3},
            {"Chocolates", 4},
            {"Chocolates", 100},
        };
    }

    @Results
    public Object[] results() {
        return new Object[] {
            "Mrce",
            "aceg",
            "adg",
            "Cca",
            "Ccas",
            "Coe",
            "C",
        };
    }
}
