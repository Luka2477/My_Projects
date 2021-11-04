package gui;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
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

        this.makeBoard = new MakeBoard(stage);
        this.loadBoard = new LoadBoard(stage);
        this.game = new Game(stage);
    }

    //------------------------------------------

    private MakeBoard makeBoard;
    private LoadBoard loadBoard;
    private Game game;

    private void initContent (GridPane pane) {
        pane.setGridLinesVisible(true);

        pane.setPadding(new Insets(20));
        pane.setHgap(10);
        pane.setVgap(10);

        Label lblHeader = new Label("Welcome to Matador");
        lblHeader.setFont(new Font(40));
        pane.add(lblHeader, 0, 0, 3, 1);

        Button btnMakeBoard = new Button("Make Board");
        btnMakeBoard.setFont(new Font(10));
        btnMakeBoard.setOnAction(event -> this.onMakeBoardAction());
        pane.add(btnMakeBoard, 1, 1);

        Button btnLoadBoard = new Button("Load Board");
        btnLoadBoard.setFont(new Font(10));
        btnLoadBoard.setOnAction(event -> this.onLoadBoardAction());
        pane.add(btnLoadBoard, 1, 2);
    }

    //------------------------------------------------

    private void onMakeBoardAction () {
        this.makeBoard.showAndWait();
    }

    private void onLoadBoardAction () {
        this.loadBoard.showAndWait();
    }
}
