/**
 * 
 */
package application;


import application.news.Article;
import application.news.User;

/**
 * @author ÁngelLucas
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
	   	private TextField title;

	    	@FXML
	    	private TextField subtitle;

	    	@FXML
	    	private TextArea bodyText;

	    	@FXML
	    	private TextArea abstractText;


	    

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
		   if (bodyText.isVisible()) {
		    	bodyText.setVisible(false);
		    	abstractText.setVisible(true);
		    	abstractText.setText(abstracthtml.getHtmlText());
		    	return;
		   }

		   if (bodyhtml.isVisible()) {
			bodyhtml.setVisible(false);
		    	abstracthtml.setVisible(true);
		    	abstracthtml.setHtmlText(abstractText.getText());
		    	return;
		   }
		   if (abstractText.isVisible()){
		   	abstractText.setVisible(false);
		   	bodyText.setVisible(true);
		   	bodyText.setText(bodyhtml.getHtmlText());
		        return;
		   }

		   if (abstracthtml.isVisible()) {
			abstracthtml.setVisible(false);
			bodyhtml.setVisible(true);
			bodyhtml.setHtmlText(bodyText.getText());
		        return;
		   }
    }
}
