/**
 * 
 */
package application;

import application.news.Article;
import application.news.Categories;
import application.news.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 * @author Ã�ngelLucas
 *
 */
public class ArticleDetailsController {
	
	    private User usr;
	    private ArticleEditModel viewArticle;
	
		@FXML
		private Label userName;
	
		@FXML
	   	private Label title;

	    @FXML
	    private Label subtitle;
	    
	    @FXML
	    private Label articleCategory;

	    @FXML
	    private WebView articleBody;
	    
		@FXML
		private WebView articleAbstract;
		
		@FXML
		private Button switchButton;
		
	    @FXML
		private ImageView imageView;

	    @FXML
	    void initialize() {
	    }

		/**
		 * @param usr the user to set
		 */
		void setUsr(User usr) {
			this.usr = usr;
		}
		
	    /**
	     * PRE: User must be set
	     *
	     * @param article the article to set
	     */
	    void setArticle(Article article) {
	        this.viewArticle = (article != null) ? new ArticleEditModel(article) : new ArticleEditModel(usr);
	        if (article != null) {
	            title.setText(article.getTitle());
	            subtitle.setText(article.getSubtitle());
	            articleCategory.setText(article.getCategory());
	            articleAbstract.getEngine().loadContent(article.getAbstractText());
	            articleBody.getEngine().loadContent(article.getBodyText());
	            if (article.getImageData() != null) {
					imageView.setImage(article.getImageData());
				}
	          
	        }
	        if(this.usr != null) {
	        	userName.setText(this.usr.getLogin());
	        }
	    }
	    
	    Article getArticle() {
	        Article result = null;
	        if (this.viewArticle != null) {
	            result = this.viewArticle.getArticleOriginal();
	        }
	        return result;
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
		    	switchButton.setText("Body");
		    	return;
		   }
		   
		   if (articleAbstract.isVisible()){
		   	articleAbstract.setVisible(false);
		   	articleBody.setVisible(true);
		   	switchButton.setText("Abstract");
		        return;
		   }
    }
}
