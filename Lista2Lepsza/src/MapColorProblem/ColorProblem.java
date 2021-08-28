package MapColorProblem;

import CSP.Constraints.Constraint;
import CSP.Constraints.NotEqualConstraint;
import MapColorProblem.ProblemGenerator.Point;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class ColorProblem {
    public static final int SCALE = 10;
    public static final int RADIUS = 4;
    public static final int OFFSET = 20;
    private ColorProblem(){};
    public int[] points;
    private Color[] colors = new Color[]{Color.RED, Color.GREEN, Color.ORANGE, Color.BLUE};
    private Point[] p;
    public boolean[][] connections;
    public static ColorProblem getProblemFromFile(String filename){
        try {
            ColorProblem problem = new ColorProblem();
            Scanner myReader = new Scanner(new File(filename));
            String line = myReader.nextLine();
            int number = Integer.parseInt(line);
            problem.p = new Point[number];
            problem.points = new int[number];
            Arrays.fill(problem.points, -1);
            problem.connections = new boolean[number][number];
            for (int i = 0; i < number; i++) {
                String temp = myReader.nextLine();
                String[] temp2 = temp.split(" ");
                problem.p[i] = new Point(Integer.parseInt(temp2[0]), Integer.parseInt(temp2[1]));
            }
            for (int i = 0; i < number; i++) {
                String temp = myReader.nextLine();
                String[] temp2 = temp.split(" ");
                for (int j = 0; j < number; j++) {
                    problem.connections[i][j] = Integer.parseInt(temp2[j]) == 1;
                }
            }
            return problem;
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(404);
        }
        return null;
    }
    public void shout(){
        var domains = getDomains(4);
        var constraints = getConstraints();
        for(int i = 0; i <connections.length; i++){
            System.out.println(Integer.toString(i) + domains.get(i));
        }
        for(int i = 0; i <connections.length; i++){
            System.out.println(Integer.toString(i)+ ": " + constraints.get(i));
        }

    }

    public LinkedList<Line> drawLines(){
        LinkedList<Line> lines = new LinkedList<>();
        for(int i = 0; i < points.length; i++){
            for(int j = 0; j < i; j++){
                if(connections[i][j]){
                    lines.add(new Line(p[i].x * SCALE + OFFSET, p[i].y * SCALE + OFFSET, p[j].x * SCALE + OFFSET, p[j].y * SCALE + OFFSET));
                }
            }
        }
        return lines;
    }

    public LinkedList<Circle> drawPoints(int[] colors){
        LinkedList<Circle> circles = new LinkedList<>();
        for(int i = 0; i < colors.length; i++){
            Circle c = new Circle(p[i].x * SCALE + OFFSET, p[i].y * SCALE + OFFSET, RADIUS);
            c.setStroke(this.colors[colors[i]]);
            c.setFill(this.colors[colors[i]]);
            circles.add(c);
        }
        return circles;
    }
    public LinkedList<Circle> drawPoints(ArrayList<Integer> colors){
        LinkedList<Circle> circles = new LinkedList<>();
        for(int i = 0; i < colors.size(); i++){
            Circle c = new Circle(p[i].x * SCALE + OFFSET, p[i].y * SCALE + OFFSET, RADIUS);
            c.setStroke(this.colors[colors.get(i)]);
            c.setFill(this.colors[colors.get(i)]);
            circles.add(c);
        }
        return circles;
    }



    public ArrayList<ArrayList<Integer>> getDomains(int colorNumb){
        ArrayList<ArrayList<Integer>> domains = new ArrayList<>(connections.length);
        for(int i = 0; i < connections.length; i++){
            domains.add(new ArrayList<>(colorNumb));
            for(int j = 0; j < colorNumb; j++){
                domains.get(i).add(j);
            }
        }
        return domains;
    }

    public ArrayList<ArrayList<Constraint<Integer>>> getConstraints(){
        ArrayList<ArrayList<Constraint<Integer>>> constraints = new ArrayList<>(connections.length);
        for(int i = 0; i < connections.length; i++){
            constraints.add(new ArrayList<>());

        }
        for(int i = 0; i < connections.length; i++){
            for(int j = i; j <connections[i].length; j++){
                if(connections[i][j]) {
                    constraints.get(i).add(new NotEqualConstraint(i, j));
                    constraints.get(j).add(new NotEqualConstraint(j, i));
                }
            }
        }
        return constraints;
    }


    public static void main(String[] args) {
        ColorProblem problem = ColorProblem.getProblemFromFile("P20.txt");
        problem.shout();
    }
}
