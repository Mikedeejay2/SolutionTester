package com.mikedeejay2.codingbatsolutions;

import com.mikedeejay2.codingbatsolutions.internal.CodingBatRunner;
import com.mikedeejay2.codingbatsolutions.solutions.TestSolution;

public class Main {
    public static void main(String[] args) {
        CodingBatRunner runner = new CodingBatRunner();
        runner.run(new TestSolution());
    }
}
