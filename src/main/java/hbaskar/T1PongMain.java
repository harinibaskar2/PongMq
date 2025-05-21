import javax.swing.*;
import javiergs.mqtt.Publisher;




/**

 *
 * @author hbaskar
 * @version 1.1
 */

public class PongMain {
    public static void main(String[] args) {
        // Create the ChatPanel (shared with MQTT)
        ChatPanel chatPanel = new ChatPanel(5);

        // Start the MQTT publisher (and subscriber if needed)
        Publisher publisher = new Publisher();  // update to pass ChatPanel if needed
        Thread mqttThread = new Thread(publisher);
        mqttThread.start();

        // Create PongPanel and pass ChatPanel to it
        PongPanel pongPanel = new PongPanel(chatPanel);

        // Set up the JFrame
        JFrame frame = new JFrame("Pong Game");
        frame.add(pongPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Clean shutdown of MQTT on app exit
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            publisher.stop();
            try {
                mqttThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("MQTT publisher stopped on exit.");
        }));
    }
}