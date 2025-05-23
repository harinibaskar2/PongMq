package hbaskar;

/**
 * This class represents the client side of a simple Pong-style game that 
 * communicates with a server using MQTT messaging protocol. It manages the 
 * lifecycle of both a publisher and a subscriber to send and receive game 
 * state updates asynchronously.
 * 
 * The client publishes its paddle position to the server and subscribes to 
 * receive updates such as the ball position and the server’s paddle position.
 * 
 * It runs the publisher and subscriber on separate threads to allow 
 * simultaneous sending and receiving of MQTT messages, ensuring smooth 
 * gameplay interaction.
 * 
 * The class also provides a method to cleanly stop both the publisher and 
 * subscriber threads, facilitating proper resource cleanup.
 * 
 * This class acts as a coordinator for MQTT communication on the client side,
 * while the actual publishing and subscribing logic is encapsulated in the 
 * T1Publisher and T1Subscriber classes.
 * 
 * @author hbaskar
 * @version 1.0
 */

public class T1Client implements Runnable {
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
