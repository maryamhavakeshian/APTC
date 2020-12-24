package com.csi.controller.selector;

import com.csi.model.Individual;
import com.csi.model.Population;

import java.util.Random;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public  abstract class ParentSelector {
    public Random randomValue;

    public ParentSelector() {
        randomValue = new Random();
    }
    public abstract Individual selectParent(Population population);

}
