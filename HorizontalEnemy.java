import java.awt.Graphics;
import java.awt.Color;

public class HorizontalEnemy extends MovingObject {
    private int leftBound, rightBound;

    public HorizontalEnemy(int x, int y, int width, int height, int speedX, int leftBound, int rightBound) {
        super(x, y, width, height, speedX, 0);
        this.leftBound = leftBound;
        this.rightBound = rightBound;
    }

    @Override
    public void move() {
        x += speedX;
        if (x <= leftBound || x + width >= rightBound) {
            speedX = -speedX; // Reverse direction at edges
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(x, y, width, height);
    }
}
