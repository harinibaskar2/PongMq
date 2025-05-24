package hbaskar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

/**
 * Main game field that renders all game elements.
 * Gets all data from repository and uses Ball, Fans, and ChatPanel components.
 * 
 * @author Daniel Alexander Miranda
 * @version 1.1
 */
public class T1Field extends JPanel implements MouseMotionListener {
    private final T1DataRepository repo;
    private final T1Ball ball;
    private final T1Fans fans;
    private final T1ChatPanel chatPanel;
    private final ScoreDisplay clientScoreDisplay;
    private final ScoreDisplay serverScoreDisplay;
    
    private static final int PADDLE_WIDTH = 15;
    private static final int PADDLE_HEIGHT = 50;
    
    public T1Field(T1Ball ball, T1Fans fans, T1ChatPanel chatPanel) {
        this.repo = T1DataRepository.getInstance();
        this.ball = ball;
        this.fans = fans;
        this.chatPanel = chatPanel;
        
        // Create decorated score displays
        this.clientScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        this.serverScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        
        setPreferredSize(new Dimension(repo.getFieldWidth(), repo.getFieldHeight()));
        setBackground(Color.GREEN);
        addMouseMotionListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw center line
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{10}, 0));
        g2d.drawLine(repo.getFieldWidth() / 2, 0, repo.getFieldWidth() / 2, repo.getFieldHeight());
        g2d.setStroke(new BasicStroke(1));
        
        // Draw paddles using repository data
        g.setColor(Color.WHITE);
        g.fillRect(10, repo.getClientPlayerY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(repo.getFieldWidth() - 25, repo.getServerPlayerY(), PADDLE_WIDTH, PADDLE_HEIGHT);
        
        // Draw ball using Ball component
        g.setColor(Color.RED);
        g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
        
        // Draw scores using repository data and score display decorators
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        
        String clientScoreText = clientScoreDisplay.getDisplay(repo.getClientScore());
        g.drawString("Client " + clientScoreText, 50, 50);
        
        String serverScoreText = serverScoreDisplay.getDisplay(repo.getServerScore());
        FontMetrics fm = g.getFontMetrics();
        int textWidth = fm.stringWidth("Server " + serverScoreText);
        g.drawString("Server " + serverScoreText, repo.getFieldWidth() - textWidth - 50, 50);
        
        // Center score display
        g.setFont(new Font("Arial", Font.BOLD, 16));
        g.setColor(Color.YELLOW);
        int currentScore = (repo.getWhoAmI() == T1DataRepository.SERVER) 
            ? repo.getServerScore() 
            : repo.getClientScore();
        ScoreDisplay myScoreDisplay = new EmojiDecorator(new BasicScoreDisplay());
        String myScore = myScoreDisplay.getDisplay(currentScore);
        int centerX = repo.getFieldWidth() / 2 - fm.stringWidth(myScore) / 2;
        g.drawString(myScore, centerX, 30);
        
        // Draw chat panel
        chatPanel.draw(g, 10, repo.getFieldHeight() - 100);
        
        // Draw fans (fans check repository for celebration trigger)
        fans.draw(g);
        
        // Draw player role info
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 12));
        String roleText = "You are: " + (repo.getWhoAmI() == T1DataRepository.SERVER ? "Server" : "Client");
        g.drawString(roleText, 10, repo.getFieldHeight() - 10);
    }
    
    public void refresh() {
        // Move ball through Ball component
        ball.move();
        repaint();
    }
    
    @Override
    public void mouseMoved(MouseEvent e) {
        // Update paddle position in repository based on player role
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