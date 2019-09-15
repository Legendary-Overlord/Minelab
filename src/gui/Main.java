package gui;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	@Override
	public void start(Stage stage) throws IOException {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("Minecraft.fxml"));
			Scene scene = new Scene(root);
			stage.setTitle("Minecraft");
			stage.setScene(scene);
			stage.show();
		} catch( Exception e) {
			e.printStackTrace();
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}
}