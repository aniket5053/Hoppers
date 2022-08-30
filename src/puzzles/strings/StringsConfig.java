package puzzles.strings;

import puzzles.common.solver.Configuration;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class StringsConfig implements Configuration {
    public String starting;
    private static String ending;

    public StringsConfig(String starting) {
        this.starting = starting;

    }
    @Override
    public Collection<Configuration> getNeighbors()
    {
        LinkedList<Configuration> neighbors = new LinkedList<>();
        for (int i = 0; i < starting.length(); i++) {
            char ascii = starting.charAt(i);
            if (ascii == 'Z')
            {
                neighbors.add(new StringsConfig(starting.substring(0, i ) + "Y" + starting.substring(i + 1)));
                neighbors.add(new StringsConfig(starting.substring(0, i) + "A" + starting.substring(i + 1) ));

            }
            else if (ascii == 'A')
            {
                neighbors.add(new StringsConfig(starting.substring(0, i ) + "Z" + starting.substring(i + 1) ));
                neighbors.add( new StringsConfig(starting.substring(0, i ) + "B" + starting.substring(i + 1) ));

            }
            else
            {
                neighbors.add( new StringsConfig(starting.substring(0, i) + Character.toString(ascii - 1) + starting.substring(i + 1) ));
                neighbors.add(new StringsConfig(starting.substring(0, i) + Character.toString(ascii + 1) + starting.substring(i + 1) ));
            }
        }
        return neighbors;
    }
    public boolean equals(Object object)
    {
        if(this == object)
        {
            return true;
        }
        if (object == null || getClass() != object.getClass())
        {
            return false;
        }
        return starting.equals(((StringsConfig) object).starting);
    }

    public  static void ending(String start)
    {
        ending = start;

    }
    public int hashCode(){
        return this.starting.hashCode();
    }

    public boolean isSolution()
    {
        return this.starting.equals(ending);

    }

    @Override
    public String toString(){
        return starting;
    }




}
