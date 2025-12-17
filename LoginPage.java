/*
 * Sample code
 * This is a login page that stores user info using ArrayList<String>
 */
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.*;

public class LoginPage extends JPanel{
	
	private int currentUserIndex;
	private JFrame frame;
	private CoinCollectorGame game;
    private JTextField userField = new JTextField(10);
    private JPasswordField passField = new JPasswordField(10);
    private JLabel status = new JLabel("Login or Register");
    private ArrayList<String> userID = new ArrayList<>();
    private ArrayList<String> userPass = new ArrayList<>();
    private ArrayList<Integer> highScore = new ArrayList<>();
    
    public LoginPage(JFrame frame) {
        
        setPreferredSize(new Dimension(250,200)); //set the size of the pane
        //add the components in the following order:[this part is already done]
        
        this.frame = frame;

        add(new JLabel("  Username:"));
        add(userField);
        add(new JLabel("   Password:"));
        add(passField);
        //create a login button
        JButton loginbuton = new JButton ("Login");
        add(loginbuton);

        //create a register button
        JButton registerbuton = new JButton ("Register");
        add(registerbuton);
        
        add(status);        
        //add the buttons and status to the pane
        
        
        
        //add action listener to the buttons
        //when login button is clicked , call "loginUser()"
        
        loginbuton.addActionListener(e-> loginUser());
        
        
        //when register button is clicked, call "registerUser()"
        
        registerbuton.addActionListener(e-> registerUser());
        
        
        
        
              
    }
	// For this example, ignore validating user info
    // You must modify the following to make it work.
    private void loginUser(){
        //first, ensure that user types something in the fields
        String name = userField.getText();
        String pass = new String(passField.getPassword());
        
     if(name.length() == 0 || pass.length() == 0 || name.trim().isEmpty() || pass.trim().isEmpty()){
            status.setText("Please enter Username or Password");
            return;
        }
     
     //Checks for the index of the userID
     int index = userID.indexOf(name);
     
     /*Checks if input is either out of bonds or the password inputted is not the same as
       the password at the index of the name
      */
     if(index == -1 || !userPass.get(index).equals(pass)){
    	 status.setText("Account Information is unregistered");
    	 return;
     }
     
     
   //if login is successful, then change the 'status' to "Login successful"  
         currentUserIndex = index;
         status.setText("Login sucessful");
         startGame();
     }

    
    //register the user by adding the user info to corresponding ArrayList
    private void registerUser(){
        //First, get user info
        String name = userField.getText();
        String pass = new String(passField.getPassword());
        
     if(name.length() == 0 || pass.length() == 0 || name.trim().isEmpty() || pass.trim().isEmpty()){
            status.setText("Please enter Username or Password");
        }
     
     if (userID.contains(name)) {
    	 status.setText("Username already exists");
     }
        
        else{
            
            status.setText("You have sucessfully registered");
            
            userID.add(name);
            userPass.add(pass);
            highScore.add(0);
            
          //only if it is not empty, add the item to the ArrayList and change the 
            //status to "Register successful"
        }
    }
    
    
    private void startGame() {
    	
    	//allows the game to restart each time a login is made
    	game.restartGame();
        
        //Changes the content panel to main game
      	 frame.setContentPane(game);
      	 frame.pack();
      	 
      	 //Makes it so the game panel is focused allowing keybinds to work for game 
      	 game.setFocusable(true);
      	 game.requestFocusInWindow();
      	 
      	 frame.revalidate();
      	 frame.repaint();
       }
    
    
    
    public void setGame(CoinCollectorGame game){
    	this.game = game;
    	
    }
    
    public void resetFields(){
    	userField.setText("");
    	passField.setText("");
    	
    }
    
    //Getters and Setters used to track highscore in-game and designate for specific account
    public int getHighScore(int index) {
        return highScore.get(index);
    }

    public void setHighScore(int index, int score) {
        highScore.set(index, score);
    }

    public int getCurrentUserIndex() {
        return currentUserIndex;
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Login Page");
        LoginPage loginPage = new LoginPage(frame);
        CoinCollectorGame game = new CoinCollectorGame(frame, loginPage);
        
        //links login to game
        loginPage.setGame(game);
        
        frame.setContentPane(loginPage);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
    }
}
