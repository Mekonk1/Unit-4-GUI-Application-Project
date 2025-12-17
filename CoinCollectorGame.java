
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;

public class CoinCollectorGame extends JPanel implements ActionListener, KeyListener {

    // Game constants
	private ArrayList<MovingObject> enemies = new ArrayList<>();
	
	private JFrame frame;
	private LoginPage loginPage;
	
    private final int WIDTH = 600, HEIGHT = 600;
    private final int PLAYER_SIZE = 20;
    private final int COIN_SIZE = 15;
    private final int SPEED = 15;

    // Player position
    private int px = WIDTH / 2, py = HEIGHT / 2;

    // Coin position
    private int coinX, coinY;

    // Movement flags
    private boolean up, down, left, right;

    private int score = 0;
    private int timers = 0;
    private boolean gameOver = false;

    private Timer timer;
    private Random random = new Random();
    
    

    public CoinCollectorGame(JFrame frame, LoginPage loginPage) {
    	
    	this.frame = frame;
    	this.loginPage = loginPage;
    	
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        JButton returnButton = new JButton("Return To Login");
        add(returnButton);
        
        returnButton.addActionListener(e -> returnLogin());
        
        spawnCoin();

        timer = new Timer(15, this);
        timer.start();

        setFocusable(true);
        addKeyListener(this);
        
    	
    }

    private void spawnCoin() {
        coinX = random.nextInt(WIDTH - COIN_SIZE);
        coinY = random.nextInt(HEIGHT - COIN_SIZE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!gameOver) {
            movePlayer();
            checkCollision();

            // MOVE ENEMIES + CHECK COLLISION WITH PLAYER
            for (MovingObject enemy : enemies) {
                enemy.move();

                if (enemy.collidesWith(px, py, PLAYER_SIZE, PLAYER_SIZE)) {
                    gameOver = true;
                }
            }
        }
        repaint();
    }

    private void movePlayer() {
        if (up) py -= SPEED;
        if (down) py += SPEED;
        if (left) px -= SPEED;
        if (right) px += SPEED;

        // Check border collision → Game Over
        if (px < 0 || py < 0 || px + PLAYER_SIZE > WIDTH || py + PLAYER_SIZE > HEIGHT) {
            gameOver = true;
            
        }
    }

    private void checkCollision() {
        Rectangle playerRect = new Rectangle(px, py, PLAYER_SIZE, PLAYER_SIZE);
        Rectangle coinRect = new Rectangle(coinX, coinY, COIN_SIZE, COIN_SIZE);

        if (playerRect.intersects(coinRect)) {
            score++;
            spawnCoin();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Score
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("Score: " + score, 475, 30);

        // Player
        g.setColor(Color.GREEN);
        g.fillRect(px, py, PLAYER_SIZE, PLAYER_SIZE);

        // Coin
        g.setColor(Color.YELLOW);
        g.fillOval(coinX, coinY, COIN_SIZE, COIN_SIZE);
        
        // DRAW ENEMIES
        for (MovingObject enemy : enemies) {
            enemy.draw(g);
        }

        // Game Over screen
        if (gameOver) {
        	
            // After game is completed update highscore of specific account(index)
            int prevHigh = loginPage.getHighScore(loginPage.getCurrentUserIndex());
           
            
            /*if-else compares previous highscore with current score to determine 
             what the new high score is
             */
            if (score > prevHigh) {
              
                loginPage.setHighScore(loginPage.getCurrentUserIndex(), score);
            } else {
              
                loginPage.setHighScore(loginPage.getCurrentUserIndex(), prevHigh);
            }
            
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 40));
            g.drawString("GAME OVER", WIDTH / 2 - 140, HEIGHT / 2 - 20);

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            g.drawString("Press R to restart", WIDTH / 2 - 90, HEIGHT / 2 + 20);
            
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Your High Score:" + loginPage.getHighScore(loginPage.getCurrentUserIndex()), WIDTH / 2 - 85, HEIGHT / 2 + 40);
        }
    }
    
    private void returnLogin() {
    	
    	 /*
    	  Calling on a method in LoginPage.java which clears all the previous text in user
    	  and pass field 
    	  */
    	 loginPage.resetFields();
    	 
      	 frame.setContentPane(loginPage); //allows to reuse single login panel
       	 frame.pack();
       	 
       	 //Makes it so the login panel is focused allowing keybinds to work for game 
       	 loginPage.setFocusable(true);
       	 loginPage.requestFocusInWindow();
       	 
       	 frame.revalidate();
       	 frame.repaint();
    }

    // --------------------- KEY LISTENERS ---------------------
    @Override
    public void keyPressed(KeyEvent e) {
        if (gameOver) {
            if (e.getKeyCode() == KeyEvent.VK_R) restartGame();
            return;
        }

        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:    up = true; break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:  down = true; break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:  left = true; break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT: right = true; break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W:
            case KeyEvent.VK_UP:    up = false; break;

            case KeyEvent.VK_S:
            case KeyEvent.VK_DOWN:  down = false; break;

            case KeyEvent.VK_A:
            case KeyEvent.VK_LEFT:  left = false; break;

            case KeyEvent.VK_D:
            case KeyEvent.VK_RIGHT: right = false; break;
        }
    }
    
    public int getCurrentScore() {
 	   return score;
    }

    
  

    @Override
    public void keyTyped(KeyEvent e) {}

    // --------------------- RESTART ---------------------

    public void restartGame() {
        score = 0;
        px = WIDTH / 2;
        py = HEIGHT / 2;
        gameOver = false;
        spawnCoin();
        
        enemies.clear();
        
        enemies.add(new HorizontalEnemy(50, 500, 30, 30, 5, 50, WIDTH - 50));
        enemies.add(new HorizontalEnemy(50, 100, 30, 30, 5, 50, WIDTH - 50));
        enemies.add(new VerticalEnemy(150, 50, 30, 30, 4, 50, HEIGHT - 50));
        enemies.add(new VerticalEnemy(450, 50, 30, 30, 4, 50, HEIGHT - 50));
    }
    

    // --------------------- MAIN ---------------------

    public static void main(String[] args) {
        JFrame frame = new JFrame("Coin Collector Game");
        LoginPage loginPage  = new LoginPage(frame);
        CoinCollectorGame game = new CoinCollectorGame(frame, loginPage);
        frame.setContentPane(loginPage);
        frame.add(game);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
