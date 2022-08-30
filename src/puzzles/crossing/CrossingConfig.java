package puzzles.crossing;

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.LinkedList;

public class CrossingConfig implements Configuration {
    private final int leftpup;
    private final int leftwolf;
    private final int rightpup;
    private final int rightwolf;
    private final boolean boat;


    public CrossingConfig(int leftpup, int leftwolf, int rightpup, int rightwolf, boolean boat) {

        this.leftpup = leftpup;
        this.leftwolf = leftwolf;
        this.rightpup = rightpup;
        this.rightwolf = rightwolf;
        this.boat = boat;

    }

    @Override
    public boolean isSolution() {
        return leftpup == 0 && leftwolf == 0;
    }


    @Override
    public Collection<Configuration> getNeighbors() {
        LinkedList<Configuration> neighbors = new LinkedList<>();
        if (boat) {
            if (leftpup >= 1) {
                neighbors.add(new CrossingConfig(leftpup - 1, leftwolf, rightpup + 1, rightwolf, false));
                if (leftpup >= 2) {
                    neighbors.add(new CrossingConfig(leftpup - 2, leftwolf, rightpup + 2, rightwolf, false));
                }
            }
            if (leftwolf >= 1) {
                neighbors.add(new CrossingConfig(leftpup, leftwolf - 1, rightpup, rightwolf + 1, false));
            }
        } else {
            if (rightpup >= 1) {
                neighbors.add(new CrossingConfig(leftpup + 1, leftwolf, rightpup - 1, rightwolf, true));
                if (rightpup >= 2) {
                    neighbors.add(new CrossingConfig(leftpup + 2, leftwolf, rightpup - 2, rightwolf, true));
                }
            }
            if (rightwolf >= 1) {
                neighbors.add(new CrossingConfig(leftpup, leftwolf + 1, rightpup, rightwolf - 1, true));
            }
        }
        return neighbors;
    }

    @Override
    public boolean equals(Object other) {
        return this.hashCode() == other.hashCode();
    }


    @Override
    public int hashCode() {
        StringBuilder hash = new StringBuilder();
        hash.append(leftpup).append(leftwolf).append(rightpup).append(rightwolf);
        if (boat) {
            hash.append(1);
        } else {
            hash.append(0);
        }
        return Integer.parseInt(hash.toString());
    }


    @Override
    public String toString() {
        String current;
        if (boat) {
            current = "(BOAT) left=[" + leftpup + ", " + leftwolf + "], right=[" + rightpup + ", " + rightwolf + "]       ";
        } else {
            current = "       left=[" + leftpup + ", " + leftwolf + "], right=[" + rightpup + ", " + rightwolf + "] (BOAT)";
        }
        return current;
    }
}
