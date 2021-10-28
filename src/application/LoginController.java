package application;


import application.news.User;

import serverConection.ConnectionManager;



public class LoginController {
//TODO Add all attribute and methods as needed 
	private LoginModel loginModel = new LoginModel();
	
	private User loggedUsr = null;

	public LoginController (){
	
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
		this.loginModel.setConnectionManager(connection);
	}
}