package MapColorProblem;

import CSP.Heuristics.LeastConstraintsHeuristic;
import CSP.Heuristics.MostConstraintsHeuristic;
import CSP.Heuristics.NaiveHeuristic;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Locale;

public class ColorSolver extends Application {
    public static final String FILENAME = "P20.txt";
    public static final int COLORS = 4;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        primaryStage.setTitle("Hello World");
//        var groups = solveBactrack();
        var groups = solveForward();
//        var groups = drawSolutions(solutions, problem);
        if (groups.size() > 0)
            primaryStage.setScene(new Scene(groups.get(0), 300, 275));
        else
            primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

    private LinkedList<Group> drawSolutions(ArrayList<ArrayList<Integer>> solutions, ColorProblem problem){
        LinkedList<Group> groups = new LinkedList<>();
        for(var solution : solutions){
            Group group = new Group();
            LinkedList<Line> lines = problem.drawLines();
            group.getChildren().addAll(lines);
            LinkedList<Circle> circles = problem.drawPoints(solution);
            group.getChildren().addAll(circles);
            groups.add(group);
        }
        return groups;
    }

    public LinkedList<Group> solveBactrack(){
        ColorProblem problem = ColorProblem.getProblemFromFile(FILENAME);

        var domain = problem.getDomains(COLORS);
        var constraints = problem.getConstraints();
        ArrayList<CSP.BactrackSolver<Integer>> solvers = new ArrayList<>(3);
        CSP.BactrackSolver<Integer> naiveSolver = new CSP.BactrackSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new NaiveHeuristic());
        CSP.BactrackSolver<Integer> mostSolver = new CSP.BactrackSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new MostConstraintsHeuristic(problem.getConstraints()));
        CSP.BactrackSolver<Integer> leastSolver = new CSP.BactrackSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new LeastConstraintsHeuristic(problem.getConstraints()));
        solvers.add(naiveSolver);
        solvers.add(mostSolver);
        solvers.add(leastSolver);
        ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
        for(int i = 0; i < solvers.size(); i++){
            var start = System.currentTimeMillis();
            var s = solvers.get(i).solve();
            var time = System.currentTimeMillis() - start;
            double seconds = (double)time/1000;
            System.out.println(i + ": \nTime: " + String.format(Locale.getDefault(), "%.3f", seconds) + "\nNodes: " + solvers.get(i).getNodes());
            solution = s;
        }
        return drawSolutions(solution, problem);
    }

    public LinkedList<Group> solveForward(){
        ColorProblem problem = ColorProblem.getProblemFromFile(FILENAME);

        var domain = problem.getDomains(COLORS);
        var constraints = problem.getConstraints();
        ArrayList<CSP.ForwardCheckingSolver<Integer>> solvers = new ArrayList<>(3);
        CSP.ForwardCheckingSolver<Integer> naiveSolver = new CSP.ForwardCheckingSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new NaiveHeuristic());
        CSP.ForwardCheckingSolver<Integer> mostSolver = new CSP.ForwardCheckingSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new MostConstraintsHeuristic(problem.getConstraints()));
        CSP.ForwardCheckingSolver<Integer> leastSolver = new CSP.ForwardCheckingSolver<Integer>(problem.getDomains(COLORS), problem.getConstraints(), new LeastConstraintsHeuristic(problem.getConstraints()));
        solvers.add(naiveSolver);
        solvers.add(mostSolver);
        solvers.add(leastSolver);
        ArrayList<ArrayList<Integer>> solution = new ArrayList<>();
        for(int i = 0; i < solvers.size(); i++){
            var start = System.currentTimeMillis();
            var s = solvers.get(i).solve();
            var time = System.currentTimeMillis() - start;
            double seconds = (double)time/1000;
            System.out.println(i + ": \nTime: " + String.format(Locale.getDefault(), "%.3f", seconds) + "\nNodes: " + solvers.get(i).getNodes());
            solution = s;
        }
        return drawSolutions(solution, problem);
    }


    public static void main(String[] args) {
        launch(args);
    }
}
