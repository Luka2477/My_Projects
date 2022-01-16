package model;

import javafx.scene.canvas.GraphicsContext;

import java.util.ArrayList;

public class QuadTree {

    private final Rectangle boundary;
    private final int capacity;
    private final ArrayList<Particle> particles = new ArrayList<>();
    private QuadTree nw, ne, sw, se;

    public QuadTree(Rectangle boundary, int capacity) {
        this.boundary = boundary;
        this.capacity = capacity;
    }

    public void subdivide () {
        this.nw = new QuadTree(new Rectangle(
                this.boundary.getX() - this.boundary.getWidth() / 4,
                this.boundary.getY() - this.boundary.getHeight() / 4,
                this.boundary.getWidth() / 2,
                this.boundary.getHeight() / 2), (this.capacity > 3) ? this.capacity / 2 : 2);
        this.ne = new QuadTree(new Rectangle(
                this.boundary.getX() + this.boundary.getWidth() / 4,
                this.boundary.getY() - this.boundary.getHeight() / 4,
                this.boundary.getWidth() / 2,
                this.boundary.getHeight() / 2), (this.capacity > 3) ? this.capacity / 2 : 2);
        this.sw = new QuadTree(new Rectangle(
                this.boundary.getX() - this.boundary.getWidth() / 4,
                this.boundary.getY() + this.boundary.getHeight() / 4,
                this.boundary.getWidth() / 2,
                this.boundary.getHeight() / 2), (this.capacity > 3) ? this.capacity / 2 : 2);
        this.se = new QuadTree(new Rectangle(
                this.boundary.getX() + this.boundary.getWidth() / 4,
                this.boundary.getY() + this.boundary.getHeight() / 4,
                this.boundary.getWidth() / 2,
                this.boundary.getHeight() / 2), (this.capacity > 3) ? this.capacity / 2 : 2);
    }

    public boolean insert (Particle particle) {
        if (this.boundary.collides(particle)) {
            if (this.particles.size() < this.capacity) {
                this.particles.add(particle);
                return true;
            } else {
                if (this.nw == null) {
                    this.subdivide();
                }

                return this.nw.insert(particle) || this.ne.insert(particle) || this.sw.insert(particle) || this.se.insert(particle);
            }
        }

        return false;
    }

    public void draw (GraphicsContext gc) {
        this.boundary.draw(gc);
        this.particles.forEach((particle -> particle.draw(gc)));

        if (this.nw != null) {
            this.nw.draw(gc);
            this.ne.draw(gc);
            this.sw.draw(gc);
            this.se.draw(gc);
        }
    }

    public void recalculateParticles () {
        ArrayList<Particle> particles = this.getAllParticles();

        if (this.nw != null) {
            this.nw = null;
            this.ne = null;
            this.sw = null;
            this.se = null;
        }

        particles.forEach(this::insert);
    }

    public ArrayList<Particle> getAllParticles () {
        ArrayList<Particle> particles = new ArrayList<>(this.particles);

        if (this.nw != null) {
            particles.addAll(this.nw.getAllParticles());
            particles.addAll(this.ne.getAllParticles());
            particles.addAll(this.sw.getAllParticles());
            particles.addAll(this.se.getAllParticles());
        }

        return particles;
    }

}
