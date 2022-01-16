package gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
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
        pane.setPrefSize(this.width, this.height);

        this.initContent(pane);

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent (Pane pane) {
        Point2D boundCoords = new Point2D(this.width / 12, this.height / 24 + this.height / 12);
        Point2D boundSize = new Point2D(this.width / 12 * 10, this.height / 24 * 20);
        Point2D center = new Point2D(this.width / 2, this.height / 24 + this.height / 2);
        double radius = this.width / 12 * 5;
        double pointRadius = this.width / 500;
        CountContext count = new CountContext();
        Group group = new Group();
        pane.getChildren().add(group);

        // Ask for input
        Scanner scanner = new Scanner(System.in);

        System.out.print("How many points (int): ");
        int points = Integer.parseInt(scanner.nextLine());

        System.out.print("How many milliseconds delay (double): ");
        double delay = Double.parseDouble(scanner.nextLine());

        // Create background
        Rectangle background = new Rectangle(0, 0, this.width, this.height);
        group.getChildren().add(background);

        // Create bounding box
        Rectangle bound = new Rectangle(boundCoords.getX(), boundCoords.getY(), boundSize.getX(), boundSize.getY());
        bound.setFill(Color.TRANSPARENT);
        bound.setStroke(Color.WHITE);
        bound.setStrokeWidth(pointRadius);
        group.getChildren().add(bound);

        // Create circle that we will use to approximate PI
        Circle circle = new Circle(center.getX(), center.getY(), radius);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(pointRadius);
        group.getChildren().add(circle);

        // Text to show approximation
        Text approximation = new Text();
        approximation.setTextAlignment(TextAlignment.CENTER);
        approximation.setX(this.width / 12);
        approximation.setY(this.height / 12);
        approximation.setFont(new Font(this.width / 24));
        approximation.setFill(Color.WHITE);
        group.getChildren().add(approximation);

        // Create Timeline
        Timeline timeline = new Timeline();

        // Generate points in Keyframes
        for (int i = 0; i < points; i++) {
            timeline.getKeyFrames().add(new KeyFrame(Duration.millis(i * delay), event -> {
                Point2D pointCoords = new Point2D(
                        boundCoords.getX() + Math.random() * boundSize.getX(),
                        boundCoords.getY() + Math.random() * boundSize.getY());
                Circle point = new Circle(pointCoords.getX(), pointCoords.getY(), pointRadius);

                if (center.distance(pointCoords.getX(), pointCoords.getY()) <= radius) {
                    point.setFill(Color.DARKRED);
                    count.addX(1);
                } else {
                    point.setFill(Color.DARKBLUE);
                }
                count.addY(1);

                group.getChildren().add(point);

                approximation.setText(String.format("PI is approximately %.15f", 4 * count.getX() / count.getY()));
            }));
        }

        // Animate the timeline
        timeline.setCycleCount(1);
        timeline.play();
    }

}
