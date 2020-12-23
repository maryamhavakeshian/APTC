package com.csi.model;

/**
 * Created by M_Havakesheyan on 11/17/2020.
 */
public class Chromosome {

    TestCase testCase;

    public Chromosome(TestCase currentTestCase) {
        testCase = currentTestCase;
    }

    public Chromosome() {
        testCase = null;
    }

    public int[] getCoveragePoint() {
        return testCase.getTestPoints();
    }

    public int getId() {
        return testCase.getId();
    }
    public TestCase getTestCase() {
        return testCase;
    }

    //deep copy
    public static Chromosome copy(Chromosome chromosome) {
        return new Chromosome(chromosome.getTestCase());
    }

}

