/**
 * 
 */
package application;

import java.io.File;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.function.Predicate;

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

/**
 * @author ÃngelLucas
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


		

	private void getData() {
		//TODO retrieve data and update UI
		 newsReaderModel.retrieveData();
		 categoryCombo.getItems().addAll(newsReaderModel.getCategories());
		 articleList.getItems().addAll(newsReaderModel.getArticles());

		 articleList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Article>() {
				 @Override
					/**
					 * When the selected element is changed this event handler is called
					 */
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
		 
		 ContextMenu contextMenu = new ContextMenu();
		 
		 MenuItem login = new MenuItem("Login");
		 MenuItem edit = new MenuItem("Edit");
		 MenuItem LoadFile = new MenuItem("Load File");
		 MenuItem deleteArticle = new MenuItem("Delete Article");
		 MenuItem newArticle = new MenuItem("New Article");
		 MenuItem exit = new MenuItem("Exit");
		 
		 login.setOnAction(new EventHandler<ActionEvent>() {
			 @Override
			 public void handle(ActionEvent event) {
				 System.out.print("login");
			 }
		 });
		 
		 contextMenu.getItems().add(login);
//		 contextMenu.getItems().add(edit);
//		 contextMenu.getItems().add(LoadFile);
//		 contextMenu.getItems().add(deleteArticle);
//		 contextMenu.getItems().add(newArticle);
//		 contextMenu.getItems().add(exit);

		 EventHandler<WindowEvent> event = new EventHandler<WindowEvent>() {
			 public void handle(WindowEvent e)
			 {
				 System.out.print(" event ");
				 if(contextMenu.isShowing()) System.out.print("showing");
				 else System.out.print("not showing");
			 }
		 };
		 
		 contextMenu.setOnShowing(event);
		 contextMenu.setOnHiding(event);
		 
		 menuButton.setContextMenu(contextMenu);

		//The method newsReaderModel.retrieveData() can be used to retrieve data  
	}

	/**
	 * @return the usr
	 */
	User getUsr() {
		return usr;
	}

	void setConnectionManager (ConnectionManager connection){
		this.newsReaderModel.setDummyData(false); //System is connected so dummy data are not needed
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


}
