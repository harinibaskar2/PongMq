package hbaskar;

import javax.swing.JFrame;




/**
 * This class is the main entry point for the Pong game application. It initializes the
 * ChatPanel, starts the MQTT publisher in a separate thread, creates the PongPanel,
 * and sets up the main game window using JFrame. It also ensures a clean shutdown
 * of the MQTT connection when the application exits.
 *
 * @author hbaskar
 * @version 1.1
 */





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
