// These is a placeholder package and placeholder class
// Feel free to rename or remove these when you add in your own code (just make sure to add/commit/push any changes made,
//		and let your teammates know to pull the changes. Follow the workflow in the a2 instructions)

package transitapp;

import java.io.FileInputStream;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	private void initUI(Stage stage) {
		//label part of the cover
		Label cover_title = new Label("Welcome to Rontoto");
		cover_title.setFont(new Font("Cambria", 32));
		
		//botton part of the cover page
		Image image = new Image(getClass().getResourceAsStream(""));
		ImageView imageView =new ImageView(image);
		Button bt_map = new Button("create a new map");
		bt_map.setMaxSize(500, 500);
		bt_map.setStyle("-fx-font-size:20");
		bt_map.setGraphic(imageView);
		
		//border pane chosen for the cover, setting up all the elements
		BorderPane cover_pane = new BorderPane();
		BorderPane.setAlignment(cover_title, Pos.CENTER);
		cover_pane.setTop(cover_title);
		cover_pane.setCenter(bt_map);
		
		//scene 
		Scene cover_scene = new Scene(cover_pane, 500, 500);
		
		//stage preparation
		stage.setTitle("Transit App");
		stage.setScene(cover_scene);
		stage.show();
	}
	
	public static void main(String[] args) {
		
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		initUI(stage);
	}
}












