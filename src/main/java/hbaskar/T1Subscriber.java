package hbaskar;
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
    String[] parts = content.split(",");
    
    if (parts.length >= 4) {
        try {
            // Only update opponent's paddle position, not our own or ball
            if (repo.getWhoAmI() == T1DataRepository.CLIENT) {
                int serverY = Integer.parseInt(parts[3]);
                repo.setServerPlayerY(serverY);
            } else {
                int clientY = Integer.parseInt(parts[2]);
                repo.setClientPlayerY(clientY);
            }
        } catch (NumberFormatException e) {
            System.err.println("Invalid message format: " + content);
        }
    }
}

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deliveryComplete'");
    }

}