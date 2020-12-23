package com.csi.util;

import com.csi.model.TestCase;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
/**
 * Created by M_Havakesheyan on 11/24/2020.
 */


//read testcases and coverage points from datafile.  create arrays of test cases by mapping
// test cases to their  the corresponding coverage points
public class CoverageFactory {
    private int maxTestcaseCount = 0;
    private int maxCoveragePointCount = 0;

    public int getMaxTestcaseCount() {
        return maxTestcaseCount;
    }

    public void setMaxTestcaseCount(int maxTestcaseCount) {
        this.maxTestcaseCount = maxTestcaseCount;
    }

    public int getMaxCoveragePointCount() {
        return maxCoveragePointCount;
    }

    public void setMaxCoveragePointCount(int maxCoveragePointCount) {
        this.maxCoveragePointCount = maxCoveragePointCount;
    }

    public ArrayList<TestCase> readTestCaseInformation(String coverageFileName) {
        ArrayList<TestCase> testCases = new ArrayList<TestCase>();
        HashMap<Integer, ArrayList<Integer>> coverageMap = new HashMap<Integer, ArrayList<Integer>>();
        int testCaseId, coveragePointNumber;
        try {
            ArrayList<Integer> coverageList;
            Scanner coverage = new Scanner(new BufferedReader(new FileReader(coverageFileName)));
            coverage.nextLine();
            while (coverage.hasNextInt()) {
                testCaseId = coverage.nextInt();
                coveragePointNumber = coverage.nextInt();
                if (testCaseId > maxTestcaseCount)
                    this.maxTestcaseCount = testCaseId;
                if (coveragePointNumber > maxCoveragePointCount)
                    this.maxCoveragePointCount = coveragePointNumber;
                if (!coverageMap.containsKey(testCaseId)) {
                    coverageMap.put(testCaseId, new ArrayList<Integer>());
                }
                coverageMap.get(testCaseId).add(coveragePointNumber);
            }

            for (int i = 1; i <= maxTestcaseCount; i++) {
                if (!coverageMap.containsKey(i))
                    coverageMap.put(i, new ArrayList<Integer>());
            }
            Set<Integer> keySets = coverageMap.keySet();
            for (Iterator<Integer> it = keySets.iterator(); it.hasNext(); ) {
                testCaseId = it.next();
                coverageList = coverageMap.get(testCaseId);
                int[] coveragePoints;
                if (coverageList != null) {
                    coveragePoints = new int[coverageList.size()];
                    for (int i = 0; i < coveragePoints.length; i++) {
                        coveragePoints[i] = coverageList.get(i);
                    }
                } else {
                    coveragePoints = new int[0];
                }
                testCases.add(new TestCase(coveragePoints, testCaseId));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
            System.exit(1);
        }
        return testCases;

    }


}
