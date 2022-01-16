package gui;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import model.Particle;
import model.QuadTree;
import model.Rectangle;

public class GUI extends Application {

    private final double WIDTH = 400, HEIGHT = 400;
    private final int INITIAL_CAPACITY = 10;

    @Override
    public void start (Stage stage) {
        stage.setTitle("Quad Tree");
        Pane root = new Pane();
        root.setStyle("-fx-background-color: black;");

        Canvas canvas = new Canvas(this.WIDTH, this.HEIGHT);
        root.getChildren().add(canvas);

        this.initLoop(canvas.getGraphicsContext2D());

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void initLoop (GraphicsContext gc) {
        QuadTree quadTree = new QuadTree(
                new Rectangle(this.WIDTH / 2, this.HEIGHT / 2, this.WIDTH, this.HEIGHT),
                this.INITIAL_CAPACITY);

        for (int i = 0; i < 100; i++) {
            quadTree.insert(new Particle(3 + Math.random() * (this.WIDTH - 6), 3 + Math.random() * (this.HEIGHT - 6), 2));
        }

        gc.setStroke(Color.WHITE);
        gc.setLineWidth(1);
        gc.setFill(Color.WHITE);

        final long[] lastNanoTime = {System.nanoTime()};
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if ((now - lastNanoTime[0]) / 1e6 > 1000 / 60f) {
                    gc.clearRect(0, 0, WIDTH, HEIGHT);
                    quadTree.getAllParticles().forEach(particle -> particle.move(WIDTH, HEIGHT));
                    quadTree.recalculateParticles();
                    quadTree.draw(gc);

                    lastNanoTime[0] = now;
                }
            }
        };
        timer.start();
    }

}
