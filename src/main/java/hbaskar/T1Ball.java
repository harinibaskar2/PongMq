package hbaskar;

/**
 * Ball component that handles all ball logic and movement.
 * Gets position data from repository and handles movement calculations.
 * 
 * @author Daniel Alexander Miranda
 * @version 1.1
 */
public class T1Ball {
    private int dx = 5, dy = 4; // Ball velocity
    private final int WIDTH = 10, HEIGHT = 10;
    private final T1DataRepository repo;

    public T1Ball() {
        this.repo = T1DataRepository.getInstance();
        // Initialize ball position in center
        repo.setBallX(repo.getFieldWidth() / 2);
        repo.setBallY(repo.getFieldHeight() / 2);
    }

    public void move() {
        int x = repo.getBallX();
        int y = repo.getBallY();
        
        // Update position
        x += dx;
        y += dy;

        // Bounce off top/bottom walls
        if (y <= 0 || y >= repo.getFieldHeight() - HEIGHT) {
            dy = -dy;
        }

        // Check paddle collisions
        if (checkPaddleCollision(x, y)) {
            dx = -dx;
        }

        // Check scoring
        if (x <= 0) {
            // Server scores
            repo.incrementServerScore();
            repo.triggerFanCelebration();
            resetBall();
            System.out.println("Server scored! Score: Client " + repo.getClientScore() + " - Server " + repo.getServerScore());
            return;
        } else if (x >= repo.getFieldWidth() - WIDTH) {
            // Client scores
            repo.incrementClientScore();
            repo.triggerFanCelebration();
            resetBall();
            System.out.println("Client scored! Score: Client " + repo.getClientScore() + " - Server " + repo.getServerScore());
            return;
        }

        // Update repository with new position
        repo.setBallX(x);
        repo.setBallY(y);
    }

    private boolean checkPaddleCollision(int ballX, int ballY) {
        // Left paddle (client) collision
        if (ballX <= 20 && dx < 0 && 
            ballY + HEIGHT >= repo.getClientPlayerY() && 
            ballY <= repo.getClientPlayerY() + 50) {
            return true;
        }
        
        // Right paddle (server) collision
        if (ballX >= repo.getFieldWidth() - 30 && dx > 0 && 
            ballY + HEIGHT >= repo.getServerPlayerY() && 
            ballY <= repo.getServerPlayerY() + 50) {
            return true;
        }
        
        return false;
    }

    private void resetBall() {
        // Reset to center
        repo.setBallX(repo.getFieldWidth() / 2);
        repo.setBallY(repo.getFieldHeight() / 2);
        
        // Random direction
        dx = (Math.random() < 0.5) ? -5 : 5;
        dy = (Math.random() < 0.5) ? -4 : 4;
    }

    public int getX() {
        return repo.getBallX();
    }

    public int getY() {
        return repo.getBallY();
    }

    public int getWidth() {
        return WIDTH;
    }

    public int getHeight() {
        return HEIGHT;
    }
}