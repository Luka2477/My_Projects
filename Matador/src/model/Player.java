package model;

import javafx.scene.paint.Color;

public class Player {

    private final String name;
    private final Color color;
    private int balance;

    public Player (String name, Color color) {
        this.name = name;
        this.color = color;
        this.balance = 1500;
    }

    public String getName () {
        return this.name;
    }

    public Color getColor () {
        return this.color;
    }

    public void addBalance (int amount) {
        if (amount > 0) {
            this.balance += amount;
        }
    }

    public void takeBalance (int amount) {
        if (amount > 0) {
            this.balance -= amount;
        }
    }

    public int getBalance () {
        return this.balance;
    }
}
