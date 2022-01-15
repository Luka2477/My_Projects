package gui;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

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

        // TODO Ask for input

        Scene scene = new Scene(pane);
        stage.setScene(scene);
        stage.show();
    }

    private void initContent (Pane pane) {
        Point2D boundstl = new Point2D(this.width / 12, this.height / 24 + this.height / 12);
        Point2D boundsbr = new Point2D(this.width / 12 * 11, this.height / 24 * 23);
        Point2D center = new Point2D(this.width / 2, this.height / 24 + this.height / 2);
        double radius = this.width / 12 * 5;
        double pointRadius = this.width / 250;
        double pi = 0.0;
        Group group = new Group();
        pane.getChildren().add(group);

        // Create background
        Rectangle background = new Rectangle(0, 0, this.width, this.height);
        group.getChildren().add(background);

        // Create bounding box
        Rectangle bounds = new Rectangle(boundstl.getX(), boundstl.getY(), boundsbr.getX() - boundstl.getX(), boundsbr.getY() - boundstl.getY());
        bounds.setFill(Color.TRANSPARENT);
        bounds.setStroke(Color.WHITE);
        bounds.setStrokeWidth(pointRadius);
        group.getChildren().add(bounds);

        // Create circle that we will use to approximate PI
        Circle circle = new Circle(center.getX(), center.getY(), radius);
        circle.setFill(Color.TRANSPARENT);
        circle.setStroke(Color.WHITE);
        circle.setStrokeWidth(pointRadius);
        group.getChildren().add(circle);

        // Text to show approximation
        Text approximation = new Text(String.format("PI = %f", pi));
        approximation.setTextAlignment(TextAlignment.CENTER);
        approximation.setX(this.width / 12);
        approximation.setY(this.height / 12);
        approximation.setFont(new Font(this.width / 24));
        approximation.setFill(Color.WHITE);
        group.getChildren().add(approximation);

        // TODO Create Timeline

        // TODO Generate points in Keyframes

        // TODO Animate the timeline

    }

}
