package com.csi.controller.operator.mutationOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class ExchangeMu extends Mutation{



    public ExchangeMu() {
        super();
    }

    public Individual mutateIndividual(Individual parentIndividual) {

        Chromosome temporaryChromosome;
        int firstExcahnePosition, secondexcangePosition, length;

        length = parentIndividual.size();
        Individual individual  = new Individual(parentIndividual.getChromosomes());

            firstExcahnePosition = randomValue.nextInt(length);
            do {
                secondexcangePosition = randomValue.nextInt(length);
            } while (firstExcahnePosition == secondexcangePosition);

            temporaryChromosome = individual.getChromosome(firstExcahnePosition); //exchange the test cases of the selected positions
            individual.replaceChromosome (firstExcahnePosition, individual.getChromosome(secondexcangePosition));
            individual.replaceChromosome(secondexcangePosition, temporaryChromosome);



        return individual;

    }

}
