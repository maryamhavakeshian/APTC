package com.csi.util;

import com.csi.model.TestCase;

import java.util.ArrayList;

/**
 * Created by M_Havakesheyan on 11/25/2020.
 */
public class Configuration  {

    private int mutationOperator, crossoverOperator, selectionOperator,repetitions,elitismCount,populationSize,tournamentSize, testCaseTotalCount,  testCaseCoveragePointCount;

    private double  targetFitness, maximumExecutionTime,mutationRate,crossOverRate;
    private String dataFileCoverage, configuration;
    private ArrayList<TestCase> testCases;
    private CoverageFactory coverageFactory = new CoverageFactory();

    private int inputParameter =0;

    public Configuration(String[] args) {

        populationSize = 100;
        crossOverRate = 0.8;
        mutationRate = 0.05;
        elitismCount =1;
        maximumExecutionTime = Double.parseDouble("60000");//3600000 ///600000" //300000
        targetFitness = 0.87;
        repetitions = 14;




        dataFileCoverage = args[inputParameter];inputParameter++;
        selectionOperator = Integer.parseInt(args[inputParameter]);inputParameter++;
        crossoverOperator = Integer.parseInt(args[inputParameter]);inputParameter++;
        mutationOperator = Integer.parseInt(args[inputParameter]); inputParameter++;

    }

    public void initializeDataCoverage() {
        testCases = coverageFactory.readTestCaseInformation(dataFileCoverage);
        testCaseTotalCount= coverageFactory.getMaxTestcaseCount();
        testCaseCoveragePointCount =coverageFactory.getMaxCoveragePointCount();
    }


    public void setElitismCount(int elitismCount) {
        this.elitismCount = elitismCount;
    }

    public void setPopulationSize(int populationSize) {
        this.populationSize = populationSize;
    }

    public void setTournamentSize(int tournamentSize) {
        this.tournamentSize = tournamentSize;
    }

    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    public void setCrossOverRate(double crossOverRate) {
        this.crossOverRate = crossOverRate;
    }

    public int getTestCaseTotalCount() {
        return testCaseTotalCount;
    }

    public void setTestCaseTotalCount(int testCaseTotalCount) {
        this.testCaseTotalCount = testCaseTotalCount;
    }

    public int getTestCaseCoveragePointCount() {
        return testCaseCoveragePointCount;
    }

    public void setTestCaseCoveragePointCount(int testCaseCoveragePointCount) {
        this.testCaseCoveragePointCount = testCaseCoveragePointCount;
    }

    public ArrayList<TestCase> getTestcase() {
        return testCases;
    }


    public int getMutationOperator() {
        return mutationOperator;
    }

    public int getCrossoverOperator() {
        return crossoverOperator;
    }

    public int getSelectionOperator() {
        return selectionOperator;
    }


    public double getMutationRate() {
        return mutationRate;
    }

    public double getTargetFitness() {
        return targetFitness;
    }

    public double getCrossOverRate() {
        return crossOverRate;
    }

    public int getElitismCount() {
        return elitismCount;
    }

    public int getTournamentSize() {
        return tournamentSize;
    }

    public double getMaximumExecutionTime() {
        return maximumExecutionTime;
    }

    public int getRepetitions() {
        return repetitions;
    }

    public int getPopulationSize() {
        return populationSize;
    }

    public String getConfig() {
        return configuration;
    }

}
