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
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.transformation.FilteredList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.print.Collation;
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


public class ArticleEditController {

    private ConnectionManager connection;
    private ArticleEditModel editingArticle;
    private User usr;
    //TODO add attributes and methods as needed


    @FXML
    private TextField title;

    @FXML
    private TextField subtitle;

    @FXML
    private TextArea bodyText;

    @FXML
    private TextArea abstractText;
    
    @FXML
    private HTMLEditor abstracthtml;

    @FXML
    private HTMLEditor bodyhtml;
    
    @FXML
    private ChoiceBox<Categories> categoryBox;

    @FXML
    private ImageView imageView;

    @FXML
    private Button sendbtn,showbodybtn,showabstractbtn;

    private boolean isHtmlEditor = true;


    
    
    public void setUser(User usr) {
		this.usr = usr;
	}

    @FXML
    void initialize() {

    	setArticle(null);
	        categoryBox.getItems().addAll(Categories.values());
        
    }

    @FXML
    void onImageClicked(MouseEvent event) {
        if (event.getClickCount() >= 2) {
            Scene parentScene = ((Node) event.getSource()).getScene();
            FXMLLoader loader = null;
            try {
                loader = new FXMLLoader(getClass().getResource(AppScenes.IMAGE_PICKER.getFxmlFile()));
                Pane root = loader.load();
                // Scene scene = new Scene(root, 570, 420);
                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
                Window parentStage = parentScene.getWindow();
                Stage stage = new Stage();
                stage.initOwner(parentStage);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UNDECORATED);
                stage.initModality(Modality.WINDOW_MODAL);
                stage.showAndWait();
                ImagePickerController controller = loader.<ImagePickerController>getController();
                Image image = controller.getImage();
                if (image != null) {
                    editingArticle.setImage(image);
                    this.imageView.setImage(image);
                    //TODO Update image on UI
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    @FXML
    public void sendAndBack(Event event) {
        boolean send = send();
        
      
        Stage stage1 = (Stage) ((Node) event.getSource()).getScene().getWindow();
    	stage1.close();
    	
        
    	
    	if(editingArticle.isbModified())
    	{
    		FXMLLoader loader = new FXMLLoader (getClass().getResource(
      				AppScenes.READER.getFxmlFile()));
           NewsReaderController controller = loader.<NewsReaderController>getController();
            controller.setConnectionManager(this.connection);
        	controller.setUsr(usr);

    	}
	
    			
//        FXMLLoader loader = new FXMLLoader (getClass().getResource(
//				AppScenes.READER.getFxmlFile()));
//		try {
//			Pane root = loader.load();
//			Stage stage = new Stage();
//			stage.initModality(Modality.WINDOW_MODAL);
//	        //stage.setTitle(AppScenes.READER.toString());
//	        stage.setScene(new Scene(root));
//	        NewsReaderController controller = loader.<NewsReaderController>getController();
//	        controller.setConnectionManager(this.connection);
//        	controller.setUsr(usr);
//        	stage.show();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
    }

    /**
     * Send and article to server,
     * Title and category must be defined and category must be different to ALL
     *
     * @return true if the article has been saved
     */
    private boolean send() {
        String titleText = title.getText();
        Categories category = categoryBox.getValue();
        if (titleText == null || category == null ||
                titleText.equals("") || category == Categories.ALL) {
            Alert alert = new Alert(AlertType.ERROR, "Imposible send the article!! Title and categoy are mandatory", ButtonType.OK);
            alert.showAndWait();
            return false;
        }
//TODO prepare and send using connection.saveArticle( ...)
        //this.editingArticle.commit();
        Article article = new Article();
        article.setCategory(category.toString());
        
        
        

        if (isHtmlEditor) {
            article.setBodyText(bodyhtml.getHtmlText());
            article.setAbstractText(abstracthtml.getHtmlText());
            
		}else {
			article.setBodyText(bodyText.getText());
	        article.setAbstractText(abstractText.getText());
		}
        
        

        
        article.setDeleted(false);
        article.setIdUser(usr.getIdUser());
        if (this.editingArticle != null && this.editingArticle.getArticleOriginal() != null) {
        	 article.setIdArticle(this.editingArticle.getArticleOriginal().getIdArticle());
		}
       
       
        article.setNeedBeSaved(true);
        article.setSubtitle(subtitle.getText());
        article.setTitle(titleText);
        
        //this.onImageClicked();
        Image imagedata =  imageView.getImage();
        if (imagedata != null) {
        	article.setImageData(imagedata);
		}
        try {
            connection.saveArticle(article);
        } catch (ServerCommunicationError serverCommunicationError) {
            serverCommunicationError.printStackTrace();
        }
        return true;
    }


//    /**
//	 * This method is used to set the connection manager which is
//	 * needed to save a news
//	 *
//	 * @param connection connection manager
//	 */
//	void setConnectionMannager(ConnectionManager connection) {
//	    this.connection = connection;
//	
//		if (this.usr == null) {
//			this.sendbtn.setDisable(true);
//		}
//		else {
//			this.sendbtn.setDisable(false);
//		}
//	}

	/**
     * This method is used to set the connection manager which is
     * needed to save a news
     *
     * @param connection connection manager
     */
    void setConnectionMannager(ConnectionManager connection) {
        this.connection = connection;

    	if (this.usr == null) {
    		this.sendbtn.setDisable(true);
		}
    	else {
    		this.sendbtn.setDisable(false);
    	}
		
    }

    /**
     * @param usr the usr to set
     */
    void setUsr(User usr) {
        this.usr = usr;
        //TODO Update UI and controls
    	if (this.usr == null) {
    		this.sendbtn.setDisable(true);
		}
    	else {
    		this.sendbtn.setDisable(false);
		}

    }

    Article getArticle() {
        Article result = null;
        if (this.editingArticle != null) {
            result = this.editingArticle.getArticleOriginal();
        }
        return result;
    }


    @FXML
    void saveFile() {
        String titleText = title.getText();
        Categories category = categoryBox.getValue();
        if (titleText == null || category == null ||
                titleText.equals("") || category == Categories.ALL) {
            Alert alert = new Alert(AlertType.ERROR, "Imposible send the article!! Title and categoy are mandatory", ButtonType.OK);
            alert.showAndWait();
            return;
        }
//TODO prepare and send using connection.saveArticle( ...)
        Article article = new Article();
        article.setTitle(titleText);
        article.setCategory(category.toString());
        article.setDeleted(false);
        article.setSubtitle(subtitle.getText());
        article.setBodyText(bodyText.getText());
        article.setAbstractText(abstractText.getText());
//        article.setBodyText(bodyhtml.getHtmlText());
//        article.setAbstractText(abstracthtml.getHtmlText());
        Image image = imageView.getImage();
        if (image != null) {
            article.setImageData(image);
        }
        setArticle(article);
        write();
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("Save successfully!");
        alert.show();
    }

    /**
     * PRE: User must be set
     *
     * @param article the article to set
     */
    void setArticle(Article article) {
        this.editingArticle = (article != null) ? new ArticleEditModel(article) : new ArticleEditModel(usr);
        //TODO update UI
        if (article != null) {
            title.setText(article.getTitle());
            title.setEditable(false);
            subtitle.setText(article.getSubtitle());
            Categories[] catesCategories =  Categories.values();
            for (Categories cate: catesCategories) {
				if (cate.toString().equals(article.getCategory())) {
					 categoryBox.getSelectionModel().select(cate);
					 break;
				}
				
			}
           
            bodyhtml.setHtmlText(article.getBodyText());
            bodyText.setText(article.getBodyText());
            abstractText.setText(article.getAbstractText());
            abstracthtml.setHtmlText(article.getAbstractText());
            if (article.getImageData() != null) {
				imageView.setImage(article.getImageData());
			}
            
            //binding
            
            this.editingArticle.abstractTextProperty().bind(abstractText.textProperty());
            this.editingArticle.bodyTextProperty().bind(bodyText.textProperty()); 
            this.editingArticle.titleProperty().bind(title.textProperty());
            this.editingArticle.subtitleProperty().bind(subtitle.textProperty());
            
//            editingArticle.abstractTextProperty().bind(abstracthtml.accessibleTextProperty());
//            editingArticle.bodyTextProperty().bind(bodyhtml.accessibleTextProperty());
 

            

            }

    }

    /**
     * Save an article to a file in a json format
     * Article must have a title
     */
    private void write() {
        //TODO Consolidate all changes 	  	
    	
    	
    	
        this.editingArticle.commit();
        //Removes special characters not allowed for filenames
        String name = this.getArticle().getTitle().replaceAll("\\||/|\\\\|:|\\?", "");
        String fileName = "saveNews" + File.separator + name + ".news";
        JsonObject data = JsonArticle.articleToJson(this.getArticle());
        try (FileWriter file = new FileWriter(fileName)) {
            file.write(data.toString());
            file.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    public void back(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }


    @FXML
    public void showText(){
    	 if (abstractText.isVisible()) {
    		 abstractText.setVisible(false);
			 abstracthtml.setVisible(true);
			 isHtmlEditor =true;
			 abstracthtml.setHtmlText(abstractText.getText());
         } else if (bodyText.isVisible())  {
        	 bodyText.setVisible(false);
        	 bodyhtml.setVisible(true);
        	 isHtmlEditor =true;
        	bodyhtml.setHtmlText(bodyText.getText());
		} else if (abstracthtml.isVisible()) {
			abstracthtml.setVisible(false);
   		 	abstractText.setVisible(true);
   		 	isHtmlEditor =false;
   		 	abstractText.setText(abstracthtml.getHtmlText());
		} else if (bodyhtml.isVisible()) {
			bodyhtml.setVisible(false);
   		 	bodyText.setVisible(true);
   		 	isHtmlEditor =false;
   		    bodyText.setText(bodyhtml.getHtmlText());
		}
//         editingArticle.abstractTextProperty().set(abstracthtml.getHtmlText());
//         editingArticle.bodyTextProperty().set(bodyhtml.getHtmlText());
    }

    @FXML
    public void showBody(){
        if (bodyText.isVisible()) {
            bodyText.setVisible(false);
            abstractText.setVisible(true);
            isHtmlEditor =false;
            abstractText.setText(abstracthtml.getHtmlText());
            showbodybtn.setVisible(false);
            showabstractbtn.setVisible(true);
            return;
        }
        
        if (bodyhtml.isVisible()) {
        	bodyhtml.setVisible(false);
            abstracthtml.setVisible(true);
            isHtmlEditor =true;
            abstracthtml.setHtmlText(abstractText.getText());
            showbodybtn.setVisible(false);
            showabstractbtn.setVisible(true);
            return;
        }
        if (abstractText.isVisible()){
            abstractText.setVisible(false);
            bodyText.setVisible(true);
            isHtmlEditor =true;
            bodyText.setText(bodyhtml.getHtmlText());
            showbodybtn.setVisible(false);
            showabstractbtn.setVisible(true);
          return;
        }
        
        if (abstracthtml.isVisible()) {
        	abstracthtml.setVisible(false);
        	bodyhtml.setVisible(true);
            isHtmlEditor =true;

        	bodyhtml.setHtmlText(bodyText.getText());
            showbodybtn.setVisible(false);
            showabstractbtn.setVisible(true);
            return;
        }
    }
    

}
