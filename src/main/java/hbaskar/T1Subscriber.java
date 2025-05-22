package hbaskar;
import java.util.concurrent.Flow.Subscriber;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
/**
 *
 * @author Darien Rodrigo
 * @version 1.1
 */
public class T1Subscriber implements MqttCallback, Runnable {
	

	private volatile boolean running = true;


	private final static String BROKER = "tcp://test.mosquitto.org:1883";
	private final static String TOPIC = "ponggame";
	private final static String CLIENT_ID = "Pong -subscriber";
	
	public static void main(String[] args) {
			T1Subscriber subscriber = new T1Subscriber();
			new Thread(subscriber).start();
	}

	@Override
	public void run(){
		try {
			while(running){
				MqttClient client = new MqttClient(BROKER, CLIENT_ID);
				client.setCallback(this);
				client.connect();
				client.subscribe(TOPIC);
			}

		} catch (MqttException e) {
            e.printStackTrace();
        }

	}

	public void stop() {
        running = false;
    }

	@Override
	public void connectionLost(Throwable throwable) {
		System.out.println("Connection lost: " + throwable.getMessage());
	}
	
	@Override
	public void messageArrived(String s, MqttMessage mqttMessage) {


		T1DataRepository repo = T1DataRepository.getInstance();
	    String content = new String(mqttMessage.getPayload());
        String [] parts = content.split(",");
        int BallX = Integer.parseInt(parts[0]);
        int BallY = Integer.parseInt(parts[1]);
       	repo.setBallX(BallX);
        repo.setBallY(BallY);

		// How do we switch between updating the coordinates for the ball and updating the coordinates
		// When breaking the tie between client and server player, the subscriber should instantly try and read a file
		// Alternately, define one naturally as a server player and the other as a client player
	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		System.out.println("Delivered complete: " + iMqttDeliveryToken.getMessageId());
	}

}


