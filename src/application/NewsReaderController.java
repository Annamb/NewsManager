/**
 * 
 */
package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
import javafx.event.Event;
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
import javafx.scene.control.MenuButton;
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
 * @author 胣gelLucas
 *
 */
public class NewsReaderController {

	@FXML
	private Label userName;
	
	@FXML
    private ListView<Article> articleList;
	
	
	@FXML
	private ImageView articleImage;
	
	@FXML
	private TextArea articleAbstract;
	
	@FXML
	private ComboBox<Categories> categoryCombo;
	
	@FXML
	private MenuButton menuButton;
	
	@FXML
	private MenuItem newButton;
	
	@FXML
	private MenuItem editButton;
	
	@FXML
	private MenuItem deleteButton;
	
	@FXML
	private Button ReadMoreButton;
	
    private FilteredList<Article> filteredData;
	private NewsReaderModel newsReaderModel = new NewsReaderModel();
	private User usr;
	
	Article chosenArticle;
	
	private ConnectionManager connectionManager;

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
						articleAbstract.setText(chosenArticle.getAbstractText());
						articleImage.setImage(chosenArticle.getImageData());
						
						articleAbstract.setEditable(false);
					}
					else { //Nothing selected
						

					}
				}
	 });
		
		articleList.getSelectionModel().selectFirst();
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
		 categoryCombo.getSelectionModel().selectFirst();
		 
    	filteredData = new FilteredList<>(newsReaderModel.getArticles(), article -> true);
    	
    	this.articleList.setItems(filteredData);
    	
    	if(this.usr == null) {
    		this.deleteButton.setDisable(true);
    		this.editButton.setDisable(true);
    	}else {
    		this.deleteButton.setDisable(false);
    		this.editButton.setDisable(false);
    	}
	}

	/**
	 * @return the usr
	 */
	User getUsr() {
		return usr;
	}

	void setConnectionManager (ConnectionManager connection){
		//this.newsReaderModel.setDummyData(false); //System is connected so dummy data are not needed
		this.connectionManager = connection;
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
		userName.setText(this.usr.getLogin());
	}
	
	public void ClickEdit(Event e) {
		NewScene(AppScenes.EDITOR, this.chosenArticle, e);
	}
	
	public void ClickNew(Event e) {
		NewScene(AppScenes.EDITOR, null, e);
	}
	
	public void ClickObserve(Event e) {
		NewScene(AppScenes.NEWS_DETAILS, this.chosenArticle, e);
	}
	
	public void ClickLogin(Event e) {
		NewScene(AppScenes.LOGIN, null,e );
	}
	
	public void ClickDelete() {

		
	}
	
	
	@FXML
	public void LoadArticleFromFile(Event e) {
		FileChooser chooser =  new FileChooser();
		chooser.setTitle("打开文件");// 设置文件对话框的标题
		
		chooser.getExtensionFilters().addAll(
		new FileChooser.ExtensionFilter("所有文件", "*.*"),
		new FileChooser.ExtensionFilter("所有图片", "*.jpg", "*.gif", "*.bmp", "*.png"));
		File file = chooser.showOpenDialog(new Stage());// 显示文件打开对话框
		if (file == null) {
			return;
		}
		
		try {
			Article article = JsonArticle.jsonToArticle(JsonArticle.readFile(file.getAbsolutePath()));
			NewScene(AppScenes.EDITOR, article, e);
		} catch (ErrorMalFormedArticle ex) {
			// TODO Auto-generated catch block
			ex.printStackTrace();
		}
	}
	

	public void ClickExit(ActionEvent event) {
		Stage stage = (Stage)((MenuItem)event.getTarget()).getParentPopup().getOwnerWindow();
    	stage.close();
	}
	

	
	public void NewScene(AppScenes scene, Article article, Event event) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader (getClass().getResource(
					scene.getFxmlFile()));
			Pane root = loader.load();
			Stage stage = new Stage();
			stage.initModality(Modality.WINDOW_MODAL);
            stage.setTitle(scene.toString());
            stage.setScene(new Scene(root));
            
            if (scene == AppScenes.EDITOR) {
            	ArticleEditController controller = loader.<ArticleEditController>getController();
            	
            	if (article != null) {
            		controller.setArticle(article);
            	
				}
            	controller.setConnectionMannager(this.connectionManager);
            	controller.setUsr(usr);
            	stage.show();
            	return;
			} else if(article == null) {
            	
            	LoginController controller = loader.<LoginController>getController();
            	
    			Properties prop = Main.buildServerProperties();
    			ConnectionManager connection = new ConnectionManager(prop);
    			connection.setAnonymousAPIKey("ANON04");
    			controller.setConnectionManager(connection);
    			
    			stage.showAndWait();
    			
            	User loggedInUsr = controller.getLoggedUsr();
            	
            	if (loggedInUsr != null) {
            		setUsr(loggedInUsr);
            	}
			 } else if(scene == AppScenes.NEWS_DETAILS) {
				 ArticleDetailsController controller = loader.<ArticleDetailsController>getController();
	            	
	             if (article != null) {
	            	controller.setArticle(article);
				 }
	            	
	             controller.setUsr(usr);
	             stage.show();
	             return;
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
