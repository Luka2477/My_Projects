package gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.util.Scanner;

public class GUI extends Application {

    private double width, height, angleDec, anglePlay, lengthDec;

    @Override
    public void start (Stage stage) {
        this.width = 600;
        this.height = 600;

        stage.setTitle("Factorial Tree");
        Pane pane = new Pane();
        pane.setPrefSize(this.width, this.height);

        this.initTree(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initTree (Pane pane) {
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

        Group group = new Group();
        this.makeBranch(group, this.width / 2, this.height, stemWidth, stemLength, Math.PI / 2, iterations);

        pane.getChildren().add(group);
    }

    private void makeBranch (Group group, double x, double y, double branchWidth, double branchLength, double angle, int iterationsLeft) {
        double newX = x - Math.cos(angle) * branchLength;
        double newY = y - Math.sin(angle) * branchLength;

        Line branch = new Line(x, y, newX, newY);
        branch.setStrokeWidth(branchWidth);
        group.getChildren().add(branch);

        if (iterationsLeft > 0) {
            double newBranchLength = branchLength * (1 - this.lengthDec);
            double newBranchWidth = branchWidth / branchLength * newBranchLength;
            double newAngleLeft = angle - (this.angleDec + (Math.random() - .5) * this.anglePlay * 2);
            double newAngleRight = angle + (this.angleDec + (Math.random() - .5) * this.anglePlay * 2);

            makeBranch(group, newX, newY, newBranchWidth, newBranchLength, newAngleLeft, iterationsLeft - 1);
            makeBranch(group, newX, newY, newBranchWidth, newBranchLength, newAngleRight, iterationsLeft - 1);
        }
    }

}
