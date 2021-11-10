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
	    private TextArea articleBody;
	    
		@FXML
		private TextArea articleAbstract;
		
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
	            articleAbstract.setText(article.getAbstractText());
	            articleBody.setText(article.getBodyText());
	            if (article.getImageData() != null) {
					imageView.setImage(article.getImageData());
				}
	            
	            articleAbstract.setEditable(false);
	            articleBody.setEditable(false);
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
		    	return;
		   }
		   
		   if (articleAbstract.isVisible()){
		   	articleAbstract.setVisible(false);
		   	articleBody.setVisible(true);
		        return;
		   }
    }
}
