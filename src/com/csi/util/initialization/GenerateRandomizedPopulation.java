package com.csi.util.initialization;

import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.Population;
import com.csi.model.TestCase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class GenerateRandomizedPopulation {
    private static Population randomizedPopulation;
    public static Population getInitialPopulation() {
        return randomizedPopulation;
    }

    public static Population createPopulation (ArrayList<Chromosome> chromosomes,int popSize,long randomizedValue) {

        Random randomValue = new Random(randomizedValue);
        ArrayList<Individual> individuals = new ArrayList<Individual>(popSize);

        for (int individualIndex=0; individualIndex < popSize; individualIndex++) {
            individuals.add(createRandomizedIndividuals (chromosomes, randomValue.nextLong()));
        }

        randomizedPopulation = new Population(individuals);
        return new Population(individuals);

    }

    private static ArrayList<Chromosome> newChromosomes;
     public static ArrayList<Chromosome> createChromosomes (ArrayList<TestCase> testCaseList) {
        ArrayList<Chromosome> chromosomes  = new ArrayList<Chromosome>(testCaseList.size());
        for (TestCase testCase: testCaseList) {
            chromosomes.add(new Chromosome(testCase));
        }
         newChromosomes = new ArrayList<Chromosome> (chromosomes);

        return new ArrayList<Chromosome>(chromosomes);

    }


    public static Individual createRandomizedIndividuals (ArrayList<Chromosome> chromosomes, long ranosmizedValue) {

        Random randomValue = new Random(ranosmizedValue);
        ArrayList<Chromosome> tempChromosomes = new ArrayList<Chromosome>(chromosomes);

        Individual tempIndividual = new Individual(tempChromosomes.size());
        for (int chromosomeIndex = 0; chromosomeIndex < chromosomes.size(); chromosomeIndex++) {
            tempIndividual.addChromosome(tempChromosomes.remove(randomValue.nextInt(tempChromosomes.size())));
        }

//        if (chromosomes.size() < 20) {
//            Collections.shuffle(tempChromosomes);
//            for (int chromosomeIndex = 0; chromosomeIndex < chromosomes.size(); chromosomeIndex++) {
//                tempIndividual.addChromosome(tempChromosomes.remove(chromosomeIndex));
//            }
//
//        }

        return tempIndividual;
    }
}





