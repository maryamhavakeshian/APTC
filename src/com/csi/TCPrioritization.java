package com.csi;

import com.csi.util.Configuration;

/** com.csi.TCPrioritization
 *
 * Created by m_havakesheyan on 12/9/2020.
 */
public class TCPrioritization {


    public static void main(String[] args) {
        Configuration configuration;
        if (valiateInput(args)) {
            configuration = new Configuration(args);
        } else {
            String[] inputParmeters = {args[0], "0", "3", "1"};
            configuration = new Configuration(inputParmeters);
        }
        configuration.initializeDataCoverage();
        executeTCPPrioritzer(configuration);

    }
    public static void executeTCPPrioritzer (Configuration configuration) {

        GeneticAlgorithm geneticAlgorithm;
        RandomRestartHillClimbing hillClimber;
        RandomSearch randomSearch;

        for(int rep=0;rep <1; rep++) {

//******************run genetic algorithm**********************************************************

            geneticAlgorithm = new GeneticAlgorithm(configuration,rep);
            geneticAlgorithm.executeTCPriotizer();

//******************run random search**********************************************************

//            randomSearch = new RandomSearch(configuration,rep);
//            randomSearch.randomSearchEngine();

//******************run hill climber**********************************************************

//            hillClimber = new RandomRestartHillClimbing(configuration, rep);
//            hillClimber.hillClimber();

        }
//******************create data set**********************************************************

// if you want to check synthesized data generation, uncomment bellow lines
//        GenerateRandomizedDataSet generateRandomizedDataSet = new GenerateRandomizedDataSet();
//        generateRandomizedDataSet.generateDataset();

    }



    public static boolean valiateInput(String[] inputParameters) {
        boolean isValidated ;

        if (inputParameters.length != INPUT_ARGUMENT_COUNT) {
            System.out.println("Insufficient arguments " + "You need to provide:" + "\n" + HELP_COMMAND + "\n" +
                    " we run it with default setting: ParnetSelectorOperator is tournament, CrossoverOperator is PositionBasedCo,  and MutationOperator is InsertionMu");
            isValidated = false;

        } else isValidated = true;

        return isValidated;
    }

    final static int INPUT_ARGUMENT_COUNT = 4;
    public static final String HELP_COMMAND =   "coverage_data_file(String)"+"mutation_operator(integer)   crossover_operator(integer)  "  +"selection_method(integer) "   ;
}
