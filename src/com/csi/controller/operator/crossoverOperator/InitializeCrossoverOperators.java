package com.csi.controller.operator.crossoverOperator;


/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class InitializeCrossoverOperators {



    public static CrossOver crossoverType (int crossoverChoice) {
        CrossOver crossOver;
        switch (crossoverChoice) {
            case 0: crossOver = new PartiallyMappedCO();
            break;
            case 1: crossOver = new OrderCO();
            break;
            case 2: crossOver = new MaximalPresentativeCO();
            break;
            case 3: crossOver = new PositionBasedCO();
            break;
            default: System.out.println ("The entered crossover operator " +crossoverChoice+" is not valid operator!");
                crossOver = null;
                System.exit(1);
                break;
        }

        return crossOver;

    }



}
