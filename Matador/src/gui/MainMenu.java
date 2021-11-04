package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MainMenu extends Application {

    @Override
    public void start (Stage stage) {
        stage.setTitle("Matador - Main Menu");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    //------------------------------------------

    private void initContent (GridPane pane) {
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
    }

}
