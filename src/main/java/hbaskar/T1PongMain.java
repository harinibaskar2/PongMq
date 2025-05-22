package hbaskar;




/**
 * This class contains the main method that sets up and launches the Pong game window.
 * It initializes the chat panel, starts the MQTT publisher, and integrates all components
 * into a JFrame. It also handles a clean shutdown of the MQTT thread on exit.
 * 
 * @author hbaskar
 * @version 1.1
 */
import javax.swing.JFrame;

public class T1PongMain {
    public static void main(String[] args) {
        // Create the ChatPanel (shared with MQTT)
        T1ChatPanel chatPanel = new T1ChatPanel(5);

        // Start the MQTT publisher (and subscriber if needed)
        T1Publisher publisher = new T1Publisher();  // pass chatPanel here if needed
        Thread mqttThread = new Thread(publisher);
        mqttThread.start();

        // Create PongPanel and pass ChatPanel to it
        T1PongPanel pongPanel = new T1PongPanel(chatPanel);

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
            System.out.println("Shutdown hook triggered: stopping publisher...");
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
