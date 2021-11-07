package model.tiles;

import javafx.scene.paint.Color;
import model.Player;

public class Property extends Tile {

    private Color color;
    private int price;
    private int mortgage;
    private final int[] rent;

    private Player owner;
    private int houses;
    private int housePrice;
    private boolean hotel;
    private int hotelPrice;

    public Property (String header, Color color, int price, int[] rent, int housePrice, int hotelPrice) {
        super(header);

        this.color = color;
        this.price = price;
        this.mortgage = price / 2;
        this.rent = rent;

        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;
    }

    public void setColor (Color color) {
        this.color = color;
    }

    public Color getColor () {
        return this.color;
    }

    public void setPrice (int price) {
        this.price = price;
        this.mortgage = price / 2;
    }

    public int getPrice () {
        return this.price;
    }

    public int getMortgage () {
        return this.mortgage;
    }

    public void setRent (int index, int value) {
        if (index < this.rent.length) {
            this.rent[index] = value;
        }
    }

    public void setRent (int[] arr) {
        if (arr.length == this.rent.length) {
            System.arraycopy(arr, 0, this.rent, 0, arr.length);
        }
    }

    public int getRent (int index) {
        if (index < this.rent.length) {
            return this.rent[index];
        }

        return 0;
    }

    public int[] getRent () {
        return this.rent.clone();
    }

    public Player getOwner () {
        return this.owner;
    }

    public int getHouses () {
        return this.houses;
    }

    public void setHousePrice (int price) {
        this.housePrice = price;
    }

    public int getHousePrice () {
        return this.housePrice;
    }

    public boolean hasHotel () {
        return this.hotel;
    }

    public void setHotelPrice (int price) {
        this.hotelPrice = price;
    }

    public int getHotelPrice () {
        return this.hotelPrice;
    }

    //--------------------------------------------------------------------

    public boolean chargeRent (Player player) {
        int rent = this.rent[this.houses + (this.hotel ? 1 : 0)];
        player.takeBalance(rent);

        return player.getBalance() > 0;
    }

    /**
     * Purchase the property for the stored price
     * @param player the player that wants to purchase the property
     * @return 0 if successful, 1 if not enough balance, 2 if property is already owned
     */
    public int purchaseProperty (Player player) {
        return this.purchaseProperty(player, this.price);
    }

    /**
     * Purchase the property for a given price
     * @param player the player that wants to purchase the property
     * @param price the given price for the property
     * @return 0 if successful, 1 if not enough balance, 2 if property is already owned
     */
    public int purchaseProperty (Player player, int price) {
        if (this.owner != null) return 2;

        player.takeBalance(price);

        if (player.getBalance() > 0) {
            this.owner = player;
            return 0;
        } else {
            player.addBalance(price);
            return 1;
        }
    }

    /**
     * Purchase a house for the property
     * @param player the player that wants to purchase the house
     * @return 0 if successful, 1 if not enough balance, 2 if too many houses
     */
    public int purchaseHouse (Player player) {
        if (this.houses >= 4) return 2;
        player.takeBalance(this.housePrice);

        if (player.getBalance() > 0) {
            this.houses++;
            return 0;
        } else {
            player.addBalance(this.hotelPrice);
            return 1;
        }
    }

    /**
     * Purchase a hotel for the property
     * @param player the player that wants to purchase the hotel
     * @return 0 if successful, 1 if not enough balance, 2 if not enough houses, 3 if hotel already exists
     */
    public int purchaseHotel (Player player) {
        if (this.hotel) return 3;
        if (this.houses != 4) return 2;

        player.takeBalance(this.hotelPrice);

        if (player.getBalance() > 0) {
            this.hotel = true;
            return 0;
        } else {
            player.addBalance(this.hotelPrice);
            return 1;
        }
    }
}
