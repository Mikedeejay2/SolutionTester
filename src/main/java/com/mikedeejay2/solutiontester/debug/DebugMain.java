package com.mikedeejay2.solutiontester.debug;

import com.mikedeejay2.solutiontester.test.SolutionTester;

/**
 * A simple <strong>debug</strong> main class for testing solutions outside of JUnit.
 *
 * This class should be ignored.
 */
public class DebugMain
{
    /**
     * Debug main method
     *
     * @param args Not used
     */
    public static void main(String[] args) {
        SolutionTester tester = new SolutionTester();
        tester.apply(new DebugSolution());
    }
}
