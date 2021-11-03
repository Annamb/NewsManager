package application;


import application.news.User;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import serverConection.ConnectionManager;



public class LoginController {
//TODO Add all attribute and methods as needed 
	private LoginModel loginModel = new LoginModel();
	
	private User loggedUsr = null;
	
	@FXML
	private TextField userName;
	
	@FXML
	private PasswordField password;
	
	@FXML
	void initialize() {
		System.out.print("initialize");
	}

	public LoginController (){
		System.out.print("LoginController");
		//Uncomment next sentence to use data from server instead dummy data
		loginModel.setDummyData(false);
	}
	
	User getLoggedUsr() {
		return loggedUsr;
		
	}
	
	User logIn (String login, String passwd) {
		return this.loginModel.validateUser(login, passwd);
	}
		
	void setConnectionManager (ConnectionManager connection) {
		System.out.print("setConnectionManager");
		this.loginModel.setConnectionManager(connection);
	}
	
	public void ClickLogIn() {
		User user = this.loginModel.validateUser(this.userName.getText(),this.password.getText());
		//User user = this.loginModel.validateUser("DEV_TEAM_04","123704");
		
		if(user == null) {
			System.out.print(" !login failed! ");
		}else {
			System.out.print(" !Login success: " + user.getIdUser());
		}
	}
}