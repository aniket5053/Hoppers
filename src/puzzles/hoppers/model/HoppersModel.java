package puzzles.hoppers.model;

import puzzles.common.Observer;
import puzzles.common.solver.Configuration;
import puzzles.common.solver.Solver;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * A class for the Model for the hoppers game
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class HoppersModel {
    /** the collection of observers of this model */
    private final List<Observer<HoppersModel, String>> observers = new LinkedList<>();

    /** the current configuration */
    private HoppersConfig currentConfig;

    /** the starting configuration */
    private HoppersConfig startConfig;

    /** User's first selected row */
    private int selectRow;
    /**User's first selected column */
    private int selectCol;
    /** Filename of the puzzle */
    private String filename;


    /**
     * The view calls this to add itself as an observer.
     *
     * @param observer the view
     */
    public void addObserver(Observer<HoppersModel, String> observer) {
        this.observers.add(observer);
    }

    /**
     * The model's state has changed (the counter), so inform the view via
     * the update method
     */
    private void alertObservers(String msg) {
        for (var observer : observers) {
            observer.update(this, msg);

        }
    }

    /**
     * Constructor for the model, preloads a game
     * @param filename filename of the puzzle someone wants to run
     */
    public HoppersModel(String filename){
        this.selectRow = -1;
        this.selectCol = -1;
        this.filename = filename;
        this.load(filename);
    }


    /**
     * Solves the puzzle from the currentConfig and makes the currentConfig the next step in the solution if it exists
     */
    public void hint(){
        Solver solve = new Solver();
        List<Configuration> path = solve.getPath(currentConfig);
        if(path == null){
            alertObservers("No solution");

        }
        else if (path.size() == 1){
            alertObservers("Already Solved");

        }
        else{

            currentConfig = (HoppersConfig) path.get(1);
            alertObservers("Hint");
        }

    }

    /**
     * Manual load for a puzzle file
     * @param filename name of the file someone wants to load
     */
    public void load(String filename){
        try(BufferedReader in = new BufferedReader(new FileReader(filename))){
            String[] fields = in.readLine().split("\\s+");
            int rows = Integer.parseInt(fields[0]);
            int cols = Integer.parseInt(fields[1]);
            char[][] frogMap = new char[rows][cols];
            String[][] fakeMap = new String[rows][cols];

            for(int i = 0; i<rows;i++){
                fakeMap[i]= in.readLine().split("\\s+");
            }

            for(int i = 0; i<rows;i++){
                for(int j = 0; j<cols;j++){
                    frogMap[i][j] = fakeMap[i][j].charAt(0);
                }
            }

            this.currentConfig = new HoppersConfig(cols,rows,frogMap);
            this.startConfig = new HoppersConfig(cols, rows, frogMap);
            alertObservers("Loaded: " + filename);
        }
        catch(IOException e){
            e.printStackTrace();
        }

    }


    /**
     * Selects the place of the frog the player wants to move on the first call, the place the player wants to move it to on the second call
     * @param row row the player chose
     * @param col column the player chose
     */
    public void select(int row, int col){
        if(row < 0 || row>= currentConfig.getMapRow() || col <0 || col >= currentConfig.getMapCol()){
            alertObservers("outofboundsSelect");
        }
        else {
            if (selectRow == -1) {
                if (currentConfig.getFrogMap()[row][col] != HoppersConfig.EMPTY) {
                    alertObservers("Selected " + "(" + row + "," + col + ")");
                    selectRow = row;
                    selectCol = col;
                } else {
                    alertObservers("No frog at" + "(" + row + "," + col + ")");
                }
            } else {
                char temp = currentConfig.getFrogMap()[selectRow][selectCol];
                if (currentConfig.getFrogMap()[row][col] == HoppersConfig.EMPTY) {
                    int checkRow;
                    int checkCol;
                    if (row == selectRow) {
                        checkRow = row;
                    } else {
                        checkRow = selectRow + ((row - selectRow) / 2);
                    }
                    if (col == selectCol) {
                        checkCol = col;
                    } else {
                        checkCol = selectCol + ((col - selectCol) / 2);
                    }
                    if (currentConfig.getFrogMap()[checkRow][checkCol] != HoppersConfig.RED &&
                            currentConfig.getFrogMap()[checkRow][checkCol] != HoppersConfig.EMPTY) {
                        char[][] newConfig = new char[currentConfig.getMapRow()][currentConfig.getMapCol()];
                        for (int i = 0; i < currentConfig.getMapRow(); i++) {
                            if (currentConfig.getMapCol() >= 0)
                                System.arraycopy(currentConfig.getFrogMap()[i], 0, newConfig[i], 0, currentConfig.getMapCol());

                        }
                        newConfig[checkRow][checkCol] = HoppersConfig.EMPTY;
                        newConfig[selectRow][selectCol] = HoppersConfig.EMPTY;
                        newConfig[row][col] = temp;
                        currentConfig = new HoppersConfig(currentConfig.getMapCol(), currentConfig.getMapRow(), newConfig);
                        alertObservers("Jumped from " + "(" + selectRow + "," + selectCol + ")" + " to " + "(" + row + "," + col + ")");


                    }
                    else {
                        alertObservers("Cannot jump from " + "(" + selectRow + "," + selectCol + ")" + " to " + "(" + row + "," + col + ")");
                    }

                } else {
                    alertObservers("Cannot jump from " + "(" + selectRow + "," + selectCol + ")" + " to " + "(" + row + "," + col + ")");
                }
                selectRow = -1;
                selectCol = -1;


            }
        }

    }


    /**
     * Quits the program
     */
    public void quit(){
        System.exit(0);
    }

    /**
     * Resets the game and the player's first select space
     */
    public void reset(){
        currentConfig = startConfig;
        selectCol = -1;
        selectRow = -1;

        alertObservers("Puzzle reset");

    }

    /**
     * Returns current config being used by the model
     * @return current configuration
     */
    public HoppersConfig getCurrentConfig() {
        return currentConfig;
    }

    /**
     * Return user's selected row
     * @return selected row
     */
    public int getSelectRow() {
        return selectRow;
    }

    /**
     * returns user's selected column
     * @return selected column
     */
    public int getSelectCol() {
        return selectCol;
    }
}
