package gui;

import application.model.Difficulty;
import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class StartWindow extends Application {

    @Override
    public void start (Stage stage) {
        stage.setTitle("Rain Typer");
        GridPane pane = new GridPane();
        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private ToggleGroup tggDifficulty;
    private TextField txfDuration, txfWidth;

    private void initContent (GridPane pane) {
        pane.setPadding(new Insets(10));
        pane.setVgap(10);
        pane.setHgap(10);
        pane.setGridLinesVisible(true);

        Label lblDifficulty = new Label("Difficulty:");
        pane.add(lblDifficulty, 0, 0);

        this.tggDifficulty = new ToggleGroup();
        RadioButton rdbEasy = new RadioButton("Easy (1-4 chars)");
        rdbEasy.setUserData(Difficulty.EASY);
        rdbEasy.setToggleGroup(this.tggDifficulty);
        rdbEasy.setSelected(true);
        rdbEasy.requestFocus();
        RadioButton rdbMedium = new RadioButton("Medium (5-7 chars)");
        rdbMedium.setUserData(Difficulty.MEDIUM);
        rdbMedium.setToggleGroup(this.tggDifficulty);
        RadioButton rdbHard = new RadioButton("Hard (8-... chars)");
        rdbHard.setUserData(Difficulty.HARD);
        rdbHard.setToggleGroup(this.tggDifficulty);
        VBox rdbBox = new VBox(rdbEasy, rdbMedium, rdbHard);
        pane.add(rdbBox, 1, 0);

        Label lblDuration = new Label("Duration (secs):");
        pane.add(lblDuration, 0, 1);

        this.txfDuration = new TextField();
        pane.add(this.txfDuration, 1, 1);

        Label lblWidth = new Label("Width (words):");
        pane.add(lblWidth, 0, 2);

        this.txfWidth = new TextField();
        pane.add(this.txfWidth, 1, 2);

        Button btnStart = new Button("Start");
        btnStart.setOnAction(event -> this.startAction());
        GridPane.setHalignment(btnStart, HPos.CENTER);
        pane.add(btnStart, 0, 3, 2, 1);
    }

    private void startAction () {
        GameWindow game = new GameWindow(
                (Difficulty) this.tggDifficulty.getSelectedToggle().getUserData(),
                Integer.parseInt(this.txfDuration.getText().trim()),
                Integer.parseInt(this.txfWidth.getText().trim()));

        game.showAndWait();
    }
}
