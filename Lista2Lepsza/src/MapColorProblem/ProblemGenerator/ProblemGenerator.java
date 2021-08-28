package MapColorProblem.ProblemGenerator;

import javafx.scene.Group;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ProblemGenerator {
    Random rng = new Random();
    public static final int SCALE = 30;
    public Group generateProblem(int pointMaxNumber, int maxX, int maxY, String fileName){
        HashSet<Point> points = new HashSet<>();
        HashSet<Segment> segments = new HashSet<>();
        for(int i = 0; i < pointMaxNumber; i++){
            points.add(new Point(rng.nextInt(maxX), rng.nextInt(maxY)));
        }
        ArrayList<Point> pointsArr = new ArrayList<>(points.size());
        pointsArr.addAll(points);
        int[][] connections = new int[pointsArr.size()][pointsArr.size()];
        for(var row : connections){
            Arrays.fill(row, 0);
        }
        for(int i = 0; i < connections.length; i++){
            for(int j = 0; j < connections[i].length; j++){
                if(i == j) connections[i][j] = -1;
            }
        }
        boolean loop = true;
        while(loop){
            int first = rng.nextInt(pointsArr.size()), sec = rng.nextInt(pointsArr.size());
            if(connections[first][sec] != 0) continue;
            loop = false;
            boolean canBeAdded = true;
            Segment connection = new Segment(pointsArr.get(first), pointsArr.get(sec));
            for(var segment : segments){
                if(segment.collides(connection)){
                    System.out.println(segment + "collides with" + connection);
                    canBeAdded = false;
                    break;
                }
            }
            if(canBeAdded){
                segments.add(connection);
                connections[first][sec] = 1;
                connections[sec][first] = 1;
            }
            else{
                connections[first][sec] = -1;
                connections[sec][first] = -1;
            }
            for (int[] ints : connections) {
                for (int anInt : ints) {
                    if (anInt == 0) {
                        loop = true;
                        break;
                    }
                }
                if (loop) break;
            }

        }
        System.out.println("Powstało " + segments.size() + "Połączeń");
        System.out.println("Koniec");
        Group group = new Group();
        for(var segment : segments){
            Line line = new Line(segment.a.x * SCALE, segment.a.y * SCALE, segment.b.x * SCALE, segment.b.y * SCALE);
            group.getChildren().add(line);
        }
        for(var point : pointsArr){
            Circle c = new Circle(point.x * SCALE, point.y * SCALE, 3);
            group.getChildren().add(c);
        }
        save(pointsArr, connections, fileName);
        return group;

    }

    public static void main(String[] args) {
        ProblemGenerator gen = new ProblemGenerator();
        Group g = gen.generateProblem(4, 100, 100, "a");

    }

    public void save(List<Point> array, int[][] connections, String filename){
        try{
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(Integer.toString(array.size()));
            writer.newLine();
            for(var point : array){
                writer.write(Integer.toString(point.x) + " " + Integer.toString(point.y));
                writer.newLine();
            }
            for(int i = 0; i < connections.length; i++){
                for(int j = 0; j < connections[i].length; j++){
                    writer.write(Integer.toString(connections[i][j] == 1? 1 : 0) + " ");
                }
                writer.newLine();
            }
            writer.close();
        }
        catch(IOException e){
            e.printStackTrace();
            System.exit(404);
        }
    }
}
