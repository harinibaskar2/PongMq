/**
 * T1Ball.java
 * Table 1
 * Daniel Alexander Miranda
 *
 * Responsibilities:
 * - Handle ball position, velocity, and movement logic
 * - Detect wall and paddle collisions
 * - Publish ball state updates via MQTT
 * - Subscribe to ball state for sync (if this instance is not the host)
 */

 public class T1Ball {
    private int x, y;
    private int dx = 9, dy = 9;
    private final int WIDTH = 10, HEIGHT = 10;
    private final int FIELD_WIDTH = 800, FIELD_HEIGHT = 600;

    private T1Publisher publisher;

    public T1Ball(T1Publisher publisher) {
        this.publisher = publisher;
        this.x = FIELD_WIDTH / 2;
        this.y = FIELD_HEIGHT / 2;
    }

    public void move() {
        x += dx;
        y += dy;

        if (y <= 0 || y >= FIELD_HEIGHT - HEIGHT) {
            dy = -dy;
        }

        if (x <= 0 || x >= FIELD_WIDTH - WIDTH) {
            dx = -dx;
        }

        publishState();
    }

    public void publishState() {
        String message = x + "," + y;
        publisher.publish("pong/ball", message);
    }

    public void updateFromMessage(String message) {
        String[] parts = message.split(",");
        if (parts.length == 2) {
            x = Integer.parseInt(parts[0]);
            y = Integer.parseInt(parts[1]);
        }
    }

    public int getX() { return x; }
    public int getY() { return y; }
    public int getWidth() { return WIDTH; }
    public int getHeight() { return HEIGHT; }
}