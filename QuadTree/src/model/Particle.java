package model;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public class Particle {

    private final double MAX_SPEED = 0.1;

    private double x, y, radius;
    private Point2D velocity = new Point2D(
            -this.MAX_SPEED + Math.random() * this.MAX_SPEED * 2,
            -this.MAX_SPEED + Math.random() * this.MAX_SPEED * 2);

    public Particle(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
    }

    public void move (double width, double height) {
        this.x += this.velocity.getX();
        this.y += this.velocity.getY();

        if (this.x - this.radius <= 0) {
            this.x = this.radius;
            this.velocity = new Point2D(this.velocity.getX() * -1, this.velocity.getY());
        } else if (this.x + this.radius >= width) {
            this.x = width - this.radius;
            this.velocity = new Point2D(this.velocity.getX() * -1, this.velocity.getY());
        }

        if (this.y - this.radius <= 0) {
            this.y = this.radius;
            this.velocity = new Point2D(this.velocity.getX(), this.velocity.getY() * -1);
        } else if (this.y + this.radius >= height) {
            this.y = height - this.radius;
            this.velocity = new Point2D(this.velocity.getX(), this.velocity.getY() * -1);
        }
    }

    public void draw (GraphicsContext gc) {
        gc.fillOval(this.x, this.y, this.radius * 2, this.radius * 2);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

}
