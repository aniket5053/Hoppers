package puzzles.jam.solver;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import puzzles.jam.model.Car;
import puzzles.jam.model.JamConfig;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * A class to run the Jam game
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class Jam {
    /**
     * Main class that checks for needed file argument, and then it proceeds with the game
     * @param args the filename of the puzzle we want to play
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: java Jam filename");
        }
        else{
            System.out.println("File: "+args[0]);
            try(BufferedReader in = new BufferedReader(new FileReader(args[0]))){
                String[] fields = in.readLine().split("\\s+");
                int rows = Integer.parseInt(fields[0]);
                int cols = Integer.parseInt(fields[1]);
                int numCars = Integer.parseInt(in.readLine());
                HashMap<Character, Car> cars = new HashMap<>();
                char[][] map = new char[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        map[i][j] = '.';
                    }
                }
                for (int i = 0; i < numCars; i++) {
                    String[] carInfo = in.readLine().split("\\s+");
                    char name = carInfo[0].charAt(0);
                    char orientation;
                    int startRow = Integer.parseInt(carInfo[1]);
                    int startCol = Integer.parseInt(carInfo[2]);
                    int endRow = Integer.parseInt(carInfo[3]);
                    int endCol = Integer.parseInt(carInfo[4]);
                    if(startRow == endRow){
                        orientation = 'H';
                        for (int j = startCol; j < endCol+1; j++) {
                            map[startRow][j] = name;
                        }
                    }
                    else{
                        orientation = 'V';
                        for (int j = startRow; j < endRow+1; j++) {
                            map[j][startCol] = name;
                        }
                    }
                    int size = (endRow - startRow)+ (endCol-startCol) +1;
                    cars.put(name, new Car(name,size,orientation));
                }

                StringBuilder begin = new StringBuilder();
                for(int i = 0; i<rows;i++){
                    for(int j = 0; j<cols;j++){
                        begin.append(map[i][j]).append(" ");
                    }
                    begin.append("\n");

                }
                System.out.println(begin);

                JamConfig start = new JamConfig(cols, rows, map, cars);

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

            }
            catch(IOException e){
                e.printStackTrace();
            }
        }
    }
}