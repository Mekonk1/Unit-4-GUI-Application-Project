import java.awt.Graphics;
import java.awt.Color;

public abstract class MovingObject {
    protected int x, y;           //Determines position of the the object
    protected int width, height;  //Determines size of the object
    protected int speedX, speedY; //Determines at what speed the object is moving

    public MovingObject(int x, int y, int width, int height, int speedX, int speedY) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.speedX = speedX;
        this.speedY = speedY;
    }

    // Abstract move method to define movement pattern
    public abstract void move();

    // Draw object (can be overridden if needed)
    public void draw(Graphics g) {
        g.setColor(Color.RED); // default color
        g.fillRect(x, y, width, height);
    }

    // Collision detection with player
    public boolean collidesWith(int px, int py, int pWidth, int pHeight) {
        return x < px + pWidth && x + width > px && y < py + pHeight && y + height > py;
    }
}
