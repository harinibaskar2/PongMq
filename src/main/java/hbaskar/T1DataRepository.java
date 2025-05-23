package hbaskar;

/**
 * Centralized data repository for storing and managing the state of a Pong game.
 * Implements Singleton pattern for consistent game state access.
 * All game components access data through this repository.
 * 
 * @author hbaskar
 * @version 1.1
 */
public class T1DataRepository {
    private static volatile T1DataRepository instance;

    // Remove ballDx and ballDy - now handled by T1Ball
    private int ballX;
    private int ballY;
    private int clientPlayerY;
    private int serverPlayerY;
    private int whoAmI;
    private int serverScore;
    private int clientScore;
    private boolean fanCelebration = false;

    public static final int SERVER = 0; 
    public static final int CLIENT = 1;
    
    private static final int FIELD_WIDTH = 800;
    private static final int FIELD_HEIGHT = 600;
    private static final int PADDLE_HEIGHT = 50;
    private static final int BALL_SIZE = 10;

    private T1DataRepository() {
        resetGame();
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

    public void resetGame() {
        ballX = FIELD_WIDTH / 2;
        ballY = FIELD_HEIGHT / 2;
        clientPlayerY = FIELD_HEIGHT / 2 - PADDLE_HEIGHT / 2;
        serverPlayerY = FIELD_HEIGHT / 2 - PADDLE_HEIGHT / 2;
        serverScore = 0;
        clientScore = 0;
        fanCelebration = false;
    }

    // Remove moveBall() and resetBall() - now handled by T1Ball
    // Remove checkPaddleCollision() - now handled by T1Ball

    // Add increment methods for ball to use
    public void incrementServerScore() {
        serverScore++;
    }
    
    public void incrementClientScore() {
        clientScore++;
    }

    // Getters and setters
    public int getBallX() { return ballX; }
    public void setBallX(int ballX) { this.ballX = ballX; }
    public int getBallY() { return ballY; }
    public void setBallY(int ballY) { this.ballY = ballY; }
    public int getBallWidth() { return BALL_SIZE; }
    public int getBallHeight() { return BALL_SIZE; }
    public int getFieldWidth() { return FIELD_WIDTH; }
    public int getFieldHeight() { return FIELD_HEIGHT; }
    public int getClientPlayerY() { return clientPlayerY; }
    public void setClientPlayerY(int y) { 
        this.clientPlayerY = Math.max(0, Math.min(y, FIELD_HEIGHT - PADDLE_HEIGHT)); 
    }
    public int getServerPlayerY() { return serverPlayerY; }
    public void setServerPlayerY(int y) { 
        this.serverPlayerY = Math.max(0, Math.min(y, FIELD_HEIGHT - PADDLE_HEIGHT)); 
    }
    public int getWhoAmI() { return whoAmI; }
    public void setWhoAmI(int whoAmI) { this.whoAmI = whoAmI; }
    public int getServerScore() { return serverScore; }
    public int getClientScore() { return clientScore; }
    public boolean isFanCelebrationOn() { return fanCelebration; }
    public void triggerFanCelebration() { fanCelebration = true; }
    public void resetFanCelebration() { fanCelebration = false; }
}