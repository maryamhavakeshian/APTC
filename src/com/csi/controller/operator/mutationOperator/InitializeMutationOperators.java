package com.csi.controller.operator.mutationOperator;


import java.util.ArrayList;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class InitializeMutationOperators {
    public static Mutation getMutationType (int mutationChoice) {

        Mutation mutationOperator;
        switch (mutationChoice) {
            case 0: mutationOperator  = new InsertionMu();
            break;
            case 1: mutationOperator  = new ExchangeMu();
            break;
            case 2: mutationOperator  = new DisplacementMu();
            break;
            default: System.out.println("The entered mutation operator " +mutationChoice+" is not valid operator!");
                mutationOperator = null;
                System.exit(1);
                break;
        }

        return mutationOperator;

    }

    public static ArrayList<Mutation>
    getMutationOperators(int[] ops, int[] seeds) {

        ArrayList<Mutation> mutations =
                new ArrayList<Mutation>(ops.length);

        for (int i=0; i<ops.length; i++) {

            mutations.add(getMutationType(ops[i]));

        }

        return mutations;

    }

}
