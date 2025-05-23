package hbaskar;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Alternative main game panel that handles rendering and user input.
 * Uses T1Ball for ball logic instead of repository ball methods.
 * 
 * @author hbaskar
 * @version 1.1
 */
public class T1PongPanel extends JPanel implements MouseMotionListener {
    private final T1DataRepository repo;
    private final T1ChatPanel chatPanel;
    private final T1Fans fans;
    private final T1Ball ball;  // Add T1Ball component
    private final Timer gameTimer;
    private final ScoreDisplay clientScoreDisplay;
    private final ScoreDisplay serverScoreDisplay;
    
    private static final int FIELD_WIDTH = 800;
    private static final int FIELD_HEIGHT = 600;
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 50;
    private static final int BALL_SIZE = 10;
    
    public T1PongPanel(T1ChatPanel chatPanel) {
        this.repo = T1DataRepository.getInstance();
        this.chatPanel = chatPanel;
        this.fans = new T1Fans();
        this.ball = new T1Ball();  // Create T1Ball instance
        
        // Create decorated score displays for both players
        this.clientScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        this.serverScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        
        setPreferredSize(new Dimension(FIELD_WIDTH, FIELD_HEIGHT));
        setBackground(new Color(172, 248, 199));
        addMouseMotionListener(this);
        
        // Game loop timer - calls ball.move() instead of repo.moveBall()
        gameTimer = new Timer(16, e -> {
            ball.move(); // Ball handles its own movement and collision logic
            
            // Check for fan celebration trigger from repository
            if (repo.isFanCelebrationOn()) {
                fans.triggerStars(); // Fans handle visual effects
                repo.resetFanCelebration();
            }
            repaint();
        });
        gameTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw center line on green field
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{10}, 0));
        g2d.drawLine(FIELD_WIDTH / 2, 0, FIELD_WIDTH / 2, FIELD_HEIGHT);
        g2d.setStroke(new BasicStroke(1)); // Reset stroke
        
        // Draw paddles - get positions from repository
        g.setColor(Color.WHITE);
        g.fillRect(10, repo.getClientPlayerY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(FIELD_WIDTH - 25, repo.getServerPlayerY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        
        // Draw ball - use T1Ball component for position
        g.setColor(Color.DARK_GRAY);
        g.fillRect(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        
        // Draw scores using repository data and decorated score display system
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        // Left side - Client score from repository
        String clientScoreText = clientScoreDisplay.getDisplay(repo.getClientScore());
        g.drawString("Client " + clientScoreText, 50, 50);
        
        // Right side - Server score from repository
        String serverScoreText = serverScoreDisplay.getDisplay(repo.getServerScore());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("Server " + serverScoreText);
        g.drawString("Server " + serverScoreText, FIELD_WIDTH - textWidth - 50, 50);
        
        // Center score display - show current player's score from repository
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.YELLOW);
        int currentScore = (repo.getWhoAmI() == T1DataRepository.SERVER) 
            ? repo.getServerScore() 
            : repo.getClientScore();
        ScoreDisplay myScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        String myScore = myScoreDisplay.getDisplay(currentScore);
        int centerX = FIELD_WIDTH / 2 - fm.stringWidth(myScore) / 2;
        g.drawString(myScore, centerX, 30);
        
        // Draw chat panel
        chatPanel.draw(g, 10, FIELD_HEIGHT - 100);
        
        // Draw fan celebration - fans handle their own rendering
        fans.draw(g);
        
        // Draw game info using repository data
        g.setColor(Color.BLACK); // Black text on green field
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        String roleText = "You are: " + (repo.getWhoAmI() == T1DataRepository.SERVER ? "Server" : "Client");
        g.drawString(roleText, 10, FIELD_HEIGHT - 10);
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // Get repository instance and move paddle based on role
        if (repo.getWhoAmI() == T1DataRepository.CLIENT) {
            repo.setClientPlayerY(e.getY() - PADDLE_HEIGHT / 2);
        } else {
            repo.setServerPlayerY(e.getY() - PADDLE_HEIGHT / 2);
        }
    }
    
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }
}