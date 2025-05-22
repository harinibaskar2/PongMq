package hbaskar;
import org.eclipse.paho.client.mqttv3.*;
/**
 *
 * @author Darien Rodrigo
 * @version 1.0
 */
public class T1Subscriber implements MqttCallback {
	
	private final static String BROKER = "tcp://test.mosquitto.org:1883";
	private final static String TOPIC = "ponggame";
	private final static String CLIENT_ID = "Pong -subscriber";
	
	public static void main(String[] args) {
		try {
			T1DataRepository repo = T1DataRepository.getInstance();
			MqttClient client = new MqttClient(BROKER, CLIENT_ID);
			T1Subscriber subscriber = new T1Subscriber();
			client.setCallback(subscriber);
			client.connect();
			System.out.println("Connected to BROKER: " + BROKER);
			client.subscribe(TOPIC);
			System.out.println("Subscribed to TOPIC: " + TOPIC);
		} catch (MqttException e) {
			e.printStackTrace();
		}
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
        int CurY = Integer.parseInt(parts[2]);
        String Message = parts [3];
       	repo.setBallX(BallX);
        repo.setBallY(BallY);
        repo.setCurY(CurY);	
		// How do we switch between updating the coordinates for the ball and updating the coordinates

	}
	
	@Override
	public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
		System.out.println("Delivered complete: " + iMqttDeliveryToken.getMessageId());
	}

}