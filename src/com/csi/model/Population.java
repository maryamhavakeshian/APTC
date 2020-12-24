package com.csi.model;

import java.util.*;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class Population {
    private ArrayList<Individual> individuals;
    private int populationMaxSize;
    public Population(ArrayList<Individual> newIndividuals) {

        individuals = new ArrayList<Individual>(newIndividuals);
        populationMaxSize = individuals.size();

    }

    public Population(int populationmaxSize) {

        individuals = new ArrayList<Individual>();
        populationMaxSize = populationmaxSize;

    }
    //creates all the individuals of the population
    public Population(int populationSize,ArrayList<Chromosome> testCases) {
        ArrayList<Chromosome> temp=(ArrayList<Chromosome>)testCases.clone();
        this.individuals = new ArrayList<>();
        for (int IndividualCount=0; IndividualCount< populationSize ; IndividualCount++) {
           ArrayList<Chromosome> x=(ArrayList<Chromosome>)temp.clone();
            Collections.shuffle(x);
            this.individuals.add( new Individual(x));
        }

    }



    public ArrayList<Individual> getIndividuals() {
        return new ArrayList<Individual>(individuals);
    }
    public Individual getIndividual(int offset) {
        return individuals.get(offset);
    }
    public void addIndividual(Individual individual) {
        individuals.add(individual);
    }
    public double getMaxFitness() {

        return getBestIndividual().getFitness();

    }
    public double getMinFitness() {

        return getWorstIndividual().getFitness();

    }
    public int getPopulationSize() {
        return individuals.size();
    }
    public int getPopulationMaxSize() {
        return populationMaxSize;
    }
    public Individual removeIndividual(int indi) {
        return individuals.remove(indi);
    }
    public Individual getBestIndividual() {
        int bestIndividual = 0;
        double bestFitness = individuals.get(0).getFitness();
        double nextFitness;
        for (int i=1; i<individuals.size(); i++) {
            nextFitness = individuals.get(i).getFitness();
            if (nextFitness > bestFitness) {
                bestFitness = nextFitness;
                bestIndividual = i;

            }

        }

        return individuals.get(bestIndividual);

    }




    //compare individuals' fitness and sort them, so the first node of sorted array is the fittest
    public Individual getFittest(int offset) {
        // Order population by fitness
        Collections.sort(this.individuals, new Comparator<Individual>() {
            @Override
            public int compare(Individual o1, Individual o2) {
                if (o1.getFitness() > o2.getFitness()) {
                    return -1;
                } else if (o1.getFitness() < o2.getFitness()) {
                    return 1;
                }
                return 0;
            }
        });
        return individuals.get(offset);
    }


    public Individual getWorstIndividual() {

        int worstIndividual = 0;
        double worstFitness = individuals.get(0).getFitness();
        double nextFitness;

        for (int i=1; i<individuals.size(); i++) {

            nextFitness = individuals.get(i).getFitness();
            if (nextFitness < worstFitness) {

                worstFitness = nextFitness;
                worstIndividual = i;

            }

        }

        return individuals.get(worstIndividual);

    }
    public static Population copy(Population population) {

        ArrayList<Individual> newIndividuals
                = new ArrayList<Individual>();

        for (Individual individual: population.getIndividuals()) {

            newIndividuals.add(Individual.copy(individual));

        }

        return new Population(newIndividuals);

    }

    public void setIndividual(int offset, Individual individual) { //TODO I am not sure here
        individuals.add(offset,individual);
    }

    private static Population initialPopulation;





       public  Population sortByFitness(Population population){

           Collections.sort(this.individuals, new Comparator<Individual>() {
               @Override
               public int compare(Individual o1, Individual o2) {
                   if (o1.getFitness() > o2.getFitness()) {
                       return -1;
                   } else if (o1.getFitness() < o2.getFitness()) {
                       return 1;
                   }
                   return 0;
               }
           });



        return new Population(this.individuals);

    }


    public double calculateAccumulatedFitness(Population population) {

        ArrayList<Individual> currentIndividual= population.getIndividuals();
        double accumulatedFitness = 0.0;
        double currentFitness;

        for (Individual individual: currentIndividual) {
            currentFitness = individual.getFitness();
            accumulatedFitness += currentFitness;
            individual.setAccumulatedFitness(accumulatedFitness);
        }

        return accumulatedFitness;

    }
    
    

}
