package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Game extends Stage {

    public Game (Stage owner) {
        this.initOwner(owner);
        this.setTitle("Matador - Game");

        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    //-------------------------------------------------------

    private void initContent (GridPane pane) {
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);
    }

}
