import java.awt.*;
import java.util.LinkedList;
import java.util.List;



/**
 
 *
 * @author hbaskar
 * @version 1.1
 */

public class ChatPanel {
    private final List<String> messages = new LinkedList<>();
    private final int maxMessages;

    public ChatPanel(int maxMessages) {
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