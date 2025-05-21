
package hbaskar;




/**
 * This class is a simple JPanel that will be used to display the Bars, Ball, and Chat.
 *
 * @author hbaskar
 * @version 1.1
 */


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class is a simple JPanel that will be used to display the Bars, Ball, and Chat.
 *
 * @author hbaskar
 * @version 1.1
 */
public class T1PongPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

    private final T1ChatPanel chatPanel;

    public T1PongPanel(T1ChatPanel chatPanel) {
        this.chatPanel = chatPanel;

        addMouseMotionListener(this);
        addMouseListener(this);
        setBackground(new Color(172, 248, 199));
        Timer timer = new Timer(1000 / 30, this);
        timer.start();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawCenterLine(g);
        drawBall(g);
        drawClientPlayer(g);
        drawServerPlayer(g);
        drawChat(g); // NEW
    }

    private void drawChat(Graphics g) {
        if (chatPanel != null) {
            chatPanel.draw(g, 20, getHeight() - 90); // Adjust Y based on where you want it to show
        }
    }

    private void drawCenterLine(Graphics g) {
        g.setColor(Color.WHITE);
        Graphics2D g2d = (Graphics2D) g;
        float[] dash1 = {10.0f};
        BasicStroke dashed = new BasicStroke(
                1.0f,
                BasicStroke.CAP_BUTT,
                BasicStroke.JOIN_MITER,
                10.0f,
                dash1,
                0.0f);
        g2d.setStroke(dashed);
        g.drawLine(400, 0, 400, 600);
        g2d.setStroke(new BasicStroke());
    }

    private void drawClientPlayer(Graphics g) {
        int y = T1DataRepository.getInstance().getClientPlayerY();
        g.setColor(T1DataRepository.getInstance().getWhoAmI() == T1DataRepository.SERVER ? Color.DARK_GRAY : Color.WHITE);
        g.fillRect(10, y, 10, 50);
    }

    private void drawServerPlayer(Graphics g) {
        int y = T1DataRepository.getInstance().getServerPlayerY();
        g.setColor(T1DataRepository.getInstance().getWhoAmI() == T1DataRepository.SERVER ? Color.DARK_GRAY : Color.WHITE);
        g.fillRect(780, y, 10, 50);
    }

    private void drawBall(Graphics g) {
        int x = T1DataRepository.getInstance().getBallX();
        int y = T1DataRepository.getInstance().getBallY();
        if (x == 400) {
            g.setColor(new Color(172, 248, 199));
        } else if ((T1DataRepository.getInstance().getWhoAmI() == T1DataRepository.SERVER && x <= 400) ||
                (T1DataRepository.getInstance().getWhoAmI() == T1DataRepository.CLIENT && x >= 400)) {
            g.setColor(Color.DARK_GRAY);
        } else {
            g.setColor(Color.WHITE);
        }
        g.fillRect(x - 5, y - 5, 10, 10);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(800, 600);
    }

    @Override public void mouseDragged(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}

    @Override
    public void mouseMoved(MouseEvent e) {
        if (T1DataRepository.getInstance().getWhoAmI() == T1DataRepository.CLIENT)
            T1DataRepository.getInstance().setClientPlayerY(e.getY());
        else
            T1DataRepository.getInstance().setServerPlayerY(e.getY());
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        T1DataRepository.getInstance().moveBall();
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();
        int ballX = T1DataRepository.getInstance().getBallX();
        int ballY = T1DataRepository.getInstance().getBallY();

        if (mouseX >= ballX - 5 && mouseX <= ballX + 5 &&
                mouseY >= ballY - 5 && mouseY <= ballY + 5) {
            System.out.println("Ball clicked at: " + mouseX + ", " + mouseY);
            T1DataRepository.getInstance().setCoordinates(mouseX, mouseY);
        }
    }
}
