package com.csi;

import com.csi.controller.fitnessComputation.ObjectiveEvaluator;
import com.csi.controller.operator.crossoverOperator.CrossOver;
import com.csi.controller.operator.crossoverOperator.InitializeCrossoverOperators;
import com.csi.controller.operator.mutationOperator.InitializeMutationOperators;
import com.csi.controller.operator.mutationOperator.Mutation;
import com.csi.controller.selector.InitializeParentSelector;
import com.csi.controller.selector.ParentSelector;
import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.Population;
import com.csi.util.CSVUtils;
import com.csi.util.Configuration;
import com.csi.util.initialization.GenerateRandomizedPopulation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * Created by m_havakesheyan on 12/9/2020.
 */
public class GeneticAlgorithm {

    private double mutationRate,crossoverRate;
    private int elitismCount,populationSize,testCaseTotalCount,  testCaseCoveragePointCount,tournamentSize;


    int repetition, generations;
    double maximumExecutionTime;               //targetFitness,
    long startTime, runTime;
    Random randomValue;

    Configuration configuration;
    ParentSelector parentSelectorType;
    CrossOver crossoverType;
    Mutation mutationType;
    ObjectiveEvaluator fitnessEvaluator;
    Population population;



    ArrayList<Chromosome> initChromosomes; //TODO rename initialIndividual

    Individual bestIndividual;



    public GeneticAlgorithm(Configuration configurationProperties, int numberofRepetition) {

        this.configuration = configurationProperties;
        this.populationSize = configuration.getPopulationSize();
        this.mutationRate = (1.0/configuration.getTestcase().size());//configuration.getMutationRate();//
         this.crossoverRate = configuration.getCrossOverRate();
        this.elitismCount = configuration.getElitismCount();
        this.tournamentSize = configuration.getTournamentSize();
        this.testCaseCoveragePointCount = configuration.getTestCaseCoveragePointCount();
        this.testCaseTotalCount = configuration.getTestCaseTotalCount();

        this.repetition = numberofRepetition;

        this.maximumExecutionTime = configuration.getMaximumExecutionTime();
        this.randomValue = new Random();
        this.initChromosomes =GenerateRandomizedPopulation.createChromosomes (this.configuration.getTestcase());//  less randomized : new Individual(this.configuration.getTestcase(),populationSize).getChromosomes(); //
        Collections.shuffle(this.initChromosomes);
        this.population  = GenerateRandomizedPopulation.createPopulation(this.initChromosomes, this.configuration.getPopulationSize(), new Random().nextLong());// //less randomized :new Population(this.populationSize, this.initChromosomes);
        this.crossoverType = InitializeCrossoverOperators.crossoverType(configuration.getCrossoverOperator());
        this.mutationType = InitializeMutationOperators .getMutationType(configuration.getMutationOperator());
        this.fitnessEvaluator = new ObjectiveEvaluator();
        this.fitnessEvaluator.evaluatePopulation(this.population,testCaseTotalCount,testCaseCoveragePointCount);
        this.parentSelectorType = InitializeParentSelector.getParentSelector(this.configuration.getSelectionOperator());

    }

    public Individual executeTCPriotizer() {

        double bestFitness = 0;
        double tempFitness;
        Individual bestIndividual =null;
        startTime = System.currentTimeMillis();
        Population currentPopulation, offspringPopulation;

        fitnessEvaluator.evaluatePopulation(population,testCaseTotalCount,testCaseCoveragePointCount);
        bestIndividual = population.getBestIndividual();

        while (!isTerminationConditionMet(maximumExecutionTime,startTime) ) {
            generations++;
            currentPopulation= Population.copy(this.population);
            // select a subpopulation of parents
            offspringPopulation = crossoverPopulation(currentPopulation);
            offspringPopulation = mutatePopulation(offspringPopulation);
            fitnessEvaluator.evaluatePopulation(offspringPopulation, testCaseTotalCount,testCaseCoveragePointCount );
            population = offspringPopulation;
            bestIndividual = findBestIndividual(population,bestIndividual);
            tempFitness = population.getMaxFitness();
            if (tempFitness >bestFitness) {
                bestIndividual = population.getBestIndividual();
                bestFitness = tempFitness;
            }
            //un comment to show the fiteness progress in the cvs file
            //CSVUtils.showOutput(generations, System.currentTimeMillis()-startTime, bestIndividual,repetition+"outGA.csv");
        }
        runTime = System.currentTimeMillis()-startTime;

        // this should be uncommented for multiple runs
        System.out.println("best fitness: "+bestFitness);
        try {
            CSVUtils.writeResultToFile("The Permutation of test cases: "+  "\r\n" + bestIndividual.toString());
        }catch (FileNotFoundException ex)	{
            System.out.println("Destination File Cannot be Openned!  The process cannot access the file because it is being used by another process");
            System.exit(1);

        } catch (IOException e) {
            System.out.println("Destination File Cannot be Openned! ");
            System.exit(1);
        }

        return bestIndividual;

    }


