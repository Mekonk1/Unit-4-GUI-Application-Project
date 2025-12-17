import java.awt.Graphics;
import java.awt.Color;

public class VerticalEnemy extends MovingObject {
    private int topBound, bottomBound;

    public VerticalEnemy(int x, int y, int width, int height, int speedY, int topBound, int bottomBound) {
        super(x, y, width, height, 0, speedY);
        this.topBound = topBound;
        this.bottomBound = bottomBound;
    }

    @Override
    public void move() {
        y += speedY;
        if (y <= topBound || y + height >= bottomBound) {
            speedY = -speedY; // Reverse direction
        }
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, width, height);
    }
}
