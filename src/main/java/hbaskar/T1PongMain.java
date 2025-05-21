package hbaskar;


import javax.swing.*;
import javiergs.mqtt.Publisher;




/**

 *
 * @author hbaskar
 * @version 1.1
 */

public class T1PongMain {
    public static void main(String[] args) {
        // Create the ChatPanel (shared with MQTT)
        T1ChatPanel chatPanel = new T1ChatPanel(5);

        // Start the MQTT publisher (and subscriber if needed)
        Publisher publisher = new Publisher();  // update to pass ChatPanel if needed
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