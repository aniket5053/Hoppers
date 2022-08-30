package puzzles.hoppers.model;
import puzzles.common.solver.Configuration;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;

/**
 * A class of a configuration of the hoppers game
 * @author Lucas Romero
 * @author Aniket Sonika
 */
public class HoppersConfig implements Configuration {
    /** How many columns in the map */
    private final int mapCol;
    /** How many rows in the map */
    private final int mapRow;
    /** A 2d array of all the frogs on the map*/
    private final char[][] frogMap;
    /** Empty character */
    public final static char EMPTY = '.';
    /** Invalid space character */
    public final static char INVALID = '*';
    /** Green frog character */
    public final static char GREEN = 'G';
    /** Red frog character */
    public final static char RED = 'R';

    /**
     * A constructor of a hoppers configuration
     * @param mapCol how many columns in the puzzle
     * @param mapRow how many rows in the puzzles
     * @param frogMap the current state of the map
     */
    public HoppersConfig(int mapCol, int mapRow, char[][] frogMap) {
        this.mapCol = mapCol;
        this.mapRow = mapRow;
        this.frogMap = frogMap;
    }

    /**
     * Checks if this config is a solution
     * @return true if this config is the solution
     */
    @Override
    public boolean isSolution() {
        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {
                if (frogMap[i][j] == GREEN) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Get neighbors function that will generate every valid move from this config
     * @return the list of possible moves from this config
     */
    @Override
    public Collection<Configuration> getNeighbors() {
        LinkedList<Configuration> neighbors = new LinkedList<>();
        for (int row = 0; row < mapRow; row++) {
            for (int col = 0; col < mapCol; col++) {
                char current = frogMap[row][col];
                if (current != EMPTY && current != INVALID) {
                    try {
                        if (frogMap[row - 1][col - 1] != EMPTY && frogMap[row - 2][col - 2] == EMPTY && frogMap[row - 1][col - 1] != RED) {
                            char[][] newConfig = new char[mapRow][mapCol];
                            for (int i = 0; i < mapRow; i++) {
                                System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                            }
                            char temp = frogMap[row][col];
                            newConfig[row][col] = EMPTY;
                            newConfig[row - 1][col - 1] = EMPTY;
                            newConfig[row - 2][col - 2] = temp;
                            neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                    try {
                        if (frogMap[row - 1][col + 1] != EMPTY && frogMap[row - 2][col + 2] == EMPTY && frogMap[row - 1][col + 1] != RED) {
                            char[][] newConfig = new char[mapRow][mapCol];
                            for (int i = 0; i < mapRow; i++) {
                                System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                            }
                            char temp = frogMap[row][col];
                            newConfig[row][col] = EMPTY;
                            newConfig[row - 1][col + 1] = EMPTY;
                            newConfig[row - 2][col + 2] = temp;
                            neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                        }
                    } catch (IndexOutOfBoundsException ignored) {

                    }
                    try {
                        if (frogMap[row + 1][col - 1] != EMPTY && frogMap[row + 2][col - 2] == EMPTY && frogMap[row + 1][col - 1] != RED) {
                            char[][] newConfig = new char[mapRow][mapCol];
                            for (int i = 0; i < mapRow; i++) {
                                System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                            }
                            char temp = frogMap[row][col];
                            newConfig[row][col] = EMPTY;
                            newConfig[row + 1][col - 1] = EMPTY;
                            newConfig[row + 2][col - 2] = temp;
                            neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                        }
                    } catch (IndexOutOfBoundsException ignored) {

                    }
                    try {
                        if (frogMap[row + 1][col + 1] != EMPTY && frogMap[row + 2][col + 2] == EMPTY && frogMap[row + 1][col + 1] != RED) {
                            char[][] newConfig = new char[mapRow][mapCol];
                            for (int i = 0; i < mapRow; i++) {
                                System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                            }
                            char temp = frogMap[row][col];
                            newConfig[row][col] = EMPTY;
                            newConfig[row + 1][col + 1] = EMPTY;
                            newConfig[row + 2][col + 2] = temp;
                            neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                        }
                    } catch (IndexOutOfBoundsException ignored) {

                    }

                    if (col % 2 == 0) {
                        try {
                            if (frogMap[row][col - 2] != EMPTY && frogMap[row][col - 4] == EMPTY && frogMap[row][col - 2] != RED) {
                                char[][] newConfig = new char[mapRow][mapCol];
                                for (int i = 0; i < mapRow; i++) {
                                    System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                                }
                                char temp = frogMap[row][col];
                                newConfig[row][col] = EMPTY;
                                newConfig[row][col - 2] = EMPTY;
                                newConfig[row][col - 4] = temp;
                                neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                            }
                        } catch (IndexOutOfBoundsException ignored) {

                        }
                        try {
                            if (frogMap[row][col + 2] != EMPTY && frogMap[row][col + 4] == EMPTY && frogMap[row][col + 2] != RED) {
                                char[][] newConfig = new char[mapRow][mapCol];
                                for (int i = 0; i < mapRow; i++) {
                                    System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                                }
                                char temp = frogMap[row][col];
                                newConfig[row][col] = EMPTY;
                                newConfig[row][col + 2] = EMPTY;
                                newConfig[row][col + 4] = temp;
                                neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                            }
                        } catch (IndexOutOfBoundsException ignored) {

                        }
                        try {
                            if (frogMap[row - 2][col] != EMPTY && frogMap[row - 4][col] == EMPTY && frogMap[row - 2][col] != RED) {
                                char[][] newConfig = new char[mapRow][mapCol];
                                for (int i = 0; i < mapRow; i++) {
                                    System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                                }
                                char temp = frogMap[row][col];
                                newConfig[row][col] = EMPTY;
                                newConfig[row - 2][col] = EMPTY;
                                newConfig[row - 4][col] = temp;
                                neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                            }
                        } catch (IndexOutOfBoundsException ignored) {

                        }
                        try {
                            if (frogMap[row + 2][col] != EMPTY && frogMap[row + 4][col] == EMPTY && frogMap[row + 2][col] != RED) {
                                char[][] newConfig = new char[mapRow][mapCol];
                                for (int i = 0; i < mapRow; i++) {
                                    System.arraycopy(frogMap[i], 0, newConfig[i], 0, mapCol);

                                }
                                char temp = frogMap[row][col];
                                newConfig[row][col] = EMPTY;
                                newConfig[row + 2][col] = EMPTY;
                                newConfig[row + 4][col] = temp;
                                neighbors.add(new HoppersConfig(mapCol, mapRow, newConfig));

                            }
                        } catch (IndexOutOfBoundsException ignored) {

                        }
                    }
                }
            }
        }
        return neighbors;
    }

    /**
     * Equals method that checks if two configs are the same
     * @param o the other config
     * @return true if this config is the same as the other config
     */
    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (o instanceof HoppersConfig that) {
            result = Arrays.deepEquals(frogMap, that.frogMap);
        }
        return result;

    }

    /**
     * Makes a hashCode for this config
     * @return an integer hashCode of this object
     */
    @Override
    public int hashCode() {
        return Arrays.deepHashCode(frogMap);

    }

    /**
     * Makes a string out of the config
     * @return a string off this object
     */
    @Override
    public String toString() {
        StringBuilder froggy = new StringBuilder();
        for (int i = 0; i < mapRow; i++) {
            for (int j = 0; j < mapCol; j++) {

                froggy.append(frogMap[i][j]);
                if (j != mapCol - 1) {
                    froggy.append(" ");
                }
            }
            froggy.append("\n");
        }
        return froggy.toString();
    }

    /**
     * Map column accessor
     * @return columns in the map
     */
    public int getMapCol() {
        return mapCol;
    }

    /**
     * Map row accessor
     * @return rows in the map
     */
    public int getMapRow() {
        return mapRow;
    }

    /**
     * Frog map accessor
     * @return the map of frogs
     */
    public char[][] getFrogMap(){
        return frogMap;
    }

}
