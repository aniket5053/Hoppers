package puzzles.jam.model;

import puzzles.common.solver.Configuration;

import java.util.*;


/**
 * A class of a configuration of the Jam game
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class JamConfig implements Configuration {
    /*Number of rows in map */
    private final int mapRow;
    /** Number of columns in map */
    private final int mapCol;
    /** 2d array of map of cars */
    private final char[][] map;
    /** An Empty space character */
    public static final char EMPTY = '.';
    /** A Hashset of cars that have been visited so far */
    private HashSet<Character> visitedCars;
    /** A hashset of cars that exist in this game */
    private final HashMap<Character, Car> cars;

    /**
     * A constructor of a configuration in this jam game
     * @param mapCol columns in the map
     * @param mapRow rows in the map
     * @param map map of cars
     * @param cars list of cars
     */
    public JamConfig(int mapCol, int mapRow, char[][] map, HashMap<Character, Car> cars) {
        this.mapCol = mapCol;
        this.mapRow = mapRow;
        this.map = map;
        this.cars = cars;
        visitedCars = new HashSet<>();

    }

    /**
     * Checks if the current config is a solution
     * @return true if the current config is a solution
     */
    @Override
    public boolean isSolution() {
        for (int i = 0; i < mapRow; i++) {
            if (map[i][mapCol - 1] == 'X') {
                return true;
            }
        }
        return false;
    }


    /**
     * Get neighbors by generating a list of every possible move available from this config
     * @return a list of possible moves
     */
    @Override
    public List<Configuration> getNeighbors() {
        LinkedList<Configuration> neighbors = new LinkedList<>();
        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                if (map[i][j] != EMPTY) {
                    if (!visitedCars.contains(map[i][j])) {
                        Car current = cars.get(map[i][j]); //using primitive for object key??
                        if (current.getOrientation() == 'H') {
                            try {
                                if (map[i][j - 1] == EMPTY) {
                                    char[][] newConfig = new char[mapRow][mapCol];
                                    for (int k = 0; k < mapRow; k++) {
                                        System.arraycopy(map[k], 0, newConfig[k], 0, mapCol);

                                    }

                                    char temp = map[i][j];
                                    newConfig[i][j - 1] = temp;
                                    newConfig[i][j - 1 + current.getSize()] = EMPTY;
                                    neighbors.add(new JamConfig(mapCol, mapRow, newConfig, cars));
                                }
                                visitedCars.add(current.getName());
                            } catch (IndexOutOfBoundsException ignored) {

                            }
                            try {
                                if (map[i][j + current.getSize()] == EMPTY) {
                                    char[][] newConfig = new char[mapRow][mapCol];
                                    for (int k = 0; k < mapRow; k++) {
                                        System.arraycopy(map[k], 0, newConfig[k], 0, mapCol);

                                    }

                                    char temp = map[i][j];
                                    newConfig[i][j + current.getSize()] = temp;
                                    newConfig[i][j] = EMPTY;
                                    neighbors.add(new JamConfig(mapCol, mapRow, newConfig, cars));
                                }
                                visitedCars.add(current.getName());
                            } catch (IndexOutOfBoundsException ignored) {

                            }
                        } else {
                            try {
                                if (map[i + current.getSize()][j] == EMPTY) {
                                    char[][] newConfig = new char[mapRow][mapCol];
                                    for (int k = 0; k < mapRow; k++) {
                                        System.arraycopy(map[k], 0, newConfig[k], 0, mapCol);

                                    }

                                    char temp = map[i][j];
                                    newConfig[i + current.getSize()][j] = temp;
                                    newConfig[i][j] = EMPTY;
                                    neighbors.add(new JamConfig(mapCol, mapRow, newConfig, cars));
                                }
                                visitedCars.add(current.getName());
                            } catch (IndexOutOfBoundsException ignored) {

                            }
                            try {
                                if (map[i - 1][j] == EMPTY) {
                                    char[][] newConfig = new char[mapRow][mapCol];
                                    for (int k = 0; k < mapRow; k++) {
                                        System.arraycopy(map[k], 0, newConfig[k], 0, mapCol);

                                    }

                                    char temp = map[i][j];
                                    newConfig[i - 1][j] = temp;
                                    newConfig[i - 1 + current.getSize()][j] = EMPTY;
                                    neighbors.add(new JamConfig(mapCol, mapRow, newConfig, cars));
                                }
                                visitedCars.add(current.getName());
                            } catch (IndexOutOfBoundsException ignored) {

                            }

                        }
                    }
                }
            }

        }
        return neighbors;
    }

    /**
     * Make a hash code of this object
     * @return an integer hash code of this object
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(map);
    }

    /**
     * Check if this is equal to another jamConfig
     * @param o the other jamConfig
     * @return  true if this is equal to the other
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof JamConfig that) {
            result = Arrays.deepEquals(map, that.map);
        }
        return result;

    }


    /**
     * Makes a string representation fo this object
     * @return a string representation of this object
     */
    @Override
    public String toString() {
        StringBuilder string = new StringBuilder();
        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                string.append(map[i][j]).append(" ");
            }
            string.append("\n");
        }
        return string.toString();
    }

    /**
     * map column accessor
     * @return columns in the map
     */
    public int getCol() {
        return mapCol;
    }

    /**
     * map row accessor
     * @return rows in the map
     */
    public int getRow() {
        return mapRow;
    }

    /**
     * map accessor
     * @return map of this config
     */
    public char[][] getMap(){
        return map;
    }

    /**
     * empties the visited cars hash Set, used for solving the same config multiple times (hint)
     */
    public void emptyCars(){
        this.visitedCars = new HashSet<>();
    }

    /**
     * car list accessor
     * @return list of cars
     */
    public HashMap<Character, Car> getCars() {
        return cars;
    }
}
