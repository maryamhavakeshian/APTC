package com.csi.controller.fitnessComputation;

import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.Population;

import java.util.ArrayList;

/**
 * Created by M_Havakesheyan on 11/21/2020.
 */
public class ObjectiveEvaluator {
    public ObjectiveEvaluator() {
        super();
    }

    public void evaluatePopulation(Population population, int testCaseTotalCount, int testCaseCoveragePointCount) {
        for (Individual individual: population.getIndividuals()) {
            evaluateFitness(individual, testCaseTotalCount, testCaseCoveragePointCount);
        }

    }



    public double evaluateFitness(Individual individual, int testcaseTotalCount, int testcaseCoveragePointCount) {
        Double fitness = 0.0;
        int culmulativeCoveragPointPosition=0;
        //List of testCases
        ArrayList<Chromosome> testCaseList= individual.getChromosomes();

        int coveragePointCounts=testcaseCoveragePointCount;
        int testcaseCount=testcaseTotalCount;
        double doubleCoveragePointCnt = new Double(coveragePointCounts); //m in the fitness formula
        double doubleTestCaseCnt= new Double(testcaseCount);//n in the fitness formula
        //computing m*n in the fitness formula
        double requireCntTimeTestCase= doubleCoveragePointCnt*doubleTestCaseCnt;
        //computing 2*n in the fitness formula
        double testcaseThreshold = 2*doubleCoveragePointCnt;
        //computing 1/2n in the fitness formula
        testcaseThreshold = 1/testcaseThreshold;
        double normalizedCumulativepostiion;
//coveragePointPosition is an empty array with the size of coverage points is initiated to maintain the position
// of  the first test case covering each coverage point
        int coveragePointPosition[] = new int[coveragePointCounts];
        for (int testcaseIdx = 0; testcaseIdx < testCaseList.size(); testcaseIdx++) {
            // the coverage points of each test case (chromosomes) are checked to find wheteher they are covered by previous test cases or not
            //the  coveredCoveragePoints array contains the coverage points which are covered by the testCase(testCaseIdx)
            int coveredCoveragePoints[] = testCaseList.get(testcaseIdx).getCoveragePoint();
            for (int coveragePointIdx = 0; coveragePointIdx < coveredCoveragePoints.length; coveragePointIdx++) {
                // check of the coverage point covered by the current test case have been covered by previous test cases or not (equal 0 means not has been covered)
                if (coveragePointPosition[coveredCoveragePoints[coveragePointIdx] -1] == 0) {
                    //set the index of the current test case(chromosome) to keep the position of the first chromosome which cover the coverage point
                    coveragePointPosition[coveredCoveragePoints[coveragePointIdx]-1] = testcaseIdx + 1;
                }
            }

        }
        //computing the accumulative first test cases covered coverage points
        for (int i = 0; i < coveragePointCounts; i++) {
            culmulativeCoveragPointPosition = culmulativeCoveragPointPosition + coveragePointPosition[i];
        }
        //compute sum of first positions/ (n*m)
        normalizedCumulativepostiion= culmulativeCoveragPointPosition/requireCntTimeTestCase;
        //computing  the entire formula
        fitness= new Double(1- (normalizedCumulativepostiion)+ testcaseThreshold);
        individual.setFitness(fitness);

        return fitness;

    }




}
