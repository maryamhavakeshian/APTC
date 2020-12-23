package com.csi.controller.selector;

import com.csi.model.Individual;
import com.csi.model.Population;

import java.util.ArrayList;
import java.util.Collections;

public class RouletteWheelLinearRankingPS extends ParentSelector {


    static final double sValue =  1.5;


    public RouletteWheelLinearRankingPS() {
        super();
    }

    public Individual selectParent(Population population) {
        computeProbability(population);
        return rouletteWheelSelection(population);
    }


    private Individual rouletteWheelSelection(Population population) {
        double rouletteWheelPosition;
        double currentFitness;
        int currentIndividual;
        double totalFitness = population.calculateAccumulatedFitness(population);
        Collections.shuffle(population.getIndividuals());
        ArrayList<Individual> individuals = population.getIndividuals();

        // select a  probability of roulettewheel position
        rouletteWheelPosition = randomValue.nextDouble() * totalFitness;
        currentIndividual = 0;
        currentFitness = 0;
        // find the roulette position
        while (currentFitness < rouletteWheelPosition) {
            currentFitness = individuals.get(currentIndividual).getAccumulatedFitness();
            if (currentFitness > rouletteWheelPosition) {
                try {
                    if (individuals.get(currentIndividual - 1).getAccumulatedFitness() > rouletteWheelPosition) {
                        currentIndividual--;
                        currentFitness = individuals.get(currentIndividual).getAccumulatedFitness();
                    }
                } catch (IndexOutOfBoundsException ex) {
                }
            }
            if (currentIndividual + 2 < individuals.size()) {
                currentIndividual += 2;
            } else {
                currentIndividual++;
            }
        }

        if (currentIndividual >= 2)
            currentIndividual -= 2;
        Individual individual = individuals.get(currentIndividual);
        individual.setFitness(individual.getProbability());
        return individual;
    }


    private void computeProbability(Population population) {

        double tmpvValue = ((2 - sValue) / population.getPopulationSize());
        double sumRank = 0;
        for (int i = 1; i < population.getPopulationSize() + 1; i++) {
            sumRank = sumRank + i;
        }

        Individual rankedIndividual = new Individual();
        double linearProbabilty;

        Population rankedPopulation = population.sortByFitness(population);
        int populationSize = rankedPopulation.getPopulationSize();

        for (int populationIdx = 0; populationIdx < populationSize; populationIdx++) {
            rankedIndividual = rankedPopulation.getIndividual(populationIdx);
            linearProbabilty = tmpvValue + (((populationSize - populationIdx) * (sValue - 1)) / (sumRank));
            rankedIndividual.setProbability(rankedIndividual.getFitness());
            rankedIndividual.setFitness(linearProbabilty);
        }


    }


}
