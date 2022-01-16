package model;

import javafx.scene.canvas.GraphicsContext;

public class Rectangle {

    private final double x, y, width, height;

    public Rectangle(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean collides (Particle particle) {
        return this.x - this.width / 2 <= particle.getX()
                && this.x + this.width / 2 >= particle.getX()
                && this.y - this.height / 2 <= particle.getY()
                && this.y + this.height / 2 >= particle.getY();
    }

    public void draw (GraphicsContext gc) {
        gc.strokeRect(this.x - this.width / 2, this.y - this.height / 2, this.width, this.height);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getWidth() {
        return this.width;
    }

    public double getHeight() {
        return this.height;
    }

}
