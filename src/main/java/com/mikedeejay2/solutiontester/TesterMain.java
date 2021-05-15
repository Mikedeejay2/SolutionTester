package com.mikedeejay2.solutiontester;

import com.mikedeejay2.solutiontester.internal.DeprecatedSolutionTester;

public class TesterMain {
    public static void main(String[] args) {
        DeprecatedSolutionTester runner = new DeprecatedSolutionTester();
        runner.run(new TestSolution());
    }
}
