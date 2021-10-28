module NewsManager {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires com.jfoenix;
	requires javafx.web;
	requires org.glassfish.java.json;
	requires java.logging;
	requires jdk.jsobject;
	requires javafx.swing;
	requires javafx.base;
	
	opens application to javafx.controls, javafx.graphics, javafx.fxml, com.jfoenix;
	exports application;
	
//To use jfoneix you must add to jvn: --add-opens java.base/java.lang.reflect=com.jfoenix  --add-exports javafx.controls/com.sun.javafx.scene.control.behavior=com.jfoenix
}
