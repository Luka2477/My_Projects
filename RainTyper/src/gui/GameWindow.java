package gui;

import application.model.Difficulty;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class GameWindow extends Stage {

    private Difficulty difficulty;
    private int duration;
    private int width;

    private Pane wordsPane;
    private TextField txfWord;
    private Label lblWordsPerMinute, lblAccuracy, lblPasses, lblMisses;

    private ArrayList<String> wordsList = new ArrayList<>();
    private ArrayList<String> words = new ArrayList<>();

    private int missed = 0, passed = 0, backspaces = 0, correctChars = 0;

    private LocalTime startTime;

    public GameWindow(Difficulty difficulty, int duration, int width) {
        this.difficulty = difficulty;
        this.duration = duration;
        this.width = width;

        this.startTime = LocalTime.now();

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
        this.wordsPane.setPrefWidth(this.width * this.difficulty.getMaxLengthWord() * 10);
        this.wordsPane.setPrefHeight(600);
        this.wordsPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0))));
        pane.add(this.wordsPane, 0, 0);

        this.txfWord = new TextField();
        this.txfWord.setPrefWidth(Difficulty.HARD.getMaxLengthWord() * 7);
        this.txfWord.setOnKeyPressed(event -> handleTxfKeyPressed(event.getCode()));
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

    private void handleTxfKeyPressed (KeyCode key) {
        if (key == KeyCode.BACK_SPACE) {
            this.backspaces++;

            this.updateStats();
        }else if (key == KeyCode.SPACE || key == KeyCode.ENTER) {
            String input = this.txfWord.getText().trim();
            boolean passed = this.words.remove(input);

            if (passed) {
                this.correctChars += input.length();
                this.passed++;
            } else {
                this.missed++;
            }

            this.words.clear();
            this.updateStats();
        }
    }

    private void updateStats () {
        double acc = (double) this.backspaces / this.correctChars * 100;
        double wpm = (double) this.passed / ChronoUnit.SECONDS.between(this.startTime, LocalTime.now()) / 60;

        this.lblMisses.setText("Miss: " + this.missed);
        this.lblPasses.setText("Pass: " + this.passed);
        this.lblAccuracy.setText(String.format("Acc: %3.2f%%", acc != 0 ? acc : 100.0));
        this.lblWordsPerMinute.setText(String.format("WPM: %.1f", wpm));
    }

    private void initWords () {
        JSONParser jsonParser = new JSONParser();

        try (FileReader reader = new FileReader("raintyper/src/storage/words.json")) {
            Object obj = jsonParser.parse(reader);

            JSONArray words = (JSONArray) obj;

            for (Object word : words) {
                String str = word.toString();

                if (str.length() >= this.difficulty.getMinLengthWord() && str.length() <= this.difficulty.getMaxLengthWord()) {
                    this.wordsList.add(str);
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }

    private void initGame () {
        this.initWords();
    }

    private void loop () {

    }
}
