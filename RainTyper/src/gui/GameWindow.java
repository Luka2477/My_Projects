package gui;

import application.model.Difficulty;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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
import java.util.*;

public class GameWindow extends Stage {

    private Difficulty difficulty;
    private int duration;
    private int width;

    private Pane wordsPane;
    private TextField txfWord;
    private Label lblWordsPerMinute, lblAccuracy, lblPasses, lblMisses;

    private ArrayList<String> wordsList = new ArrayList<>();
    private ArrayList<Label> words = new ArrayList<>();

    private int missed = 0, passed = 0, backspaces = 0, correctChars = 0;

    private LocalTime startTime;
    private Timer timer;

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
        pane.setGridLinesVisible(false);

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
        statsBox.setPrefWidth(500);
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
            boolean passed = false;

            for (Label lbl : this.words) {
                if (lbl.getText().equals(input)) {
                    passed = true;
                    this.wordsPane.getChildren().remove(lbl);
                    this.words.remove(lbl);
                    break;
                }
            }

            if (passed) {
                this.correctChars += input.length();
                this.passed++;
            } else {
                this.missed++;
            }

            this.txfWord.clear();
            this.updateStats();
        }
    }

    private void updateStats () {
        double acc = (double) this.backspaces / this.correctChars * 100;
        double wpm = (double) this.passed / (ChronoUnit.SECONDS.between(this.startTime, LocalTime.now()) / 60.0);
        System.out.println(wpm);
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
        this.initLoop();
    }

    private void initLoop () {
        GameWindow finalThis = this;

        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(finalThis::loop);
            }
        }, 0, 1000/60);
    }

    private void loop () {
        if (this.words.isEmpty() || this.words.get(this.words.size()-1).getLayoutY() > 50) {
            Label lbl = new Label(this.wordsList.get(new Random().nextInt(this.wordsList.size()-1)));
            lbl.setLayoutX(new Random().nextInt((int) (this.wordsPane.getPrefWidth()-lbl.getLayoutBounds().getWidth())));
            lbl.setLayoutY(this.wordsPane.getLayoutY());
            lbl.setTextFill(Color.WHITE);
            this.words.add(lbl);
            this.wordsPane.getChildren().add(this.words.get(this.words.size()-1));
        }

        double moveAmount = this.wordsPane.getPrefHeight() / (this.duration * 60);
        for (Label lbl : this.words) {
            lbl.setLayoutY(lbl.getLayoutY()+moveAmount);
        }

        if (this.words.get(0).getLayoutY() >= this.wordsPane.getLayoutY() + this.wordsPane.getPrefHeight()) {
            this.gameOver();
        }
    }

    private void gameOver () {
        this.timer.cancel();
        this.txfWord.setDisable(true);
    }
}
