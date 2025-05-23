package hbaskar;

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
