package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class GUI extends Application {

    @Override
    public void start (Stage stage) {
        stage.setTitle("Factorial Tree");
        Pane pane = new Pane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent (Pane pane) {

    }

}
