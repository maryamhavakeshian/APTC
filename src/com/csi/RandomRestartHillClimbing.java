package com.csi;

import com.csi.controller.fitnessComputation.ObjectiveEvaluator;
import com.csi.controller.operator.mutationOperator.InitializeMutationOperators;
import com.csi.controller.operator.mutationOperator.Mutation;
import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.util.CSVUtils;
import com.csi.util.Configuration;
import com.csi.util.initialization.GenerateRandomizedPopulation;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by M_Havakesheyan on 12/2/2020.
 */

//random mutationType HC
public class RandomRestartHillClimbing {
    ObjectiveEvaluator fitnessCalculator;
    Individual bestIndividual, initIndividual;
    long startTime, runTime,observeSartTime, observeRunTime;
    Configuration config;
    Mutation mutation;
    double targetFitness,maxExecutionTime;
    int repetition,testCaseTotalCount,testCaseCoveragePointCount;
    ArrayList<Chromosome> initChromosomes;
    Random randomValue;
    int numOfEval;


    public RandomRestartHillClimbing(Configuration config, int repetition) {
        this.config = config;
        this.repetition = repetition;
        initChromosomes  = GenerateRandomizedPopulation.createChromosomes(config.getTestcase());
        Collections.shuffle(initChromosomes);
        initIndividual = new Individual(initChromosomes);
        targetFitness = config.getTargetFitness();
        mutation  = InitializeMutationOperators.getMutationType(1);
        testCaseCoveragePointCount = config.getTestCaseCoveragePointCount();
        testCaseTotalCount = config.getTestCaseTotalCount();
        maxExecutionTime= config.getMaximumExecutionTime();
        fitnessCalculator = new ObjectiveEvaluator();
        // compute fitness
        fitnessCalculator.evaluateFitness(initIndividual, testCaseTotalCount,testCaseCoveragePointCount );

    }

    public Individual observeHillClimber(){
        observeSartTime = System.currentTimeMillis();
        Individual ind = hillClimber();
          while (ind==null)
            ind= hillClimber();
        observeRunTime = System.currentTimeMillis()-observeSartTime;
        bestIndividual =ind;
        System.out.println("best fitness: "+bestIndividual.getFitness());
        return  bestIndividual;

    }


    public Individual hillClimber() {

        double bestFitness, tmpFitness = 0;
        double currentFitness;
         int iteration=0;

        Individual lastTestSuite, neighbouringIndividual;
        startTime = System.currentTimeMillis();
                // clone  the current Indiviual
        Collections.shuffle(initChromosomes);
        lastTestSuite =  new Individual(initChromosomes);
        fitnessCalculator.evaluateFitness(lastTestSuite, testCaseTotalCount,testCaseCoveragePointCount );
        bestIndividual = lastTestSuite;
        bestFitness= lastTestSuite.getFitness();
        while(  System.currentTimeMillis()- startTime < maxExecutionTime ){
            iteration++;
                //  mutate two random TCs to reach the neighbor
                neighbouringIndividual = mutation.mutateIndividual(lastTestSuite);
                // calculate fitnesses for neighbor
                fitnessCalculator.evaluateFitness(neighbouringIndividual, testCaseTotalCount,testCaseCoveragePointCount );
                currentFitness = neighbouringIndividual.getFitness();
               if (currentFitness> bestFitness) {
                    bestIndividual =neighbouringIndividual;
                    bestFitness = currentFitness;
                    lastTestSuite = neighbouringIndividual;
                    fitnessCalculator.evaluateFitness(lastTestSuite, testCaseTotalCount,testCaseCoveragePointCount );
                    numOfEval = 0;
                }
                else
                    numOfEval++;

                if (numOfEval>50){
                    numOfEval=0;
                    Collections.shuffle(initChromosomes);
                    lastTestSuite =  new Individual(initChromosomes);
                    fitnessCalculator.evaluateFitness(lastTestSuite,testCaseTotalCount,testCaseCoveragePointCount);
                    tmpFitness=lastTestSuite.getFitness();
                    if (tmpFitness>bestFitness)
                    bestFitness=tmpFitness;

                }

            CSVUtils.showOutput(iteration, System.currentTimeMillis()-startTime, bestIndividual,repetition+"outHC.csv");
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


    public void showOutput() {



        BufferedWriter out;
        String toFile =
                +repetition+1+","+runTime+"\n";

        try {


            out = new BufferedWriter
                    (new FileWriter("outHC.csv",true));
            out.flush();
            out.write(toFile);
            out.close();

        }
        catch (IOException ex) {

            ex.printStackTrace();

        }





    }


}



