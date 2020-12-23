package com.csi.util.initialization;

import com.csi.util.CSVUtils;

import java.util.*;

public class GenerateRandomizedDataSet {

    static List<Integer> ents = new ArrayList<>();
    public   void generateDataset() {
        // create random data
        final Random rand = new Random();
        final Set<Integer> generated = new LinkedHashSet<>();
        final int countItems = 300;// set max count items ;
        final int maxCountDetail =70;//set max count detail for one item //60--> 93 50---> 92 80-->95 the morecoverage per test case, the better fitness
        final int maxValueDetail = 1200;//set max value for one detail

        for (int i = 1; i <= maxValueDetail; i++) {
            ents.add(i);
        }

        if (maxValueDetail < maxCountDetail) {
            System.out.println(String.format(" maxValueDetail(%s) should not be less than maxCountDetail (%s)", maxValueDetail, maxCountDetail));
            return;
        }

        for (int i = 1; i <= countItems; i++) {
            int detailCount = rand.nextInt(maxCountDetail) + 1;
            while (generated.size() < detailCount) {
                Integer next = rand.nextInt(maxValueDetail) + 1;
                generated.add(next);
            }
            display(i, generated);
            remove(generated);
            generated.clear();
        }
        displayRem();

    }

//this method is used to create random input

    private  void remove(Set<Integer> gen) {
        for (Integer item : gen) {
            if(ents.contains(item))
                ents.remove(item);
        }
    }
    private  void displayRem(){
        for (Integer ent : ents) {
            System.out.println(String.format("%s  %s", 1, ent));
            CSVUtils.showdataset(1, ent.intValue(), "synthesizeddataset.txt");
        }
    }
    private  void display(int i, Set<Integer> generated) {
        for (Integer integer : generated) {
            System.out.println(String.format("%s  %s", i, integer));
            CSVUtils.showdataset(i, integer.intValue(), "synthesizeddataset.txt")  ;
        }


    }



}
