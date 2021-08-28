package CSP;

import CSP.Constraints.BinaryConstraint;
import CSP.Constraints.Constraint;
import CSP.Constraints.NAryConstraint;
import CSP.Constraints.UnaryConstraint;
import CSP.Heuristics.NaiveHeuristic;
import EinsteinProblem.EinsteinSolver;

import java.util.*;
import java.util.stream.Collectors;

public class AC3Algorithm {
    private ArrayList<ArrayList<Integer>> domains;
    private ArrayList<ArrayList<Constraint<Integer>>> constraints;
    private ArrayList<ArrayList<BinaryConstraint<Integer>>> bins;
    private ArrayList<ArrayList<BinaryConstraint<Integer>>> reversedBins;
    private ArrayList<UnaryConstraint<Integer>> unary;
    HashSet<BinaryConstraint<Integer>> set;

    public AC3Algorithm(ArrayList<ArrayList<Integer>> domains, ArrayList<ArrayList<Constraint<Integer>>> constraints){
        if(domains.size() != constraints.size()) throw new IllegalArgumentException("Sizes");
        this.domains = domains;
        this.constraints = constraints;

        bins = new ArrayList<>(domains.size());
        reversedBins = new ArrayList<>(domains.size());
        unary = new ArrayList<>(domains.size());
        set = new HashSet<>();

        for(int i = 0; i < constraints.size(); i++){
            bins.add(new ArrayList<>(constraints.get(i).size()));
            for(int j = 0; j < constraints.get(i). size(); j++){
                var c = constraints.get(i).get(j);
                if(c instanceof BinaryConstraint){
                    bins.get(i).add((BinaryConstraint<Integer>) c);
                    set.add((BinaryConstraint<Integer>) c);
                }
                else {
                    if(c instanceof UnaryConstraint){
                        unary.add((UnaryConstraint<Integer>) c);
                    }
                    else{
                        if(c instanceof NAryConstraint){
                            var splitedConstraints = (((NAryConstraint<Integer>) c).getBinary());
                            for(var sc : splitedConstraints){
                                bins.get(i).add(sc);
                            }
                        }
                    }
                }

            }
        }

        for(int i = 0; i < domains.size(); i++){
            reversedBins.add(new ArrayList<>(bins.get(i).size()));
            for(int j = 0; j < bins.get(i).size(); j++){
                reversedBins.get(i).add(bins.get(i).get(j).reversed());
            }
        }
    }

    private void unaryConsistency(){
        for(var u : unary){
            u.AC3Domain(domains.get(u.getIndex()));
        }
    }

    private void binaryConsistency(){
        LinkedList<BinaryConstraint<Integer>> agenda = new LinkedList<>(set);
        while(!agenda.isEmpty()){
            var c = agenda.getFirst();
            var dom1 = domains.get(c.getFirst());
            var dom2 = domains.get(c.getSec());
            boolean changed = c.AC3Domains(dom1, dom2);
            if(changed){
                for(var r : reversedBins.get(c.getFirst())){
                    if(!set.contains(r)){
                        agenda.addLast(r);
                        set.add(r);
                    }
                }
            }
            var a = set.remove(c);
            agenda.removeFirst();
        }
    }

    public void simplify(){
        unaryConsistency();
        binaryConsistency();
    }

    public static void main(String[] args) {
        var domains = EinsteinSolver.getDomains();
        var constraints = EinsteinSolver.getConstraints();

        AC3Algorithm ac3 = new AC3Algorithm(domains, constraints);
        ac3.unaryConsistency();
        ac3.binaryConsistency();
        System.out.println("YEET");

        BactrackSolver<Integer> solver = new BactrackSolver<>(domains, constraints, new NaiveHeuristic());
        var solutions = solver.solve();
        System.out.println(solutions.size());
        System.out.println(solver.getNodes());
    }
}
