package hbaskar;

/**
 * This class represents the server side of a simple Pong-style game that 
 * manages MQTT communication with one or more clients. It controls the 
 * game state publishing and listens for client updates via MQTT messaging.
 * 
 * The server publishes the ball’s position and its own paddle position to 
 * subscribed clients, while subscribing to receive paddle position updates 
 * from clients.
 * 
 * It runs both a publisher and a subscriber on separate threads to enable 
 * asynchronous bidirectional communication, allowing real-time game state 
 * synchronization.
 * 
 * The class provides a method to stop the publisher and subscriber threads 
 * cleanly, ensuring proper disconnection and resource management.
 * 
 * This class serves as the main coordinator of MQTT message handling on the 
 * server side, delegating the actual message sending and receiving to the 
 * T1Publisher and T1Subscriber classes.
 * 
 * @author hbaskar
 * @version 1.0
 */







public class T1Server implements Runnable {
    private T1Publisher publisher;
    private T1Subscriber subscriber;

    @Override
    public void run() {
        publisher = new T1Publisher();
        subscriber = new T1Subscriber();

        Thread pubThread = new Thread(publisher);
        Thread subThread = new Thread(subscriber);

        pubThread.start();
        subThread.start();

        try {
            pubThread.join();
            subThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (publisher != null) publisher.stop();
        if (subscriber != null) subscriber.stop();
    }
}
