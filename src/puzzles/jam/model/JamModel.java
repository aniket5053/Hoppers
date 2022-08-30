package puzzles.jam.model;
import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *  Class for a Model for the game Jam.
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class JamModel {
    /**
     * the collection of observers of this model
     */
    private final List<Observer<JamModel, String>> observers = new LinkedList<>();

    /**
     * the current configuration
     */
    private JamConfig currentConfig;

    /** the starting configuration*/
    private JamConfig startConfig;

    /** use's first selected row*/
    private int selectRow;

    /**user's first selected column*/
    private int selectCol;

    /** filename of puzzle running*/
    private String filename;

    /**hashmap of every car in the configuration*/
    private final HashMap<Character, Car> cars = new HashMap<>();


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<JamModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * alerts observers of this object with a message, force an update
     * @param msg whatever message you want observers to see
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);

        }
    }

    /**
     * Constructor for the model, preloads  a given file
     * @param filename name of file of puzzle we want to play
     */
    public JamModel(String filename){
        this.selectRow = -1;
        this.selectCol = -1;
        this.filename = filename;
        this.load(filename);
    }

    /**
     * Gives the player a hint and updates the currentConfig with the next move in the solution if the board is solvable
     */
    public void hint(){
        Solver solve = new Solver();
        currentConfig.emptyCars();
        List<Configuration> path = solve.getPath(currentConfig);
        if(path == null){
            alertObservers("No solution");
        }
        else if (path.size() == 1){
            alertObservers("Already Solved");
        }
        else{
            currentConfig = (JamConfig) path.get(1);
            alertObservers("Hint");
        }

    }

    /**
     * Manually loads a different puzzle that the player wants to play
     * @param filename puzzle file someone wants ot play
     */
    public void load(String filename){
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String[] fields = in.readLine().split("\\s+");
            int rows = Integer.parseInt(fields[0]);
            int cols = Integer.parseInt(fields[1]);
            int numCars = Integer.parseInt(in.readLine());

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
                cars.put(name, new Car(name,size,orientation,startRow, startCol));
            }

            StringBuilder begin = new StringBuilder();
            for(int i = 0; i<rows;i++){
                for(int j = 0; j<cols;j++){
                    begin.append(map[i][j]).append(" ");
                }
                begin.append("\n");

            }

            this.currentConfig = new JamConfig(cols, rows, map, cars);
            this.startConfig = new JamConfig(cols, rows, map, cars);
            alertObservers("Loaded");

        }
        catch(IOException ignored){

        }


    }


    /**
     * Select will get the car a player wants to move when first called, the slide the car to the
     * (valid) adjacent space a player chooses to move it to on the second call.
     * @param row selected row by player
     * @param col selected column by player
     */
    public void select(int row, int col){
        if(row < 0 || row>= currentConfig.getRow() || col <0 || col >= currentConfig.getCol()){
            alertObservers("outofboundsSelect");
        }
        else{
            if(selectRow == -1){
                if (currentConfig.getMap()[row][col] != JamConfig.EMPTY) {
                    alertObservers("Selected " + "(" + row + "," + col + ")");
                    selectRow = row;
                    selectCol = col;
                }
                else{
                    alertObservers("No car at" + "(" + row + "," + col + ")");
                }
            }
            else{
                if(currentConfig.getMap()[row][col] ==  JamConfig.EMPTY) {
                    Car currentCar = cars.get(currentConfig.getMap()[selectRow][selectCol]);
                    char[][] newConfig = new char[currentConfig.getRow()][currentConfig.getCol()];
                    for (int k = 0; k < currentConfig.getRow(); k++) {
                        System.arraycopy(currentConfig.getMap()[k], 0, newConfig[k], 0, currentConfig.getCol());

                    }
                    if (currentCar.getOrientation() == 'H') {
                        if (currentCar.getRow() == row) {
                            if (col == currentCar.getCol() - 1) {
                                newConfig[row][col] = currentConfig.getMap()[row][col+currentCar.getSize()];
                                newConfig[row][col+currentCar.getSize()] = JamConfig.EMPTY;
                                cars.remove(currentCar.getName());
                                cars.put(currentCar.getName(), new Car(currentCar.getName(), currentCar.getSize(), currentCar.getOrientation(), currentCar.getRow(), currentCar.getCol()-1));
                            }
                            else if (col == currentCar.getCol()+currentCar.getSize()){
                                newConfig[row][col] = currentConfig.getMap()[row][col- currentCar.getSize()];
                                newConfig[row][col-currentCar.getSize()] = JamConfig.EMPTY;
                                cars.remove(currentCar.getName());
                                cars.put(currentCar.getName(), new Car(currentCar.getName(), currentCar.getSize(), currentCar.getOrientation(), currentCar.getRow(), currentCar.getCol()+1));
                            }
                            else{
                                alertObservers("invalidselect2");
                            }

                        }
                        else {
                            alertObservers("not in same row for h");
                        }
                    }
                    else{
                        if(currentCar.getCol() == col){
                            if(row == currentCar.getRow()-1){
                                newConfig[row][col] = currentConfig.getMap()[row+ currentCar.getSize()][col];
                                newConfig[row+currentCar.getSize()][col] = JamConfig.EMPTY;
                                cars.remove(currentCar.getName());
                                cars.put(currentCar.getName(), new Car(currentCar.getName(), currentCar.getSize(), currentCar.getOrientation(), currentCar.getRow()-1, currentCar.getCol()));

                            }
                            else if(row == currentCar.getRow()+ currentCar.getSize()){
                                newConfig[row][col] = currentConfig.getMap()[row - currentCar.getSize()][col];
                                newConfig[row-currentCar.getSize()][col] = JamConfig.EMPTY;
                                cars.remove(currentCar.getName());
                                cars.put(currentCar.getName(), new Car(currentCar.getName(), currentCar.getSize(), currentCar.getOrientation(), currentCar.getRow()+1, currentCar.getCol()));
                            }
                            else{
                                alertObservers("Invalidselect 2");
                            }
                        }
                        else {
                            alertObservers("not in same row for V");
                        }
                    }
                    currentConfig = new JamConfig(currentConfig.getCol(),currentConfig.getRow(), newConfig, cars);
                    alertObservers("Moved from " + "(" + selectRow + "," + selectCol + ")" + " to " + "(" + row + "," + col + ")");


                }
                else{
                    alertObservers("Cannot move from " + "(" + selectRow + "," + selectCol + ")" + " to " + "(" + row + "," + col + ")");
                }
                selectRow = -1;
                selectCol = -1;
            }
        }

    }

    /**
     * Quits out of the program
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * Resets the game, including the player's first select
     */
    public void reset(){
        currentConfig = startConfig;
        selectCol = -1;
        selectRow = -1;

        alertObservers("reset");

    }

    /**
     * Returns the current config the Model is on
     * @return currentConfig
     */
    public JamConfig getCurrentConfig() {
        return currentConfig;
    }


}