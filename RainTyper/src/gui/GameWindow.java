package gui;

import application.model.Difficulty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class GameWindow extends Stage {

    private Difficulty difficulty;
    private int duration;
    private int width;

    private Pane wordsPane;
    private TextField txfWord;
    private Label lblWordsPerMinute, lblAccuracy, lblPasses, lblMisses;

    public GameWindow(Difficulty difficulty, int duration, int width) {
        this.difficulty = difficulty;
        this.duration = duration;
        this.width = width;

        this.setTitle("Rain Typer");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        this.setScene(scene);
    }

    private void initContent (GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setGridLinesVisible(true);

        this.wordsPane = new Pane();
        this.wordsPane.setPrefWidth(this.width * (this.difficulty == Difficulty.EASY ? 40 : (this.difficulty == Difficulty.MEDIUM ? 70 : 150)));
        this.wordsPane.setPrefHeight(600);
        this.wordsPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0))));
        pane.add(this.wordsPane, 0, 0);

        this.txfWord = new TextField();
        this.txfWord.setPrefWidth(150);
        this.lblWordsPerMinute = new Label("WPM: 0");
        this.lblAccuracy = new Label("Acc: 0.0%");
        this.lblPasses = new Label("Pass: 0");
        this.lblMisses = new Label("Miss: 0");
        HBox statsBox = new HBox(this.txfWord, this.lblWordsPerMinute, this.lblAccuracy, this.lblPasses, this.lblMisses);
        statsBox.setAlignment(Pos.CENTER);
        statsBox.setSpacing(10);
        GridPane.setHalignment(statsBox, HPos.CENTER);
        pane.add(statsBox, 0, 1);

        this.initGame();
    }

    private void initGame () {

    }
}
