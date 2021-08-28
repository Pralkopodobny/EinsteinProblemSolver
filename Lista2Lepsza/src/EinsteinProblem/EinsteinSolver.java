package EinsteinProblem;

import CSP.*;
import CSP.Constraints.*;
import CSP.Heuristics.Heuristic;
import CSP.Heuristics.LeastConstraintsHeuristic;
import CSP.Heuristics.MostConstraintsHeuristic;
import CSP.Heuristics.NaiveHeuristic;

import java.util.ArrayList;

public class EinsteinSolver {
    ArrayList<ArrayList<Integer>> domains;
    ArrayList<ArrayList<Constraint<Integer>>> constraints;

    public EinsteinSolver(){
        domains = EinsteinSolver.getDomains();
        constraints = EinsteinSolver.getConstraints();
    }

    public void solveBacktracking() {
        CSP.BactrackSolver<Integer> solver = new BactrackSolver<>(domains, constraints, new NaiveHeuristic());
        var solutions = solver.solve();
        for(var s : solutions){
            System.out.println(stringifySolution(s));
        }
        System.out.println(solver.getNodes());
    }
    public void solveBacktracking(Heuristic h) {
        var start = System.currentTimeMillis();
        AC3Algorithm ac = new AC3Algorithm(domains, constraints);
        ac.simplify();
        CSP.BactrackSolver<Integer> solver = new BactrackSolver<>(domains, constraints, h);
        var solutions = solver.solve();
        var time = System.currentTimeMillis() - start;
        double seconds = (double)time/1000;
        System.out.println("Czas trwania: " + seconds);
        for(var s : solutions){
            System.out.println(stringifySolution(s));
        }
        System.out.println(solver.getNodes());
    }

    public void solveForward(Heuristic h){
        var start = System.currentTimeMillis();
        AC3Algorithm ac = new AC3Algorithm(domains, constraints);
        ac.simplify();
        CSP.ForwardCheckingSolver<Integer> solver = new ForwardCheckingSolver<>(domains, constraints, h);
        var solutions = solver.solve();
        var time = System.currentTimeMillis() - start;
        double seconds = (double)time/1000;
        System.out.println("Czas trwania: " + seconds);
        for(var s : solutions){
            System.out.println(stringifySolution(s));
        }
        System.out.println(solver.getNodes());
    }

