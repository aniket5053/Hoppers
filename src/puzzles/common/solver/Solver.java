package puzzles.common.solver;

import java.util.*;

public class Solver {

    private int uniqueConfigs = 1;
    private int totalConfigs = 1;

    public Solver(){

    }
    public List<Configuration> getPath(Configuration start){
        List<Configuration> queue = new LinkedList<>();
        queue.add(start);

        HashMap<Configuration, Configuration> predecessor = new HashMap<>();
        predecessor.put(start, start);
        Configuration end = null;


        while(!queue.isEmpty()) {
            Configuration current = queue.remove(0);
            if(current.isSolution()){
                end = current;
                break;
            }

            for (Configuration neighbors : current.getNeighbors()){
                totalConfigs = totalConfigs + 1;
                if(!predecessor.containsKey(neighbors)){
                    uniqueConfigs = uniqueConfigs + 1;
                    predecessor.put(neighbors, current);
                    queue.add(neighbors);
                }
            }
        }

        List<Configuration> path = new LinkedList<>();
        if(predecessor.containsKey(end)){
            Configuration currentWord = end;

            while(currentWord != start){
                path.add(0, currentWord);
                currentWord = predecessor.get(currentWord);
            }
            path.add(0, start);


        }
        if(path.isEmpty()){
            return null;
        }
        return path;
    }


    public int getUniqueConfigs(){
        return uniqueConfigs;
    }

    public int getTotalConfigs(){
        return totalConfigs;
    }

}