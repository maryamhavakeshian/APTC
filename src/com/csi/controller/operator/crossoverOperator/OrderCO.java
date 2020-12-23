package com.csi.controller.operator.crossoverOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class OrderCO extends CrossOver {


    private static  final int PROBLEM_PORTION = 4;//devide the problem size into four parts
    private int subtourLength; //define randomSubtour with respect to size of problem
    public OrderCO() {
        super();
    }

    public ArrayList<Individual> executeCO (Individual firstParent, Individual secondParent) {
        ArrayList<Individual> offSpringList = new ArrayList<Individual>();
        int firstCuttingPoint, secondCuttingPoint, fistOffspringPosition, secondOffspringPosition;
        int testsuitLenght = firstParent.size();


        subtourLength = randomValue.nextInt(testsuitLenght/PROBLEM_PORTION+1) + testsuitLenght/PROBLEM_PORTION; //  a random subtour less than testsuiteLenght/2

        HashMap<Integer,Integer> firstOffspringTestcases = new HashMap<Integer,Integer> ();
        HashMap<Integer,Integer> secondOffspringTestCases = new HashMap<Integer,Integer> ();

        Individual firstOffspring = new Individual(testsuitLenght);
        Individual secondOffspring = new Individual(testsuitLenght);

        TestCase emptyTestCase = new TestCase(-1);
        Chromosome emptyChromosome = new Chromosome(emptyTestCase);
        Chromosome nextChromfirst, nextChromSecond;

       for (int i=0; i<testsuitLenght; i++) {
            firstOffspring.addChromosome(emptyChromosome); // initilize individuals with empty test cases
            secondOffspring.addChromosome(emptyChromosome);
        }

        // the position from which the parents subtour is started
        firstCuttingPoint = randomValue.nextInt(testsuitLenght-subtourLength);
       //the position at which the parent subtour is ended
        secondCuttingPoint = firstCuttingPoint + subtourLength;

        for (int i=0; i<subtourLength; i++) {

            nextChromfirst = firstParent.getChromosome(firstCuttingPoint+i);
            nextChromSecond = secondParent.getChromosome(firstCuttingPoint+i);
            // fill the offspring with the subtour of their opposite parents
            firstOffspring.replaceChromosome(firstCuttingPoint+i, nextChromfirst);
            secondOffspring.replaceChromosome(firstCuttingPoint+i, nextChromSecond);

            firstOffspringTestcases.put(nextChromfirst.getId(), firstCuttingPoint+i);
            secondOffspringTestCases.put(nextChromSecond.getId(), firstCuttingPoint+i);

        }


        //insert the remaining chromosoms with the non-duplicated chromosomes of other parent
        fistOffspringPosition = secondCuttingPoint;
        secondOffspringPosition = secondCuttingPoint;

        for (int i=0; i<testsuitLenght; i++) {
            //(secondCuttingPoint+i)%testsuitLenght checks the end of test suite: if we reach the end of test suite, we should com
            //back to the first test cases of the test suite
            nextChromfirst = secondParent.getChromosome((secondCuttingPoint+i)%testsuitLenght);
            nextChromSecond = firstParent.getChromosome((secondCuttingPoint+i)%testsuitLenght);

            // check for duplicated testcases in the first offspring to avoid duplicate entry
            if (!firstOffspringTestcases.containsKey(nextChromfirst.getId())) {
                firstOffspring.replaceChromosome(fistOffspringPosition % testsuitLenght, nextChromfirst);
                fistOffspringPosition++;
            }

            // check for duplicated testcases in the second offspring to avoid duplicate entry
            if (!secondOffspringTestCases.containsKey (nextChromSecond.getId())) {
                secondOffspring.replaceChromosome(secondOffspringPosition % testsuitLenght, nextChromSecond);
                secondOffspringPosition++;

            }

        }

        offSpringList.add(firstOffspring);
        offSpringList.add(secondOffspring);
        return offSpringList;

    }




}
