package application.news;

import java.awt.image.BufferedImage;
import java.util.Date;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
/**
 * This class represents an article. An article is formed by:
 * - A title
 * - A subtitle
 * - An Image
 * - HTML text for the abstract
 * - HTML text for the body
 * - An article could be mark as deleted (only administrator users can see an modify it)
 * - An article belong to one category
 * @author agonzalez
 */
public class Article {
	//Binding attributes: getters and setters methods are providing in order to hiding the use of properties
	private BooleanProperty isDeleted;
	private StringProperty  abstractText;
	private StringProperty  bodyText;
	private StringProperty title;
	private StringProperty  subtitle;
	private Date publicationDate;
	
	//Author id User
	private int idUser;
	//Article Image
	private Image imageData;
	//IDs. These ids are needed for storing the information in the server
	private int idArticle = 0; //0 -> New Article so don't exists in the BD
	private int idImage = 0; //0 -> New Image so don't exists in the BD
	
	private String  category = Categories.ALL.name();
	//if needBeSaved is true the article was modified by a set method or by a property 
	private boolean needBeSaved = false;
	/**
	 * Default constructor
	 */
	public Article()
	{
		abstractText =  new SimpleStringProperty(this, "abstractText","");
		bodyText =  new SimpleStringProperty(this, "bodyText","");
		title = new SimpleStringProperty(this, "title","");
		subtitle = new SimpleStringProperty(this, "subtitle","");
		isDeleted= new SimpleBooleanProperty (this,"isDeleted", false);
		needBeSaved = false;
	}
	
	/**
	 * Copy constructor
	 * @param org article to be copied
	 */
	public Article (Article org){
		this(org.getAbstractText(),
				org.getBodyText(), org.getTitle(),
				org.publicationDate, org.idUser, null,
				org.category);
		this.setDeleted(org.getDeleted());
		this.setSubtitle(org.getSubtitle());
		this.setImageData(org.getImageData());
	}
	
	public Article(String title, int idUser, String category) {
		this(); //Calling default constructor
		this.title.setValue(title);
		this.idUser = idUser;
		this.category = category;
	}
	
	public Article(String title, int idUser, String category, String abstractText) {
		this( title, idUser, category);
		this.abstractText.setValue(abstractText);
	}
	
	public Article(String title, int idUser, String category,
			String abstractText,String urlImage) {
		this(title, idUser, category,abstractText);
		this.setUrlImage(urlImage);
	}
	
	public Article(String abstractText, String bodyText, String title,
			Date publicationDate, int idUser, String urlImage, String category) {
		this(title, idUser, category,abstractText,urlImage);
		this.bodyText.setValue(bodyText);
		if (publicationDate!=null){
			this.publicationDate = (Date)publicationDate.clone();
		}
		else{
			this.publicationDate = publicationDate;
		}
	}
	
	/**
	 * Provide access to abstractText property. 
	 * Useful for binding operations
	 * @return abstract property
	 */
	public StringProperty abstractTextProperty(){
		return abstractText;
	}
	
	//Getter and setters
	
	/**
	 * Provide access to bodyText property. 
	 * Useful for binding operations
	 * @return body property
	 */
	public StringProperty bodyTextProperty(){
		return bodyText;
	}
	
	/**
	 * @return the abstractText
	 */
	public String getAbstractText() {
		return abstractText.getValue();
	}
	
	
	/**
	 * @return the bodyText
	 */
	public String getBodyText() {
		return bodyText.getValue();
	}
	
	/**
	 * @return the category
	 */
	public String getCategory() {
		return category;
	}
	
	public boolean getDeleted(){
	 return isDeleted.getValue();	
	}
	
	/**
	 * @return the idArticle
	 */
	public int getIdArticle() {
		return idArticle;
	}
	
	/**
	 * @return the idImage
	 */
	public int getIdImage() {
		return idImage;
	}
	
	
	/**
	 * @return the idUser
	 */
	public int getIdUser() {
		return idUser;
	}
	
	/**
	 * 
	 * @return the image data
	 */
	public Image getImageData(){
		return this.imageData;
	}
	
	/**
	 * @return the publicationDate
	 */
	public Date getPublicationDate() {
		return publicationDate;
	}
	
	/**
	 * @return the subtitle
	 */
	public String getSubtitle() {
		return subtitle.getValue();
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title.getValue();
	}
	
	
	public boolean isDeleted(){
		return isDeleted.getValue();
	}
	
	/**
	 * Provide access to isDeleted property. 
	 * Useful for binding operations
	 * @return isDeletd property
	 */
	public BooleanProperty isDeletedProperty(){
		return isDeleted;
	}
	
	/**
	 * @return the needBeSaved
	 */
	public boolean isNeedBeSaved() {
		return needBeSaved;
	}
	
	/**
	 * @param string the abstractText to set
	 */
	public void setAbstractText(String string) {
		this.abstractText.setValue(string);
	}
	
	/**
	 * @param bodyText the bodyText to set
	 */
	public void setBodyText(String bodyText) {
		this.bodyText.setValue(bodyText);
	}
	
	/**
	 * @param category the category to set
	 */
	public void setCategory(String category) {
		this.category = category;
		this.setNeedBeSaved(true);
	}
	
	public void setDeleted(boolean deleted){
	 isDeleted.setValue(deleted);	
	}
	/**
	 * @param idArticle the idArticle to set
	 */
	public void setIdArticle(int idArticle) {
		this.idArticle = idArticle;
	}
	
	/**
	 * @param idImage the idImage to set
	 */
	public void setIdImage(int idImage) {
		this.idImage = idImage;
	}
	
	/**
	 * @param idUser the idUser to set
	 */
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	
	/**
	 * Set the image data
	 * @param data data for the image
	 */
	public void setImageData(BufferedImage data){
		this.imageData = SwingFXUtils.toFXImage(data, null);
		this.setNeedBeSaved(true);
	}
	
	/**
	 * Set the image data
	 * @param data image to use in the article
	 */
	public void setImageData(Image data){
		this.imageData = data;
		this.setNeedBeSaved(true);
	}
	
	public void setNeedBeSaved(boolean need){
		this.needBeSaved = need;
	}
	
	/**
	 * @param publicationDate the publicationDate to set
	 */
	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	/**
	 * @param subtitle the subtitle to set
	 */
	public void setSubtitle(String subtitle) {
		this.subtitle.setValue(subtitle);
	}

	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title.setValue(title);
	}

	/**
	 * @param urlImage the urlImage to set
	 */
	public void setUrlImage(String urlImage) {
		if (urlImage!=null){
			imageData = new Image(urlImage, false);	
			this.setNeedBeSaved(true);
		}
		
	}

	/**
	 * Provide access to subtitle property. 
	 * Useful for binding operations
	 * @return title property
	 */
	public StringProperty subtitleProperty(){
		return subtitle;
	}
	

	/**
	 * Provide access to title property. 
	 * Useful for binding operations
	 * @return title property
	 */
	public StringProperty titleProperty(){
		return title;
	}	
	
	/**
	 * Return a string with the article title
	 */
	public String toString()
	{
		return this.title.getValue();
	}
}