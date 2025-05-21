package hbaskar;

import java.io.Serializable;

/**
 * This class is a simple JPanel that will be used to display the Bars, Ball, and Chat.
 *
 * @author hbaskar
 * @version 1.1
 */



public class T1Coordinate implements Serializable {

    private int mouseX;
    private int mouseY;

    public T1Coordinate(int mouseX, int mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public T1Coordinate() {
        this(0, 0);
    }


    public int getX() {
        return mouseX;
    }

    public int getY() {
        return mouseY;
    }


    public void setBallX(int mouseX) {this.mouseX = mouseX;}

    public void setBallY(int mouseX) {this.mouseY = mouseY;}


}