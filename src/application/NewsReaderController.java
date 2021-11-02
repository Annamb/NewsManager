/**
 * 
 */
package application;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;

import application.news.Article;
import application.news.Categories;
import application.news.User;
import application.utils.JsonArticle;
import application.utils.exceptions.ErrorMalFormedArticle;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import javafx.stage.FileChooser.ExtensionFilter;
import serverConection.ConnectionManager;
import serverConection.exceptions.AuthenticationError;

/**
 * @author √ngelLucas
 *
 */
public class NewsReaderController {

	@FXML
    private ListView<Article> articleList;
	
	@FXML
	private Label articleName;
	
	@FXML
	private TextArea articleBody;
	
	@FXML
	private ComboBox<Categories> categoryCombo;
	
	@FXML
	private Button menuButton;
	
    private FilteredList<Article> filteredData;
	private NewsReaderModel newsReaderModel = new NewsReaderModel();
	private User usr;
	
	Article chosenArticle;

	//TODO add attributes and methods as needed


	public NewsReaderController() {
		//TODO
		//Uncomment next sentence to use data from server instead dummy data
		newsReaderModel.setDummyData(false);
		//Get text Label
	}
	
	@FXML
	void initialize() {
		
		articleList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {
			 @Override
				public void changed(ObservableValue<? extends Article> observable, Article oldValue, Article newValue) {
					if (newValue != null){
						chosenArticle = newValue;
						articleName.setText(chosenArticle.getTitle());
						articleBody.setText(chosenArticle.getBodyText());
					}
					else { //Nothing selected

					}
				}
	 });
	}


	 @FXML
	    void changeCategory(ActionEvent event) {
		//TODO:
	    	//Get the text associated to key pressed
	    	
	    	//If key code corresponds to a character or digit then add to text filter
	    	
	    	
	    	//Update the filter. If filtetText is empty, 
	    	//all contacts must be shown in other case,
	    	//only contacts that start with filterText must be shown
	      //END TODO
	    	
	    	String filterText = this.categoryCombo.getValue().toString();

	    	if(filterText == "All") {
		    	filteredData.setPredicate(article -> true);
	    	}else {
		    	filteredData.setPredicate(article -> article.getCategory().equals(filterText));
	    	}
	    	
	    	this.articleList.setItems(filteredData);
	    }
	

	private void getData() {
		//TODO retrieve data and update UI
		 newsReaderModel.retrieveData();
		 categoryCombo.getItems().addAll(newsReaderModel.getCategories());
		 
	    	filteredData = new FilteredList<>(newsReaderModel.getArticles(), article -> true);
	    	
	    	this.articleList.setItems(filteredData);
		 //articleList.getItems().addAll(newsReaderModel.getArticles());

	}

	/**
	 * @return the usr
	 */
	User getUsr() {
		return usr;
	}

	void setConnectionManager (ConnectionManager connection){
		//this.newsReaderModel.setDummyData(false); //System is connected so dummy data are not needed
		this.newsReaderModel.setConnectionManager(connection);
		this.getData();
	}
	
	/**
	 * @param usr the usr to set
	 */
	void setUsr(User usr) {
		
		this.usr = usr;
		//Reload articles
		this.getData();
		//TODO Update UI
	}
	
	public void ClickEdit() {
		
	}
	
	public void ClickNew() {
		
	}
	
	public void ClickObserve() {
		
	}
	
	public void ClickLogin() {
		NewScene(AppScenes.LOGIN, null);
	}
	
	public void ClickDelete() {
		
	}
	
	public void ClickExit() {
		
	}
	
	public void NewScene(AppScenes scene, Article article) {
		
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource(
					scene.getFxmlFile()));
			Pane root = loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(scene.toString());
            stage.setScene(new Scene(root));
            
            if(article == null) {
            	stage.show();
            	LoginController controller = loader.<LoginController>getController();
            	
    			Properties prop = Main.buildServerProperties();
    			ConnectionManager connection = new ConnectionManager(prop);

    			connection.setAnonymousAPIKey("");

    			controller.setConnectionManager(connection);		
    			
            	
            	User loggedInUsr = controller.getLoggedUsr();
            	if (loggedInUsr != null) {
            		setUsr(loggedInUsr);
            	}
            }else {
            	stage.show();
            	NewsReaderController controller = loader.<NewsReaderController>getController();
            }
            
		} catch(AuthenticationError e) {
			Logger.getGlobal().log(Level.SEVERE, "Error in loging process");
			e.printStackTrace();
		} catch(IOException e){
			e.printStackTrace();
		}
		
	}


}
