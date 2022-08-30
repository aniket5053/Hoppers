package puzzles.strings;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.util.Collection;

public class Strings {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println(("Usage: java Strings start finish"));
        } else {
            StringsConfig start = new StringsConfig(args[0]);
            StringsConfig.ending(args[1]);
            System.out.println("Start: "+ args[0] +", End: "+ args[1]);
            Solver solve = new Solver();
            Collection<Configuration> result = solve.getPath( start);
            System.out.println("Total configs: "+solve.getTotalConfigs());
            System.out.println("Unique configs: "+solve.getUniqueConfigs());
            if (result == null)
            {
                System.out.println("No solution");
            }
            else
            {
                int index = 0;
                for (Configuration config : result) {
                    System.out.println("Step " + index + ": " +config);
                    index++;
                }

            }


        }
    }
}
