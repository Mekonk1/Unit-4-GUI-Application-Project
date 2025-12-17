//Used to store registered account data

public class Account{
	private String username;
	private String password;
	
	
	//Getters & Setters
	public Account(String username, String password) {
		this.username = username;
		this.password = password;
		
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassworrd() {
		return password;
	}
}
