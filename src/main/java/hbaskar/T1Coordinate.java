package hbaskar;

import java.io.Serializable;

/**
 * This class represents the coordinates of the ball and mouse interaction
 * within a graphical panel. It stores the x and y positions and allows 
 * for their retrieval and update.
 *
 * This class is serializable, enabling it to be used in networked or 
 * persistent storage applications.
 * 
 * It is used in the context of displaying interactive elements like 
 * Bars, Ball, and Chat within a JPanel.
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