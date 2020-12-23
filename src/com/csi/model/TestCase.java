package com.csi.model;

/**
 * Created by M_Havakesheyan on 11/17/2020.
 */
public class TestCase{

    private int[] testPoints;
    private final int id;

    public TestCase( int[] testPointArrays,  int testCaseId) {
        testPoints = testPointArrays;
        this.id = testCaseId;

    }

    public TestCase(int testpoitnsCounts, int testCaseId) {
        testPoints = new int[testpoitnsCounts];
        id = testCaseId;
    }

    public TestCase(int testCaseId) {
        id = testCaseId;
    }

    public int[] getTestPoints() {
        return testPoints;
    }


    public int getId() {
        return id;
    }

}
