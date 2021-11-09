/**
 * 
 */
package application;

/**
 * Contain all app scenes
 * @author Ã�ngelLucas
 *
 */
public enum AppScenes {
 LOGIN("Login.fxml"), READER("NewsReader.fxml"), 
 NEWS_DETAILS ("ArticleDetails.fxml"),
 EDITOR("ArticleEdit.fxml"), ADMIN("AdminNews.fxml")
		 ,IMAGE_PICKER("ImagePicker.fxml")
		 /*,IMAGE_PICKER("ImagePickerMateriallDesign.fxml")*/; 
 private String fxmlFile;
 
 private AppScenes (String file){
	 this.fxmlFile = file;
 }
 
 public String getFxmlFile()
 {
	 return this.fxmlFile;
 }
 
}
