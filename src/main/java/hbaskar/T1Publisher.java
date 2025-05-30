package hbaskar;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * This class implements a simple MQTT publisher using the Eclipse Paho library.
 * It connects to the Mosquitto test broker and periodically publishes messages 
 * to a specific topic. The publisher runs in a separate thread and supports 
 * graceful shutdown through a `stop()` method.
 * 
 * It also provides a method to publish custom messages on-demand.
 *
 * @author Darien
 * @version 1.2
 */
public class T1Publisher implements Runnable {

    private volatile boolean running = true;
    private MqttClient client;

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "ponggame";

    @Override
    public void run() {
        try {
            client = new MqttClient(BROKER, CLIENT_ID);
            client.connect();
            System.out.println("Connected to BROKER: " + BROKER);
            int counter = 0;

            while (running) {
                String content = "this is message " + counter;
                MqttMessage message = new MqttMessage(content.getBytes());
                message.setQos(2);

                if (client.isConnected())
                    client.publish(TOPIC, message);

                counter++;
                System.out.println("Message published: " + content);
                Thread.sleep(5000);
            }

            client.disconnect();
            System.out.println("MQTT client disconnected.");

        } catch (MqttException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gracefully stops the publishing loop.
     */
    public void stop() {
        running = false;
    }

    /**
     * Publishes a custom message to a specific MQTT topic.
     *
     * @param topic The topic to publish to
     * @param messageStr The message content to publish
     */
    public synchronized void publish(String topic, String messageStr) {
        if (client != null && client.isConnected()) {
            try {
                T1DataRepository repo = T1DataRepository.getInstance();
                String gameState = String.format("%d,%d,%d,%d", 
                    repo.getBallX(), repo.getBallY(), 
                    repo.getClientPlayerY(), repo.getServerPlayerY());
                
                MqttMessage message = new MqttMessage(gameState.getBytes());
                message.setQos(1);
                client.publish(topic, message);
                
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Fix CLIENT_ID to be unique:
    private static final String CLIENT_ID = "PongPlayer_" + System.currentTimeMillis();

    /**
     * Publishes a custom message to the default topic.
     *
     * @param messageStr The message content to publish
     */
    public void publish(String messageStr) {
        publish(TOPIC, messageStr);
    }
}