package gui;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class MakeBoard extends Stage {

    public MakeBoard (Stage owner) {
        this.initOwner(owner);
        this.setTitle("Matador - Make Board");

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
