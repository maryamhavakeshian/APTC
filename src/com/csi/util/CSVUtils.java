package com.csi.util;

import com.csi.controller.fitnessComputation.ObjectiveEvaluator;
import com.csi.model.Chromosome;
import com.csi.model.Individual;
import com.csi.model.Population;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class CSVUtils {
    public static final String OUTPUT_FILE_NAME= "solution.csv";

    public static void showOutput( int generations,double elapsedTime,Individual bestIndividual, String fileName) {

        BufferedWriter out;
        if (bestIndividual == null) {
            System.out.println("BAD CHOICE");
            System.exit(1);
        }

        String toFile =  generations + "," + elapsedTime + "," + bestIndividual.getFitness() + "\n";

        try {
            out = new BufferedWriter(new FileWriter(fileName, true));
            out.flush();
            out.write(toFile);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();

        }
    }




    public static void showdataset( int testcase,int testpointCoverage, String fileName) {

        BufferedWriter out;
        String toFile = testcase + " " + testpointCoverage + "\n";
        try {
            out = new BufferedWriter (new FileWriter(fileName, true));
            out.flush();
            out.write(toFile);
            out.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }



    public static void writeResultToFile(String str)
            throws IOException {

        BufferedWriter writer = new BufferedWriter(new FileWriter(OUTPUT_FILE_NAME));
        writer.write(str);

        writer.close();
    }




}