    private String stringifySolution(ArrayList<Integer> solution){
        StringBuilder result = new StringBuilder();
        for(int home = 0; home < 5; home++){
            result.append("\nDom ").append(home + 1).append(":\n");
            for (int index = 0 ; index < 25; index++) {
                if(solution.get(index) == home)
                    result.append(getValue(index)).append(" ");
            }
        }
        return result.toString();
    }
    private String getValue(Integer index){
        switch (index){
            case 0:
                return "Norweg";
            case 1:
                return "Duńczyk";
            case 2:
                return "Niemiec";
            case 3:
                return "Szwed";
            case 4:
                return "Anglik";
            case 5:
                return "Czerwony";
            case 6:
                return "Żółty";
            case 7:
                return "Niebieski";
            case 8:
                return "Biały";
            case 9:
                return "Zielony";
            case 10:
                return "Herbata";
            case 11:
                return "Woda";
            case 12:
                return "Kawa";
            case 13:
                return "Mleko";
            case 14:
                return "Piwo";
            case 15:
                return "Koty";
            case 16:
                return "Ptaki";
            case 17:
                return "Konie";
            case 18:
                return "Psy";
            case 19:
                return "Ryby";
            case 20:
                return "Cygara";
            case 21:
                return "Papierosy light";
            case 22:
                return "Bez filtra";
            case 23:
                return "Fajka";
            default:
                return "Mentol";
        }
    }
    public static ArrayList<ArrayList<Integer>> getDomains(){
        ArrayList<ArrayList<Integer>> domains = new ArrayList<>(25);
        for(int i = 0; i < 25; i++){
            domains.add(new ArrayList<>(5));
            for(int home = 0; home < 5; home ++){
                domains.get(i).add(home);
            }
        }
        return domains;
    }
    public static ArrayList<ArrayList<Constraint<Integer>>> getConstraints(){
        ArrayList<ArrayList<Constraint<Integer>>> constraints = new ArrayList<>(25);
        for(int i = 0; i < 25; i++){
            constraints.add(new ArrayList<>(5));
        }
        constraints.get(0).add(new EqualToValueConstraint(0, 0)); // Norweg w pierwszym domu

        constraints.get(4).add(new EqualConstraint(4, 5)); //Anglik mieszka w czerwonym domu
        constraints.get(5).add(new EqualConstraint(5, 4)); //Anglik mieszka w czerwonym domu

        constraints.get(8).add(new DiffConstraint(8, 9, 1)); // Zielony po lewej od białego
        constraints.get(9).add(new DiffConstraint(9, 8, -1)); // Zielony po lewej od białego

        constraints.get(1).add(new EqualConstraint(1, 10)); //Duńczyk pija herbatę
        constraints.get(10).add(new EqualConstraint(10, 1)); //Duńczyk pija herbatę

        constraints.get(15).add(new AbsDiffConstraint(15, 21, 1)); // Palacz light obok hodowcy kotów
        constraints.get(21).add(new AbsDiffConstraint(21, 15, 1)); // Palacz light obok hodowcy kotów

        constraints.get(6).add(new EqualConstraint(6, 20)); // Mieszkaniec żółtego domu pali cygara
        constraints.get(20).add(new EqualConstraint(20, 6)); // Mieszkaniec żółtego domu pali cygara

        constraints.get(2).add(new EqualConstraint(2, 23)); // Niemiec pali fajkę
        constraints.get(23).add(new EqualConstraint(23, 2)); // Niemiec pali fajkę

        constraints.get(13).add(new EqualToValueConstraint(13, 2)); // Mieszkaniec środkowego domu pija mleko

        constraints.get(11).add(new AbsDiffConstraint(11, 21, 1)); // Palacz light ma sąsiada, który pija wodę
        constraints.get(21).add(new AbsDiffConstraint(21, 11, 1)); // Palacz light ma sąsiada, który pija wodę

        constraints.get(16).add(new EqualConstraint(16, 22)); // Palacz bez filtra hoduje ptaki
        constraints.get(22).add(new EqualConstraint(22, 16)); // Palacz bez filtra hoduje ptaki

        constraints.get(3).add(new EqualConstraint(3, 18)); //Szwed hoduje psy
        constraints.get(18).add(new EqualConstraint(18, 3)); //Szwed hoduje psy


        constraints.get(0).add(new AbsDiffConstraint(0, 7, 1)); // Norweg obok niebieskiego domu
        constraints.get(7).add(new AbsDiffConstraint(7, 0, 1)); // Norweg obok niebieskiego domu

        constraints.get(6).add(new AbsDiffConstraint(6, 17, 1)); // Hodowca koni obok żółtego domu
        constraints.get(17).add(new AbsDiffConstraint(17, 6, 1)); // Hodowca koni obok żółtego domu

        constraints.get(14).add(new EqualConstraint(14, 24)); // Palacz mentolowych pija piwo
        constraints.get(24).add(new EqualConstraint(24, 14)); // Palacz mentolowych pija piwo

        constraints.get(9).add(new EqualConstraint(9, 12)); // W zielonym domu pija się kawę
        constraints.get(12).add(new EqualConstraint(12, 9)); // W zielonym domu pija się kawę

        for(int i = 0; i < 5; i++){
            constraints.get(i).add(new MassNotEqualConstraint(i, new int[]{0, 1, 2, 3, 4}));
        }
        for(int i = 5; i < 10; i++){
            constraints.get(i).add(new MassNotEqualConstraint(i, new int[]{5, 6, 7, 8, 9}));
        }
        for(int i = 10; i < 15; i++){
            constraints.get(i).add(new MassNotEqualConstraint(i, new int[]{10, 11, 12, 13, 14}));
        }
        for(int i = 15; i < 20; i++){
            constraints.get(i).add(new MassNotEqualConstraint(i, new int[]{15, 16, 17, 18, 19}));
        }
        for(int i = 20; i < 25; i++){
            constraints.get(i).add(new MassNotEqualConstraint(i, new int[]{20, 21, 22, 23, 24}));
        }
        return constraints;
    }

    public static void main(String[] args) {
        EinsteinSolver solver = new EinsteinSolver();
        Heuristic hn = new NaiveHeuristic();
        Heuristic hl = new LeastConstraintsHeuristic(EinsteinSolver.getConstraints());
        Heuristic hm = new MostConstraintsHeuristic(EinsteinSolver.getConstraints());

        solver.solveBacktracking(hl);

    }
}
