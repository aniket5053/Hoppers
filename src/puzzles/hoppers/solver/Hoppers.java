package puzzles.hoppers.solver;

import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.hoppers.model.HoppersConfig;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Class to run the Hoppers game
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class Hoppers {
    /**
     * Main function that checks for file input and then starts the game
     * @param args hopefully one filename
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Hoppers filename");
        }
        else
        {
            System.out.println("File: "+args[0]);
            try(BufferedReader in = new BufferedReader(new FileReader(args[0]))){
                String[] fields = in.readLine().split("\\s+");
                int rows = Integer.parseInt(fields[0]);
                int cols = Integer.parseInt(fields[1]);
                char[][] frogMap = new char[rows][cols];
                String[][] fakeMap = new String[rows][cols];

                for(int i = 0; i<rows;i++){
                    fakeMap[i]= in.readLine().split("\\s+");
                }
                StringBuilder begin = new StringBuilder();
                for(int i = 0; i<rows;i++){
                    for(int j = 0; j<cols;j++){
                        frogMap[i][j] = fakeMap[i][j].charAt(0);
                        begin.append(frogMap[i][j]).append(" ");
                    }
                    begin.append("\n");

                }
                System.out.println(begin);

                HoppersConfig start = new HoppersConfig(cols,rows,frogMap);

                Solver solve = new Solver();
                List<Configuration> result = solve.getPath( start);
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
                        System.out.println("Step " + index + ": \n" +config);
                        index++;
                    }

                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
