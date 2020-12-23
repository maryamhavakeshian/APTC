package com.csi;

import com.csi.controller.fitnessComputation.ObjectiveEvaluator;
import com.csi.controller.operator.mutationOperator.InitializeMutationOperators;
import com.csi.controller.operator.mutationOperator.Mutation;
import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.util.Configuration;
import com.csi.util.CSVUtils;
import com.csi.util.initialization.GenerateRandomizedPopulation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by m_havakesheyan on 12/6/2020.
 */
public class RandomSearch {

    ObjectiveEvaluator fitnessCalculator;
    Individual bestIndividual, initIndividual;
    long startTime, runTime;
    Configuration config;
    Mutation mutation;
    double targetFitness,maxTime;
    int repetition;
    ArrayList<Chromosome> initChromosomes;
    int numOfEval,iterationCount,testCaseTotalCount,testCaseCoveragePointCount;


    public RandomSearch(Configuration config, int repetition) {
        this.config = config;
        this.repetition = repetition;
        maxTime=config.getMaximumExecutionTime();
        initChromosomes = GenerateRandomizedPopulation.createChromosomes(config.getTestcase());
        Collections.shuffle(initChromosomes);
        initIndividual = new Individual(initChromosomes);
        targetFitness = config.getTargetFitness();
        mutation = InitializeMutationOperators .getMutationType(1);
        testCaseCoveragePointCount= config.getTestCaseCoveragePointCount();
        testCaseTotalCount = config.getTestCaseTotalCount();


        fitnessCalculator = new ObjectiveEvaluator();


        // compute fitness
        fitnessCalculator.evaluateFitness(initIndividual,testCaseTotalCount,testCaseCoveragePointCount);

    }




    public Individual randomSearchEngine() {

        double bestFitness, tmpFitness = 0;
        double currentFitness;


        Individual lastTestSuite, neighbouringIndividual;
        startTime = System.currentTimeMillis();
        Collections.shuffle(initChromosomes);
        lastTestSuite =  new Individual(initChromosomes);
        fitnessCalculator.evaluateFitness(lastTestSuite,testCaseTotalCount,testCaseCoveragePointCount );
        bestFitness= lastTestSuite.getFitness();
        bestIndividual = lastTestSuite;
//        while(  ( bestFitness< targetFitness) ){
            while(  System.currentTimeMillis()-startTime<maxTime){//3600000
            Collections.shuffle(initChromosomes);
            neighbouringIndividual = new Individual(initChromosomes);
            fitnessCalculator.evaluateFitness(neighbouringIndividual, testCaseTotalCount,testCaseCoveragePointCount );
            currentFitness = neighbouringIndividual.getFitness();
            if (currentFitness> bestFitness) {
                bestIndividual =neighbouringIndividual;
                bestFitness = currentFitness;
                numOfEval = 0;
            }
            else
                numOfEval++;

//                if (numOfEval>50){
//                    numOfEval=0;
//                    Collections.shuffle(initChromosomes);
//                    lastTestSuite =  new Individual(initChromosomes);
//                    fitnessEvaluator.evaluateFitness(lastTestSuite);
//                    tmpFitness=lastTestSuite.getFitness();
////                    if (tmpFitness>bestFitness)
//                    bestFitness=tmpFitness;
//
//                }

                iterationCount++;
//                CSVUtils.showOutput(configuration, iterationCount,
//                        System.currentTimeMillis()-startTime, repetition,numOfEval,
//                        currentFitness, bestFitness,
//                        bestIndividual,"outRS.csv");
//                System.out.println("best fitness" +bestFitness);
                CSVUtils.showOutput(iterationCount, System.currentTimeMillis()-startTime, bestIndividual,repetition+"outRS.csv");

            }
        runTime = System.currentTimeMillis()-startTime;
        //if (runTime>6000){
//            observeRunTime=runTime;
//            hillClimber();
//            observeRunTime = observeSartTime+runTime;
        //bestIndividual = null;
        //}


        System.out.println("best fitness: "+bestIndividual.getFitness());
        return  bestIndividual;

    }

}
