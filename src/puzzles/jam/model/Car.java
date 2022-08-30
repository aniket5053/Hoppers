package puzzles.jam.model;

/**
 * A class for a Car object
 * @author Lucas Romero
 */
public class Car {

    /** Name of the car, one letter*/
    private final char name;
    /** Size of the car*/
    private final int size;
    /** Orientation fo the car, either V(ertical) or H(orizontal)*/
    private final char orientation;
    /** Row the car is in, top left cell of the car*/
    private int row;
    /**Column the car is in, top left cell of the car*/
    private int col;

    /**
     * Constructor for a car if location is not important (PTUI mainly)
     * @param name Letter assigned to car
     * @param size Size of car
     * @param orientation Orientation of car
     */
    public Car(char name, int size, char orientation){
        this.name = name;
        this.size = size;
        this.orientation = orientation;

    }

    /**
     * Constructor for a car if location is important
     * @param name name of car
     * @param size size of car
     * @param orientation orientation of car
     * @param row row of car
     * @param col column of car
     */
    public Car(char name, int size, char orientation, int row, int col){
        this.name = name;
        this.size = size;
        this.orientation = orientation;
        this.row = row;
        this.col = col;
    }

    /**
     * Name accessor
     * @return name of car
     */
    public char getName() {
        return name;
    }

    /**
     * Size accessor
     * @return size of car
     */
    public int getSize() {
        return size;
    }

    /**
     * Orientation accessor
     * @return orientation of car
     */
    public char getOrientation() {
        return orientation;
    }

    /**
     * Hash Code of a car object is just the ascii value of its name
     * @return integer hash Code
     */
    @Override
    public int hashCode(){
        return Character.getNumericValue(name);
    }

    /**
     * If two object are the same return true
     * @param other what you are comparing against
     * @return true if same
     */
    @Override
    public boolean equals(Object other){
        return this.hashCode() == other.hashCode();
    }

    /**
     * Returns the string format of the object
     * @return string format
     */
    @Override
    public String toString(){
        return Character.toString(name);
    }

    /**
     * Row accessor
     * @return row of car
     */
    public int getRow() {
        return row;
    }

    /**
     * Column accessor
     * @return column of car
     */
    public int getCol() {
        return col;
    }
}
