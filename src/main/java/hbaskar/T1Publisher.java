package hbaskar;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class T1Publisher implements Runnable {

    private volatile boolean running = true;
    private MqttClient client;

    private static final String BROKER = "tcp://test.mosquitto.org:1883";
    private static final String TOPIC = "cal-poly/csc/309";
    private static final String CLIENT_ID = "jgs-publisher";

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

    public void stop() {
        running = false;
    }
}
