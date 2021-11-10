package application;


import application.news.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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
	private Label errorLabel;
	
	@FXML
	private Button cancelButton;
	
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
	
	public void ClickLogIn(ActionEvent event) {
		loggedUsr = this.loginModel.validateUser(this.userName.getText(),this.password.getText());
		//User user = this.loginModel.validateUser("DEV_TEAM_04","123704");
		
		if(loggedUsr == null) {
			System.out.print(" !login failed! ");
			this.errorLabel.setText("Login failed!");
			System.out.print("error label"+ this.errorLabel.getText());
		}else {
			System.out.print(" !Login success: " + loggedUsr.getIdUser());
	    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    	stage.close();
		}
	}
	
	public void ClickCancel(ActionEvent event) {
		System.out.print("cancel clicked");
    	Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage.close();
	}
}