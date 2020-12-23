package com.csi.controller.operator.crossoverOperator;

import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.TestCase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by m_havakesheyan on 12/6/2020.
 */
public class PositionBasedCO extends CrossOver {


    private static  final int PROBLEM_PORTION = 4;//devide the problem size into four parts
    private int spots;



    public PositionBasedCO() {
        super();
    }

    public ArrayList<Individual> executeCO(Individual firstParent, Individual secondParent) {
        ArrayList<Individual> offSpringList = new ArrayList<Individual>(2);
        int testsuiteLength = firstParent.size();
        //incontrast to other crossovers, spots is the counts of postions, not the subtourlenght
        spots = randomValue.nextInt(testsuiteLength/PROBLEM_PORTION+1) + testsuiteLength/PROBLEM_PORTION;

        // HashMaps store mapping of testcase id, and its porsiton
        HashMap<Integer,Integer> firstOffspringChromosomes = new HashMap<Integer,Integer> ();
        HashMap<Integer,Integer> secondOffsrignChromosomes = new HashMap<Integer,Integer> ();

        Individual firstIndividual = new Individual(testsuiteLength);
        Individual secondIndividual = new Individual(testsuiteLength);

        TestCase emptyTestCase = new TestCase(-1);

        Chromosome emptyChromosome = new Chromosome(emptyTestCase);
        Chromosome firstNextChromosome, secondNextChromosome;

        int nextChromIndex, firstNextChromosomeIndex, secondNextChromosomeIndex;
        for (int i=0; i<testsuiteLength; i++) {
            firstIndividual.addChromosome(emptyChromosome); // initilize individuals with empty test cases
            secondIndividual.addChromosome(emptyChromosome);

        }
        for (int i = 0; i< spots; i++) { // create random postions  for spots time

            // make sure that the index has not already been 
            //  used for a position
            do {
                nextChromIndex = randomValue.nextInt(testsuiteLength);
            } while (firstOffspringChromosomes.containsValue(nextChromIndex));//check duplicate test case

            //get the chromosome of randomized position of the first parent
            firstNextChromosome = firstParent.getChromosome(nextChromIndex);
            //get the chromosome of randomized position of the second parent
            secondNextChromosome = secondParent.getChromosome(nextChromIndex);

            // add the chromosome at the random posititon of second parnet ot the first offspring
            firstIndividual.replaceChromosome(nextChromIndex, secondNextChromosome);
            // add the chromosome at the random posititon of first parnet ot the second offspring
            secondIndividual.replaceChromosome(nextChromIndex, firstNextChromosome);

            //keep the index to avoid duplicate elements
            firstOffspringChromosomes.put(secondNextChromosome.getId(),nextChromIndex);
            secondOffsrignChromosomes.put(firstNextChromosome.getId(), nextChromIndex);


        }

        firstNextChromosomeIndex = 0;
        secondNextChromosomeIndex = 0;
        for (int i=0; i<testsuiteLength; i++) {
           // fill the empty spots of each offspring with it non-duplicated elements of its correspnding parent
            firstNextChromosome = firstParent.getChromosome(i);
            secondNextChromosome = secondParent.getChromosome(i);

            //check to see if the choromose is used in the offspring
            if (!firstOffspringChromosomes.containsKey(firstNextChromosome.getId())) {
                while (firstOffspringChromosomes.containsValue(firstNextChromosomeIndex)) {//check for duplicate elements
                    firstNextChromosomeIndex++;
                }

                //the chromosome is not used in the offsprig so it is added to offspring, and the index is increased for next
                //chromosome
                firstIndividual.replaceChromosome(firstNextChromosomeIndex, firstNextChromosome);
                firstOffspringChromosomes.put(firstNextChromosome.getId(), firstNextChromosomeIndex);
                firstNextChromosomeIndex++;

            }
             //**** the same for the seconf offsping***///
            if (!secondOffsrignChromosomes.containsKey(secondNextChromosome.getId())) {
                while (secondOffsrignChromosomes.containsValue(secondNextChromosomeIndex)) {
                    secondNextChromosomeIndex++;
                }
                secondIndividual.replaceChromosome(secondNextChromosomeIndex, secondNextChromosome);
                secondOffsrignChromosomes.put (secondNextChromosome.getId(), secondNextChromosomeIndex);
                secondNextChromosomeIndex++;

            }

        }



        offSpringList.add(firstIndividual);
        offSpringList.add(secondIndividual);
        return offSpringList;

    }




}
