package com.csi.controller.operator.crossoverOperator;

import com.csi.model.Individual;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public abstract class CrossOver {

    public Random randomValue;

    public CrossOver() {
        randomValue = new Random();
    }

    public abstract ArrayList<Individual> executeCO (Individual firstParent, Individual secondParent);
    public ArrayList<Individual> executeCrossOver(Individual firstParent,Individual secondParent) {
           return  executeCO(firstParent,secondParent);
    }

}
