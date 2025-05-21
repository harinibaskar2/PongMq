package hbaskar;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;



/**

 *
 * @author hbaskar
 * @version 1.1
 */
public class T1DataRepository {
    private static T1DataRepository instance;

    private int ballX;
    private int ballY;
    private int clientPlayerY;
    private int serverPlayerY;
    private int direction;
    private int whoAmI;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    private T1DataRepository() {
   
        ballX = 400;
        ballY = 300;
        clientPlayerY = 250;
        serverPlayerY = 250;
        direction = RIGHT;
        whoAmI = 0; // example default SERVER
    }

    public static T1DataRepository getInstance() {
        if (instance == null) {
            synchronized (T1DataRepository.class) {
                if (instance == null) {
                    instance = new T1DataRepository();
                }
            }
        }
        return instance;
    }

    // Getters and setters

    public int getBallX() { return ballX; }
    public void setBallX(int ballX) { this.ballX = ballX; }

    public int getBallY() { return ballY; }
    public void setBallY(int ballY) { this.ballY = ballY; }

    public int getClientPlayerY() { return clientPlayerY; }
    public void setClientPlayerY(int y) { clientPlayerY = y; }

    public int getServerPlayerY() { return serverPlayerY; }
    public void setServerPlayerY(int y) { serverPlayerY = y; }

    public int getDirection() { return direction; }
    public void setDirection(int direction) { this.direction = direction; }

    public int getWhoAmI() { return whoAmI; }
    public void setWhoAmI(int whoAmI) { this.whoAmI = whoAmI; }


    public void moveBall() {
        if (direction == RIGHT)
            ballX += 10;
        else
            ballX -= 10;

        if (ballX >= 800 || ballX <= 0)
            ballX = 400;

        if (collision()) {
            direction = (direction == RIGHT) ? LEFT : RIGHT;
        }
    }

    private boolean collision() {
        if (ballX == 20 &&
            ballY >= serverPlayerY &&
            ballY <= serverPlayerY + 50)
            return true;

        if (ballX == 780 &&
            ballY >= clientPlayerY &&
            ballY <= clientPlayerY + 50)
            return true;

        return false;
    }

    // Keep your coordinate publishing if needed
    public void setCoordinates(int x, int y) {

        this.ballX = x;
        this.ballY = y;
        publishCoordinates();
    }

    private void publishCoordinates() {
        // Your existing MQTT publish logic
        coordinate coord = new coordinate(ballX, ballY);
        T1Publisher.publishCoordinate(coord);
    }
}
