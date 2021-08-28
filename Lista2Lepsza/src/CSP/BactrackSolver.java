package CSP;
import CSP.Constraints.Constraint;
import CSP.Heuristics.Heuristic;

import java.util.ArrayList;

public class BactrackSolver<T> {
    private ArrayList<ArrayList<T>> domains;
    private ArrayList<T> solution;
    private ArrayList<ArrayList<T>> allSolutions = new ArrayList<>();
    private ArrayList<ArrayList<Constraint<T>>> constraints;
    private Heuristic heuristic;
    private int nodes = 0;

    public int getNodes() {
        return nodes;
    }

    public BactrackSolver(ArrayList<ArrayList<T>> domains, ArrayList<ArrayList<Constraint<T>>> constraints, Heuristic heuristic)
    {
        this.domains = domains;
        this.constraints = constraints;
        this.heuristic = heuristic;
        if(domains.size() != constraints.size()) throw new IllegalArgumentException("domains and constraints must have same size!");
        solution = new ArrayList<>(domains.size());
        for(int i =0; i < domains.size(); i++){
            solution.add(null);
        }
        for (var constraintArrayList : constraints) {
            for (var constraint : constraintArrayList) {
                constraint.setResultArray(solution);
            }
        }

    }
    public ArrayList<ArrayList<T>> solve(){
        step(heuristic.first());
        return allSolutions;
    }

    private void step(int index){
        if(index >= solution.size()){
            allSolutions.add((ArrayList<T>) solution.clone());
            heuristic.back();
            return;
        }
        else{
            for(int i = 0; i < domains.get(index).size(); i++){
                solution.set(index, domains.get(index).get(i));
                if(!reject(index)){
                    step(heuristic.next());
                }

            }
        }
        backtrack(index);
    }

    private boolean reject(int index){
        nodes++;
        for (var constraint: constraints.get(index)) {
            if(!constraint.satisfied()) return true;
        }
        return false;
    }

    private void backtrack(int index){
        solution.set(index, null);
        heuristic.back();
    }
}
