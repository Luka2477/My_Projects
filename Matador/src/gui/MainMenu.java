package gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import storage.GlobalSettings;

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
        lblHeader.setFont(GlobalSettings.FONT_HEADER);
        pane.add(lblHeader, 0, 0);

        Button btnMakeBoard = new Button("Make Board");
        btnMakeBoard.setFont(GlobalSettings.FONT_BUTTON);
        btnMakeBoard.setOnAction(event -> this.onMakeBoardAction());
        GridPane.setHalignment(btnMakeBoard, HPos.CENTER);
        pane.add(btnMakeBoard, 0, 1);

        Button btnLoadBoard = new Button("Load Board");
        btnLoadBoard.setFont(GlobalSettings.FONT_BUTTON);
        btnLoadBoard.setOnAction(event -> this.onLoadBoardAction());
        GridPane.setHalignment(btnLoadBoard, HPos.CENTER);
        pane.add(btnLoadBoard, 0, 2);
    }

    //------------------------------------------------

    private void onMakeBoardAction () {
        this.makeBoard.showAndWait();
    }

    private void onLoadBoardAction () {
        this.loadBoard.showAndWait();
    }
}
