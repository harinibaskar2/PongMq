package hbaskar;


/**
 * T1Fans.java
 * Table 1
 * Daniel Alexander Miranda
 *
 * Responsibilities:
 * - Show visual effects when triggered (e.g., stars, emojis)
 * - Trigger effects based on score or gameplay events
 * - Keep drawing lightweight and graphics-native
 */

 import java.awt.Color;
import java.awt.Graphics;

 public class T1Fans {
     private boolean showStars = false;
     private int frame = 0;
 
     public void triggerStars() {
         showStars = true;
         frame = 0;
     }
 
     public void draw(Graphics g) {
         if (!showStars) return;
 
         int numFans = 8;
         int spacing = 80;
         int fanWidth = 20;
         int fanHeight = 35;
 
         //RED fans
         g.setColor(Color.RED);
         for (int i = 0; i < numFans; i++) {
             int x = 100 + i * spacing;
 
             int phase = (frame + i * 3) % 10;
             int jumpY = (phase < 5) ? -10 : 0;
 
             int y = 30 + jumpY;
             g.fillOval(x, y, fanWidth, fanHeight);
         }
 
         // BLUE fans
         g.setColor(Color.BLUE);
         for (int i = 0; i < numFans; i++) {
             int x = 100 + i * spacing;
 
             int phase = (frame + i * 3) % 10;
             int jumpY = (phase < 5) ? -10 : 0;
 
             int y = 550 + jumpY; 
             g.fillOval(x, y, fanWidth, fanHeight);
         }
 
         frame++;
         if (frame > 30) {
             showStars = false;
         }
     }
 }
 