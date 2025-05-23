package hbaskar;

/**
 * This class acts as a centralized data repository for storing and managing 
 * the state of a simple Pong-style game. It maintains information about 
 * the ball's position, paddle positions for both server and client players, 
 * direction of the ball, and the role of the current instance (server or client).
 * 
 * It implements the Singleton design pattern to ensure that all components 
 * in the application access and modify a consistent game state.
 * 
 * Game logic, including ball movement and collision detection, is encapsulated 
 * in this class.
 * 
 * @author hbaskar
 * @version 1.1
 */
public class T1DataRepository {

    // Single instance, volatile for safe publication in multi-threaded env
    private static volatile T1DataRepository instance;

    private int ballX;
    private int ballY;
    private int clientPlayerY;
    private int serverPlayerY;
    private int direction;
    private int whoAmI;
    private String message;

    private final T1ChatPanel chatPanel;

    private static final int RIGHT = 0;
    private static final int LEFT = 1;

    public static final int SERVER = 0; 
    public static final int CLIENT = 1;

    // Private constructor so no external instantiation
    private T1DataRepository() {
        ballX = 400;
        ballY = 300;
        clientPlayerY = 250;
        serverPlayerY = 250;
        direction = RIGHT;
        whoAmI = SERVER; // example default SERVER
        this.chatPanel = T1ChatPanel.getInstance();
    }

    // Double-checked locking for thread-safe singleton access
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

    // Getters and setters for variables
    public int getBallX() {
        return ballX;
    }

    public void setBallX(int ballX) {
        this.ballX = ballX;
    }

    public int getBallY() {
        return ballY;
    }

    public void setBallY(int ballY) {
        this.ballY = ballY;
    }

    public int getClientPlayerY() {
        return clientPlayerY;
    }

    public void setClientPlayerY(int y) {
        if (y < 0) {
            y = 0;
        } else if (y > 550) {
            y = 550;
        }
        clientPlayerY = y;
    }

    public int getServerPlayerY() {
        return serverPlayerY;
    }

    public void setServerPlayerY(int y) {
        if (y < 0) {
            y = 0;
        } else if (y > 550) {
            y = 550;
        }
        serverPlayerY = y;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getWhoAmI() {
        return whoAmI;
    }

    public void setWhoAmI(int whoAmI) {
        this.whoAmI = whoAmI;
    }

    public void setMsg(String input){
        this.message = input;
        chatPanel.addMessage(message);
    }

    public String getMsg(){
        String temp = message;
        message ="";
        return temp;
    }

    // Move the ball logic
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
        // Server paddle collision on left side (x=10 or 20)
        if (ballX <= 20 &&
            ballY >= serverPlayerY &&
            ballY <= serverPlayerY + 50)
            return true;

        // Client paddle collision on right side (x=780)
        if (ballX >= 780 &&
            ballY >= clientPlayerY &&
            ballY <= clientPlayerY + 50)
            return true;

        return false;
    }

    // Coordinates setter
    public void setCoordinates(int x, int y) {
        this.ballX = x;
        this.ballY = y;
    }
}


