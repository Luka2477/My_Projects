package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Scanner;

public class GUI extends Application {

    private double width, height;

    @Override
    public void start (Stage stage) {
        this.width = 600;
        this.height = 600;

        stage.setTitle("Approximating PI");
        Pane pane = new Pane();

        Canvas canvas = new Canvas();
        canvas.setWidth(this.width);
        canvas.setHeight(this.height);
        pane.getChildren().add(canvas);

        this.initContent(canvas.getGraphicsContext2D());

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent (GraphicsContext context) {
        Point2D boundCoords = new Point2D(this.width / 12, this.height / 24 + this.height / 12);
        Point2D boundSize = new Point2D(this.width / 12 * 10, this.height / 24 * 20);
        Point2D center = new Point2D(this.width / 2, this.height / 24 + this.height / 2);
        double radius = this.width / 12 * 5;
        double pointRadius = this.width / 500;
        CountContext count = new CountContext();

        // Initialize drawing
        context.setLineWidth(pointRadius);
        context.setStroke(Color.WHITE);
        context.setFont(new Font(this.width / 24));

        // Ask for input
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many points (int): ");
        int points = Integer.parseInt(scanner.nextLine());

        System.out.print("How many milliseconds delay (double): ");
        double delay = Double.parseDouble(scanner.nextLine());

        // Create background
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, this.width, this.height);

        // Create bounding box
        context.strokeRect(boundCoords.getX(), boundCoords.getY(), boundSize.getX(), boundSize.getY());

        // Create circle that we will use to approximate PI
        context.strokeOval(center.getX() - radius, center.getY() - radius, radius * 2, radius * 2);

        // Create Timeline
        Timeline timeline = new Timeline();

        // Generate points in Keyframes
        for (int i = 0; i < points; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * delay), event -> {
                Point2D pointCoords = new Point2D(
                        boundCoords.getX() + Math.random() * boundSize.getX(),
                        boundCoords.getY() + Math.random() * boundSize.getY());

                if (center.distance(pointCoords) <= radius) {
                    context.setFill(Color.DARKRED);
                    count.addX(1);
                } else {
                    context.setFill(Color.DARKBLUE);
                }

                context.fillOval(pointCoords.getX() - pointRadius, pointCoords.getY() - pointRadius, pointRadius * 2, pointRadius * 2);
                count.addY(1);

                context.setFill(Color.BLACK);
                context.fillRect(boundCoords.getX(), 0, boundSize.getX(), boundCoords.getY() - pointRadius);
                context.setFill(Color.WHITE);
                context.fillText(String.format("PI is approximately %.15f", 4 * count.getX() / count.getY()), this.width / 12, this.height / 12);
            }));
        }

        // Animate the timeline
        timeline.setCycleCount(1);
        timeline.play();
    }

}
