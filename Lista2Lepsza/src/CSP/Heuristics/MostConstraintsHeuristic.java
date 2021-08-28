package CSP.Heuristics;

import CSP.Constraints.Constraint;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class MostConstraintsHeuristic implements Heuristic{
    public int index = 0;
    public int[] list;
    public MostConstraintsHeuristic(ArrayList<ArrayList<Constraint<Integer>>> constraints){
        list = new int[constraints.size() + 1];
        list[constraints.size()] = constraints.size();

        Pair<Integer, Integer>[] sizes = new Pair[constraints.size()];
        for(int i = 0; i < constraints.size(); i++){
            sizes[i] = new Pair<>(i, constraints.get(i).size());
        }
        Arrays.sort(sizes, new Comparator<Pair<Integer, Integer>>() {
            @Override
            public int compare(Pair<Integer, Integer> t1, Pair<Integer, Integer> t2) {
                return t2.getValue().compareTo(t1.getValue());
            }
        });
        int i = 0;
        for(var s : sizes){
            list[i++] = s.getKey();
        }
    }

    @Override
    public int next() {
        return list[++index];
    }

    @Override
    public void back() {
        index--;
    }

    @Override
    public int first() {
        return list[0];
    }
}
