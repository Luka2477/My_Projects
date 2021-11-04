package model.tiles;

import javafx.scene.paint.Color;

public class Property extends Tile {

    private Color color;
    private int price;

    public Property (String header, Color color, int price) {
        super(header);

        this.color = color;
        this.price = price;
    }
}
