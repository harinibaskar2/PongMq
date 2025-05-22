package hbaskar;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;



/**
 * This class is a simple chat panel used to display a list of messages 
 * within a graphical context. It stores a limited number of messages 
 * and provides a method to render them on screen.
 * 
 * The messages are displayed in a monospaced font, with each new message 
 * shown beneath the previous ones.
 *
 * @author hbaskar
 * @version 1.1
 */



public class T1ChatPanel {
    private final List<String> messages = new LinkedList<>();
    private final int maxMessages;

    public T1ChatPanel(int maxMessages) {
        this.maxMessages = maxMessages;
    }

    public void addMessage(String msg) {
        if (messages.size() >= maxMessages) {
            messages.remove(0);
        }
        messages.add(msg);
    }

    public void draw(Graphics g, int x, int y) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        for (int i = 0; i < messages.size(); i++) {
            g.drawString(">> " + messages.get(i), x, y + i * 18);
        }
    }
}