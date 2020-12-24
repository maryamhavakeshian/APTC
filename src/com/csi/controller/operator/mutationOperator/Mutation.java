package com.csi.controller.operator.mutationOperator;

import com.csi.model.Individual;
import java.util.Random;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public abstract class Mutation {
    public Random randomValue;
    public Mutation() {
        randomValue = new Random();
    }
    public abstract Individual mutateIndividual(Individual parentIndividual);
}
