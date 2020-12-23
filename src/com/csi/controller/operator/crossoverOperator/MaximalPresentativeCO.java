package com.csi.controller.operator.crossoverOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class MaximalPresentativeCO extends CrossOver {

    private static  final int PROBLEM_PORTION = 4;//devide the problem size into four parts
    private int subtourLength; //define randomSubtour with respect to size of problem
    public MaximalPresentativeCO() {
        super();

    }

    @Override
    public ArrayList<Individual> executeCO(Individual recievedfirstParent, Individual recievedsecondParent) {
        ArrayList<Individual>  offspringList =new ArrayList<>();
        offspringList.add(executeMaximalPresentative(recievedfirstParent,recievedsecondParent));
        offspringList.add(executeMaximalPresentative(recievedsecondParent,recievedfirstParent));
        return offspringList;
    }


    public Individual executeMaximalPresentative(Individual firstParent, Individual secondParent) {

        int testsuitLenght = firstParent.size();
        subtourLength = randomValue.nextInt(testsuitLenght/PROBLEM_PORTION+1) + testsuitLenght/PROBLEM_PORTION;

        HashMap<Integer,Integer> offspringChromosomes = new HashMap<Integer,Integer> ();
        int cuttingPoint;
        Individual offsring = new Individual();
        Chromosome nextChromosome;
        // add the subtour of the first parent at the befining of the offspring
        cuttingPoint = randomValue.nextInt(testsuitLenght-subtourLength);
        for (int i=cuttingPoint; i<cuttingPoint+subtourLength; i++) {
            nextChromosome = firstParent.getChromosome(i);
            offsring.addChromosome(nextChromosome);
            offspringChromosomes.put(nextChromosome.getId(), i);

        }

        // fill the empty spots of the offspring from the second parent
        for (int i=0; i<testsuitLenght; i++) {

            nextChromosome = secondParent.getChromosome(i);
            if (!offspringChromosomes.containsKey(nextChromosome.getId())) {   //check and fill the hashmap to avoid duplicate test cases
                offsring.addChromosome(nextChromosome);
                offspringChromosomes.put(nextChromosome.getId(), i);

            }

        }

       return offsring;
        

    }


}
