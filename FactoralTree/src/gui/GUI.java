package gui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Scanner;

public class GUI extends Application {

    private double width, height, angleDec, anglePlay, lengthDec;

    @Override
    public void start (Stage stage) {
        this.width = 4800;
        this.height = 4800;

        stage.setTitle("Factorial Tree");
        PannablePane pane = new PannablePane();
        pane.setMaxSize(this.width / 6, this.height / 6);

        Canvas canvas = new Canvas();
        canvas.setWidth(this.width);
        canvas.setHeight(this.height);
        canvas.layoutXProperty().set(-this.width / 2 + this.width / 12);
        canvas.layoutYProperty().set(-this.height + this.height / 6);
        pane.getChildren().add(canvas);

        this.initTree(canvas.getGraphicsContext2D());

        Scene scene = new Scene(pane);

        SceneGestures sceneGestures = new SceneGestures(pane);
        scene.addEventFilter( MouseEvent.MOUSE_PRESSED, sceneGestures.getOnMousePressedEventHandler());
        scene.addEventFilter( MouseEvent.MOUSE_DRAGGED, sceneGestures.getOnMouseDraggedEventHandler());
        scene.addEventFilter( ScrollEvent.ANY, sceneGestures.getOnScrollEventHandler());

        stage.setScene(scene);
        stage.show();
    }

    private void initTree (GraphicsContext context) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many iterations (int): ");
        int iterations = Integer.parseInt(scanner.nextLine());

        System.out.print("Initial stem width (double): ");
        double stemWidth = Double.parseDouble(scanner.nextLine());

        System.out.print("Initial stem length (double): ");
        double stemLength = Double.parseDouble(scanner.nextLine());

        System.out.print("Length decrement as percentage (int): ");
        this.lengthDec = Integer.parseInt(scanner.nextLine()) * 0.01;

        System.out.print("Angle decrement (double): ");
        this.angleDec = Math.toRadians(Double.parseDouble(scanner.nextLine()));

        System.out.print("Should the angle have play (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            System.out.print("How much play (double): ");
            this.anglePlay = Math.toRadians(Double.parseDouble(scanner.nextLine()));
        }

        context.setStroke(Color.BLACK);
        this.makeBranch(context, this.width / 2, this.height, stemWidth, stemLength, Math.PI / 2, iterations);
    }

    private void makeBranch (GraphicsContext context, double x, double y, double branchWidth, double branchLength, double angle, int iterationsLeft) {
        double newX = x - Math.cos(angle) * branchLength;
        double newY = y - Math.sin(angle) * branchLength;

        context.setLineWidth(branchWidth);
        context.strokeLine(x, y, newX, newY);

        if (iterationsLeft > 0) {
            double newBranchLength = branchLength * (1 - this.lengthDec);
            double newBranchWidth = branchWidth / branchLength * newBranchLength;
            double newAngleLeft = angle - (this.angleDec + (Math.random() - .5) * this.anglePlay * 2);
            double newAngleRight = angle + (this.angleDec + (Math.random() - .5) * this.anglePlay * 2);

            makeBranch(context, newX, newY, newBranchWidth, newBranchLength, newAngleLeft, iterationsLeft - 1);
            makeBranch(context, newX, newY, newBranchWidth, newBranchLength, newAngleRight, iterationsLeft - 1);
        }
    }

}
