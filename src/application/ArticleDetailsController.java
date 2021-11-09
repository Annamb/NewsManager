/**
 * 
 */
package application;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.json.JsonObject;


import application.news.Article;
import application.news.Categories;
import application.news.User;
import application.utils.JsonArticle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import serverConection.ConnectionManager;
import serverConection.exceptions.ServerCommunicationError;



/**
 * @author Ã�ngelLucas
 *
 */
public class ArticleDetailsController {
	//TODO add attributes and methods as needed
	    private User usr;
	    private Article article;
	
		@FXML
		private Label userName;

		@FXML
		private ImageView articleImage;

		@FXML
		private TextArea articleAbstract;
	
		@FXML
	   	private TextArea articleTitle;

	    @FXML
	    private TextArea articleSubtitle;
	    
	    @FXML
	    private TextArea articleCategory;

	    @FXML
	    private TextArea articleBody;

	    

		/**
		 * @param usr the usr to set
		 */
		void setUsr(User usr) {
			this.usr = usr;
			if (usr == null) {
				return; //Not logged user
			}
			//TODO Update UI information
		}

		/**
		 * @param article the article to set
		 */
		void setArticle(Article article) {
			this.article = article;
			//TODO complete this method
		}
	
	    	@FXML
	   	public void back(Event event) {
			Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			stage.close();
    		}
		
		@FXML
	    	public void showBody(){
		   if (articleBody.isVisible()) {
		    	articleBody.setVisible(false);
		    	articleAbstract.setVisible(true);
		    	return;
		   }
		   
		   if (articleAbstract.isVisible()){
		   	articleAbstract.setVisible(false);
		   	articleBody.setVisible(true);
		        return;
		   }
    }
}
