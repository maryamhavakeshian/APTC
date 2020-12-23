package com.csi.controller.selector;

import com.csi.model.Individual;
import com.csi.model.Population;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class TournamentPS extends ParentSelector {


    private int tournamentLenght;



    public TournamentPS() {
        super();
        tournamentLenght = 2;
    }

    public Individual selectParent(Population population) {
        //create a population of individuals; the size of population is tournamentSize
        Population tournament = new Population(this.tournamentLenght);
        // not to choose the same individual twice
        Collections.shuffle(population.getIndividuals());
        for (int i = 0; i < this.tournamentLenght; i++) {
            //randomly select the individulas of a population
            int individualIdx= 	new Random().nextInt(population.getPopulationSize());
            Individual tournamentIndividual = population.getIndividual(individualIdx);
            tournament.setIndividual(i, tournamentIndividual);
        }
        // the getFittest() function compares the fitness of the individuals and sorts them
        // the first elemet is the fittest
        return tournament.getFittest(0);
    }

}
