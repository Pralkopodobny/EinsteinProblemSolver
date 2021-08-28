package CSP;

import CSP.Constraints.Constraint;
import CSP.Heuristics.Heuristic;

import java.util.ArrayList;

public class ForwardCheckingSolver<T> {
    private ArrayList<ArrayList<T>> domains;
    private ArrayList<T> solution;
    private ArrayList<ArrayList<T>> allSolutions = new ArrayList<>();
    private ArrayList<ArrayList<Constraint<T>>> constraints;
    private Heuristic heuristic;
    private int nodes = 0;

    public int getNodes() {
        return nodes;
    }

    public ForwardCheckingSolver(ArrayList<ArrayList<T>> domains, ArrayList<ArrayList<Constraint<T>>> constraints, Heuristic heuristic)
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
        step(heuristic.first(), domains);
        return allSolutions;
    }

    private void step(int index, ArrayList<ArrayList<T>> copyOfDomain){
        if(index >= solution.size()){
            allSolutions.add((ArrayList<T>) solution.clone());
            heuristic.back();
            return;
        }
        else{
            if(goodDomains(copyOfDomain)) {
                for (int i = 0; i < copyOfDomain.get(index).size(); i++) {
                    solution.set(index, copyOfDomain.get(index).get(i));
                    if (!reject(index)) {
                        var nextDomain = deepCopyOfDomains(copyOfDomain);
                        simplifyDomains(index, copyOfDomain.get(index).get(i), nextDomain);
                        step(heuristic.next(), nextDomain);
                    }

                }
            }
        }
        backtrack(index);
    }

    private void simplifyDomains(int index, T value, ArrayList<ArrayList<T>> nextDomain){
        for(var c : constraints.get(index)){
            c.simplifyDomains(value, nextDomain);
        }
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

    private ArrayList<ArrayList<T>> deepCopyOfDomains(ArrayList<ArrayList<T>> d){
        ArrayList<ArrayList<T>> temp = new ArrayList<>(d.size());
        for(int i = 0; i < d.size(); i++){
            temp.add((ArrayList<T>) d.get(i).clone());
        }
        return temp;
    }

    private boolean goodDomains(ArrayList<ArrayList<T>> copyOfDomain){
        for (var d : copyOfDomain){
            if(d.size() == 0) return false;
        }
        return true;
    }

}
