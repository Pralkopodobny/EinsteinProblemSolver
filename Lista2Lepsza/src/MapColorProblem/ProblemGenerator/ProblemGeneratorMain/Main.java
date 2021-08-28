package MapColorProblem.ProblemGenerator.ProblemGeneratorMain;

import MapColorProblem.ProblemGenerator.ProblemGenerator;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        ProblemGenerator p = new ProblemGenerator();
        Group g = p.generateProblem(4, 40, 40, "P4.txt");
        primaryStage.setScene(new Scene(g, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