    public Population intitializePopulation(ArrayList<Chromosome> testcases) {
        //initializing randomized individuals in the constructor of population
        Population population = new Population(this.populationSize, testcases);
        return population;
    }

    //if we want to target number of generation for termination condition, we should use this method
    public boolean isTerminationConditionMet(int generationsCount, int maxGenerations) {
        return (generationsCount > maxGenerations);
    }
    public boolean isTerminationConditionMet(double maxTime, double startTime) {
        return (System.currentTimeMillis()- startTime > maxTime);
    }

    public Population crossoverPopulation(Population population){

        Population newPopulation = new Population(population.getPopulationSize());

        for (int populationIndex = 0; populationIndex < population.getPopulationSize()  ; populationIndex += 2) {
            //initialize empty individuals
            Individual frstParent= new Individual(population.getIndividual(populationIndex).getChromosomes().size());
            Individual scndParent= new Individual(population.getIndividual(populationIndex).getChromosomes().size());
            //control number of parents should be considered as elite
            boolean isElitFrstParent=false;
            boolean isElitScndParent=false;

            if (populationIndex < this.elitismCount - 1) {
                frstParent = population.getFittest(populationIndex);
                scndParent = population.getFittest(populationIndex + 1);
                isElitFrstParent = true;
                isElitScndParent = true;
            } else if (populationIndex == this.elitismCount - 1) {
                //only first parent can be considered as elite
                frstParent = population.getFittest(populationIndex);
                 scndParent = parentSelectorType.selectParent(population);
                isElitFrstParent=true;
                isElitScndParent=false;
            } else if (populationIndex >= this.elitismCount) {
                //the two parents participate in recombination process
                frstParent  = parentSelectorType.selectParent(population);
                scndParent  = parentSelectorType.selectParent(population);
                isElitFrstParent=false;
                isElitScndParent=false;

            }

            if (this.crossoverRate > Math.random()) {

                List<Individual> newOffsprings= crossoverType.executeCrossOver(frstParent, scndParent);
                //add elite or crossoverType parents
                Individual frstOffspring = isElitFrstParent? frstParent:  newOffsprings.get(0);
                Individual scndOffSpring = isElitScndParent? scndParent:  newOffsprings.get(1);

                //add crossovered offspring
                newPopulation.setIndividual(populationIndex, frstOffspring);
                //handle last round of odd populations
                if ( populationIndex<population.getPopulationSize()-1) {
                    newPopulation.setIndividual(populationIndex+1, scndOffSpring);
                }
            } else {
                //crossoverType possibility is not high enough for crossoverType operation,
                // so the selected parent add to the next populatio
                newPopulation.setIndividual(populationIndex, frstParent);
                if ( populationIndex<population.getPopulationSize()-1) {
                    newPopulation.setIndividual(populationIndex+1, scndParent);
                }
            }

        }

        return newPopulation;
    }



    public Population mutatePopulation(Population population){

        Population newPopulation = new Population(this.populationSize);

        for (int populationIndex = 0; populationIndex < population.getPopulationSize(); populationIndex += 2) {
            Individual offSpring1 = population.getFittest(populationIndex);
            if (populationIndex >= this.elitismCount)
                if (this.mutationRate > Math.random())
                    offSpring1 = mutationType.mutateIndividual( offSpring1);
            newPopulation.setIndividual(populationIndex, offSpring1);
            // handle last round of odd populations
            if (populationIndex < population.getPopulationSize() - 1) {
                Individual offSpring2 = population.getFittest(populationIndex + 1);
                if (populationIndex+1 >= this.elitismCount)
                    if (this.mutationRate > Math.random())
                        offSpring2 = mutationType.mutateIndividual(offSpring2);
                newPopulation.setIndividual(populationIndex + 1, offSpring2);
            }

        }

        return newPopulation;
    }

    // compare the fitness of the fittest individual of a population and the best individual from the past generations
    public Individual findBestIndividual(Population population, Individual bestIndividual)  {
        if (bestIndividual==null)
            return population.getFittest(0);
        else{
            Individual bestIndividualInPopualtion = population.getFittest(0);
            double bestIndividualfitness= bestIndividual.getFitness();
            double bestIndividualInPopualtionFitness= bestIndividualInPopualtion.getFitness();
            return bestIndividualInPopualtionFitness>bestIndividualfitness?bestIndividualInPopualtion:bestIndividual;

        }


    }





///these two methods works to create randomized  population. but they are less ranodimized method

// new Population(this.populationSize, this.initChromosomes);//creatPopulation(this.initChromosomes,this.populationSize);//

//new Individual(this.configuration.getTestcase(),populationSize).getChromosomes();//

}
