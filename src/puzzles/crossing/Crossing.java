package puzzles.crossing;


import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;
import java.util.LinkedList;

public class Crossing {


    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Crossing pups wolves"));
        } else {
            int pups = Integer.parseInt(args[0]);
            int wolves = Integer.parseInt(args[1]);
            System.out.println("Pups: " + pups + ", Wolves: " + wolves);
            CrossingConfig initial = new CrossingConfig(pups, wolves, 0, 0, true);
            CrossingConfig solution = new CrossingConfig(0, 0, pups, wolves, false);
            Solver solve = new Solver();
            LinkedList<Configuration> path = (LinkedList<Configuration>) solve.getPath(initial);
            System.out.println("Total configs: " + solve.getTotalConfigs());
            System.out.println("Unique configs: " + solve.getUniqueConfigs());
            ;
            int index = 0;
            if (path != null) {
                for (Configuration config : path) {
                    System.out.println("Step " + index + ": " + config);
                    index++;
                }
            }


        }
    }
}