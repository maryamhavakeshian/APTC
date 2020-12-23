package com.csi.controller.operator.mutationOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class InsertionMu extends Mutation {

    public Individual mutateIndividual(Individual _individual) {

        int length = _individual.size();
        Individual individual = new Individual(_individual.getChromosomes());
        Chromosome tempChrom = individual.removeChromosome (randomValue.nextInt(length));
        individual.addChromosome(randomValue.nextInt(length-1),tempChrom);

        return individual;

    }

}
