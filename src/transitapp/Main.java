// These is a placeholder package and placeholder class
// Feel free to rename or remove these when you add in your own code (just make sure to add/commit/push any changes made,
//		and let your teammates know to pull the changes. Follow the workflow in the a2 instructions)

package transitapp;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	private void initUI(Stage stage) {
		
	/*below is the cover scene part, including a boardpane with a botton on it*/	
		//label part of the cover
		Label label_cover_title = new Label("Welcome to Rontoto");
		label_cover_title.setFont(new Font("Cambria", 32));
		//botton part of the cover page
		Image image = new Image("/transitapp/attachment/plan-map-vector-icon-png_264735.png/");
		ImageView imageView =new ImageView(image);
		imageView.setFitHeight(75);
		imageView.setFitWidth(90);
		Button bt_construct = new Button("construct transit system");
		bt_construct.setMaxSize(400, 100);
		bt_construct.setStyle("-fx-font-size:20");
		bt_construct.setGraphic(imageView);
		//border pane chosen for the cover, setting up all the elements
		BorderPane cover_pane = new BorderPane();
		BorderPane.setAlignment(label_cover_title, Pos.CENTER);
		cover_pane.setTop(label_cover_title);
		cover_pane.setCenter(bt_construct);
		//scene of cover
		Scene cover_scene = new Scene(cover_pane, 500, 500);
	
		
	/*station scene start*/
		Label label_station_input = new Label("input the name of bus stop and subway station........");
		Label label_bus = new Label("Bus stops:");
		label_bus.setFont(new Font("Cambria", 22));
		Label label_subway = new Label("Subway stations:");
		label_subway.setFont(new Font("Cambria", 22));
		
		TextField field_bus = new TextField();
		TextField field_subway = new TextField();
		
		Button bt_form = new Button("Form");
		Button bt_bus = new Button("Comfirm");
		Button bt_subway = new Button("Comfirm");		
		
		GridPane station_input_pane = new GridPane();
		station_input_pane.setVgap(70);
		station_input_pane.setHgap(5);
		GridPane.setConstraints(label_bus, 0 , 0);
		GridPane.setConstraints(label_subway, 0, 1);
		GridPane.setConstraints(field_bus, 1, 0);
		GridPane.setConstraints(field_subway, 1, 1);
		GridPane.setConstraints(bt_bus, 2, 0);
		GridPane.setConstraints(bt_subway, 2, 1);
		station_input_pane.getChildren().addAll(label_bus, label_subway, field_bus, field_subway, bt_bus, bt_subway);
		station_input_pane.setAlignment(Pos.CENTER);
		
		BorderPane stationpage_pane = new BorderPane();
		stationpage_pane.setTop(label_station_input);
		stationpage_pane.setCenter(station_input_pane);
		stationpage_pane.setBottom(bt_form);
		BorderPane.setAlignment(label_station_input, Pos.CENTER);
		BorderPane.setAlignment(bt_form, Pos.CENTER);
		
		
		Scene station_scene = new Scene(stationpage_pane, 500, 500);

		
	/*Action will happen after pressing button in cover pages*/
		bt_construct.setOnAction(new SwitchScene(stage, station_scene));
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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












