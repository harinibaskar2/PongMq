/**
 * T1Field.java
 * Table 1
 * Daniel Alexander Miranda
 *
 * Responsibilities:
 * - Render the Pong field and game elements (like the ball)
 * - Provide a basic graphical interface for testing
 * - Should be refreshed regularly by the game loop
 */

 import java.awt.Color;
 import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;
 
 public class T1Field extends JPanel {
     private T1Ball ball;
     private T1Fans fans;
 
     public T1Field(T1Ball ball, T1Fans fans) {
         this.ball = ball;
         this.fans = fans;
         setPreferredSize(new Dimension(800, 600));
         setBackground(Color.BLACK);
     }
 
     @Override
     protected void paintComponent(Graphics g) {
         super.paintComponent(g);
 
         // Draw ball
         g.setColor(Color.WHITE);
         g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
 
         // Draw decorations from fans
         fans.draw(g);
     }
 
     public void refresh() {
         repaint();
     }
 }
 