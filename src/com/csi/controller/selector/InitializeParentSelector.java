package com.csi.controller.selector;

/**
 * Created by M_Havakesheyan on 11/29/2020.
 */
public class InitializeParentSelector  {

    public static ParentSelector getParentSelector(int parenrSelectorChoice) {

        ParentSelector parentSelector;
        switch (parenrSelectorChoice) {
            case 0:
                parentSelector = new RouletteWheelLinearRankingPS();
                break;
            case 1:
                parentSelector = new TournamentPS();
                break;
            default:
                System.out.println("The entered parent selector " +parenrSelectorChoice+" is not valid selector!");
                parentSelector = null;
                System.exit(1);
                break;
        }

        return parentSelector;

    }


}
