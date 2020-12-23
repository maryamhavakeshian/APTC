package com.csi.controller.operator.crossoverOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

public class PartiallyMappedCO extends CrossOver {

    private static final int PROBLEM_PORTION = 4;//devide the problem size into four parts
    private int subtourLength;//define randomSubtour with respect to size of problem

    public PartiallyMappedCO() {
        super();

    }

    public ArrayList<Individual> executeCO(Individual firstParent, Individual secondParent) {
        ArrayList<Individual> offspringList  = new ArrayList<Individual>();
        int firstCuttingPoint, secondCuttingPoint, tempIndex;
        int testsuitelenght = firstParent.size();
        subtourLength = randomValue.nextInt(testsuitelenght / PROBLEM_PORTION + 1) + testsuitelenght / PROBLEM_PORTION;

        // the hashmaps ceat a map between test cases ids(keys) and their position (values), which helps to
        //to define prevent duplicated entries
        HashMap<Integer, Integer> firstOffspringTestcases = new HashMap<Integer, Integer>();
        HashMap<Integer, Integer> secondOffspringTestCases = new HashMap<Integer, Integer>();

        Individual firstOffspring = new Individual(testsuitelenght);
        Individual secondOffspring = new Individual(testsuitelenght);
        Chromosome firstChromosome, secondChromosme;

        TestCase emptyTestcase = new TestCase(-1);
        Chromosome emptyChromosome = new Chromosome(emptyTestcase);


        for (int i = 0; i < testsuitelenght; i++) { //// initilize individuals with empty test cases
            firstOffspring.addChromosome(emptyChromosome);
            secondOffspring.addChromosome(emptyChromosome);
        }

        // the position from which the parents subtour is started
        firstCuttingPoint = randomValue.nextInt(testsuitelenght - subtourLength);
        //the position at which the parent subtour is ended
        secondCuttingPoint = firstCuttingPoint + subtourLength;


        for (int i = 0; i < subtourLength; i++) {
            firstChromosome = Chromosome.copy(firstParent.getChromosome(firstCuttingPoint + i));
            secondChromosme = Chromosome.copy(secondParent.getChromosome(firstCuttingPoint + i));
            //keep a map between two sub tour of the two parents; test case ids is the key of map and their position is it value
            firstOffspringTestcases.put(secondChromosme.getId(), firstCuttingPoint + i);
            secondOffspringTestCases.put(firstChromosome.getId(), firstCuttingPoint + i);
            // fill the offspring with the subtour of their opposite parents
            firstOffspring.replaceChromosome(firstCuttingPoint + i, secondChromosme);
            secondOffspring.replaceChromosome(firstCuttingPoint + i, firstChromosome);
        }

        // #####add non-duplicated elements of the corresponding parent from the beggining=0 to the first cutting point position###//
        if (firstCuttingPoint > 0) {
            for (int i = 0; i < firstCuttingPoint; i++) {
                firstChromosome = firstParent.getChromosome(i);
                secondChromosme = secondParent.getChromosome(i);
                // if the  first hashmap contians the test cases id (the key of the hashmap),it means that the first
                // offspring has duplicated value so  we find its mapping chromosome
                //from the  parent to replace the duplicated chromosome
                if (firstOffspringTestcases.containsKey(firstChromosome.getId())) {//the keys of map is test case ids
                    do {
                        tempIndex = firstOffspringTestcases.get(firstChromosome.getId());
                        firstChromosome = firstParent.getChromosome(tempIndex);
                    } while (firstOffspringTestcases.containsKey(firstChromosome.getId()));
                    firstOffspring.replaceChromosome(i, firstChromosome);
                } else {//thers is no duplicatd entry, and the chromosome is added directly
                    firstOffspring.replaceChromosome(i, firstChromosome);
                }
                //******************apply the same scenarion for the secondOffspringTestCases hashmap**********//

                if (secondOffspringTestCases.containsKey(secondChromosme.getId())) {
                    do {
                        tempIndex = secondOffspringTestCases.get(secondChromosme.getId());
                        secondChromosme = secondParent.getChromosome(tempIndex);
                    } while (secondOffspringTestCases.containsKey(secondChromosme.getId()));
                    secondOffspring.replaceChromosome(i, secondChromosme);

                } else {
                    secondOffspring.replaceChromosome(i, secondChromosme);
                }

            }

        }

        // #####add non-duplicated elements of the corresponding parent from the secondCuttingpoint to the end of testsuites ###//
        if (secondCuttingPoint < testsuitelenght) {

            for (int i = secondCuttingPoint; i < testsuitelenght; i++) {

                firstChromosome = firstParent.getChromosome(i);
                secondChromosme = secondParent.getChromosome(i);
                // if the  first hashmap contians the test cases id (the key of the hashmap),it means that the first
                // offspring has duplicated value so  we find its mapping chromosome
                //from the  parent to replace the duplicated chromosome
                if (firstOffspringTestcases.containsKey(firstChromosome.getId())) {//the keys of map is test case ids
                    do {
                        tempIndex = firstOffspringTestcases.get(firstChromosome.getId());
                        firstChromosome = firstParent.getChromosome(tempIndex);

                    } while (firstOffspringTestcases.containsKey(firstChromosome.getId()));
                    firstOffspring.replaceChromosome(i, firstChromosome);
                }
                else {//thers is no duplicatd entry, and the chromosome is added directly
                    firstOffspring.replaceChromosome(i, firstChromosome);
                }

                //******************apply the same scenarion for the secondOffspringTestCases hashmap**********//
               if (secondOffspringTestCases.containsKey(secondChromosme.getId())) {
                    do {
                        tempIndex = secondOffspringTestCases.get(secondChromosme.getId());
                        secondChromosme = secondParent.getChromosome(tempIndex);
                    } while (secondOffspringTestCases.containsKey(secondChromosme.getId()));
                    secondOffspring.replaceChromosome(i, secondChromosme);
                } else {
                    secondOffspring.replaceChromosome(i, secondChromosme);
                }

            }

        }

        offspringList.add(firstOffspring);
        offspringList.add(secondOffspring);
        return offspringList;

    }


}