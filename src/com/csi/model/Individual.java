package com.csi.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by M_Havakesheyan on 11/17/2020.
 */
public class Individual implements Comparable<Individual> {


    private ArrayList<Chromosome> chromosomes;


    private double fitness;
    private double probability;
    private double initFitness;
    private double accumulatedFitness;


    public Individual(ArrayList<Chromosome> individualChromosome,double individualFitness) {
        chromosomes = individualChromosome;
        fitness = individualFitness;
        initFitness = fitness;
    }

    public Individual(ArrayList<Chromosome> individualChromosome) {
        chromosomes = individualChromosome;
        fitness = -1;
        initFitness = fitness;
    }

    //initialize an empty Individual.
    public Individual(ArrayList<TestCase> testCases, int populationSize) {
        ArrayList<Chromosome> individualChromosome = new ArrayList<>(testCases.size());
        for (int i = 0; i <testCases.size(); i++ ){
            Chromosome chromosome = new Chromosome(testCases.get(i));
            individualChromosome.add(chromosome);
        }
        Collections.shuffle(individualChromosome);

        chromosomes = individualChromosome;
        fitness = -1;
        initFitness = fitness;

    }

     //initialize an empty Individual.
      public Individual() {

        chromosomes = new ArrayList<Chromosome>();
        fitness = -1;
        initFitness = fitness;

    }
    //initialize an empty Individual with specifiz test case size
    public Individual(int size) {
        chromosomes = new ArrayList<Chromosome>(size);
        fitness = -1;
        initFitness = fitness;
    }

    public ArrayList<Chromosome> getChromosomes() {
        return new ArrayList<Chromosome>(chromosomes);
    }

    public void setFitness(double fitnessValue) {
        fitness = fitnessValue;
    }


    public void setInitFitness(double fitnessValue) {
        initFitness = fitnessValue;
    }

    public double getFitness() {
        return fitness;
    }

    public double getInitFitness() {
        return initFitness;
    }

    public void addChromosome(Chromosome chromosome) {
        chromosomes.add(chromosome);
    }


    public Chromosome removeChromosome(int i) {
        return chromosomes.remove(i);
    }


    public Chromosome getChromosome(int i) {
        return chromosomes.get(i);
    }


    public void addChromosome(int index, Chromosome chromosome) {
        chromosomes.add(index,chromosome);
    }

    public Chromosome replaceChromosome(int index, Chromosome chromosome) {
        return chromosomes.set(index, chromosome);
    }

    public int size() {
        return chromosomes.size();
    }


    public void setAccumulatedFitness(double fitness) {
        accumulatedFitness = fitness;
    }

    public double getAccumulatedFitness() {
        return accumulatedFitness;
    }

    public static Individual copy(Individual individual) {
        ArrayList<Chromosome> newChromosomes = new ArrayList<Chromosome>();
        for (Chromosome chromosome : individual.getChromosomes()) {
            newChromosomes.add(Chromosome.copy(chromosome));
        }
        return new Individual(newChromosomes, individual.getFitness());
    }


    public int compareTo(Individual individual) {

        double fitness2 = individual.getFitness();

        if (fitness < fitness2)
            return -1;
        else if (fitness == fitness2)
            return 0;
        else
            return 1;

    }

    // to check duplicate teseCase. it prevents adding duplicate TestCase in crossover operator
    public boolean containsGene(int gene) {
        for (Chromosome chro : chromosomes) {
            if (chro.getId() == gene) {
                return true;
            }
        }
        return false;
    }



    public String toString() {
        String output = "";
        for (int geneIdx = 0;  geneIdx < this.chromosomes.size(); geneIdx++) {
            output += this.chromosomes.get(geneIdx).testCase.getId() +  "\n" ;
        }
        return output;
    }

    public double getProbability() {
        return probability;
    }

    public void setProbability(double probability) {
        this.probability = probability;
    }
}
