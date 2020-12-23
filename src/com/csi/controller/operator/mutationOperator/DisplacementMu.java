package com.csi.controller.operator.mutationOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;

import java.util.ArrayList;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class DisplacementMu extends Mutation {


    private int subtourLength;



    public DisplacementMu() {
        super();
    }

    public Individual mutateIndividual(Individual parentIndividual) {
        int firstCuttingPoint, insert;
        int length = parentIndividual.size();
        Individual individual= new Individual(parentIndividual.getChromosomes());
        subtourLength = length / 4 + randomValue.nextInt(length / 4 + 1);
        ArrayList<Chromosome> subtour = new ArrayList<Chromosome>(subtourLength);



        firstCuttingPoint = randomValue.nextInt(length-subtourLength);
        for (int i=0; i<subtourLength; i++) {
            subtour.add(0, individual.removeChromosome(firstCuttingPoint));
        }


        insert = randomValue.nextInt(individual.size());
        //insert the sub tour in the random inserion position
        for (Chromosome chromosome: subtour) {
            individual.addChromosome(insert, chromosome);
        }

        return individual;

    }

}
