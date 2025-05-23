package hbaskar;

import java.awt.BorderLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;




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
        T1ChatPanel chatPanel = T1ChatPanel.getInstance();
        T1PongPanel pongPanel = new T1PongPanel(chatPanel);

        //add defaults
        chatPanel.addMessage("Welcome to Pong!");
        chatPanel.addMessage("Use the mouse to move.");
        chatPanel.addMessage("Click the ball to interact.");

        // Create input field and button for sending chat messages
        JTextField inputField = new JTextField();
        JButton sendButton = new JButton("Send");

        // Start the MQTT publisher (and subscriber if needed)
        T1Publisher publisher = new T1Publisher();  // pass chatPanel here if needed
        Thread mqttThread = new Thread(publisher);
        mqttThread.start();


        // Create a panel to hold input field and button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);

        // Send message when button clicked or Enter pressed
        ActionListener sendAction = e -> {
            String text = inputField.getText().trim();
            if (!text.isEmpty()) {
                chatPanel.addMessage(text);
                inputField.setText("");
                pongPanel.repaint(); // Trigger redraw of the chat area
            }
        };

        sendButton.addActionListener(sendAction);
        inputField.addActionListener(sendAction);

        // Set up the JFrame
        JFrame frame = new JFrame("Pong Game");
        frame.setLayout(new BorderLayout());
        frame.add(pongPanel,BorderLayout.CENTER);
        frame.add(inputPanel,BorderLayout.SOUTH);
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