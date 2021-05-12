package com.mikedeejay2.codingbatsolutions;

import com.mikedeejay2.codingbatsolutions.internal.DeprecatedSolutionTester;
import com.mikedeejay2.codingbatsolutions.solutions.TestSolution;

public class TesterMain {
    public static void main(String[] args) {
        DeprecatedSolutionTester runner = new DeprecatedSolutionTester();
        runner.run(new TestSolution());
    }
}
