// These is a placeholder package and placeholder class
// Feel free to rename or remove these when you add in your own code (just make sure to add/commit/push any changes made,
//		and let your teammates know to pull the changes. Follow the workflow in the a2 instructions)

package transitapp;

import java.util.ArrayList;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static int num_bus_line = 0; //testing purpose
	public static String[] bus_stops_group;
	public static String[]subway_stations_group;
	
	
	private void initUI(Stage stage) {
	
	/*below is the cover scene part, including a boardpane with a botton on it*/	
		//label part of the cover
		Label page1 = new Label("P1");
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
		cover_pane.setBottom(page1);
		BorderPane.setAlignment(page1, Pos.BOTTOM_RIGHT);
		//scene of cover
		Scene cover_scene = new Scene(cover_pane, 500, 500);
	
		
	/*station scene start*/
		Label page2 = new Label("P2");
		Label label_station_input = new Label("input the name of bus stop and subway station........");
		Label label_bus = new Label("Bus Stop(s):");
		Label label_busline = new Label("Bus Line:");
		Label label_subway = new Label("Subway Station:");
		Label label_subwayline = new Label("Subway Line:");
		label_subway.setFont(new Font("Cambria", 18));
		label_bus.setFont(new Font("Cambria", 18));
		label_busline.setFont(new Font("Cambria", 18));
		label_subwayline.setFont(new Font("Cambria", 18));
		
		TextField field_bus = new TextField();
		TextField field_busline = new TextField();
		TextField field_subway = new TextField();
		TextField field_subwayline = new TextField();
		
		Button bt_next = new Button("NEXT");
		Button bt_bus = new Button("Comfirm");
		Button bt_subway = new Button("Comfirm");	
		
		GridPane station_input_pane = new GridPane();
		station_input_pane.setVgap(20);
		station_input_pane.setHgap(5);
		GridPane.setConstraints(label_bus, 1 , 0);
		GridPane.setConstraints(label_busline, 0, 0);
		GridPane.setConstraints(field_busline, 0, 1);
		GridPane.setConstraints(field_bus, 1, 1);
		GridPane.setConstraints(bt_bus, 2, 1);
		GridPane.setConstraints(label_subwayline, 0, 2);
		GridPane.setConstraints(label_subway, 1, 2);
		GridPane.setConstraints(field_subwayline, 0, 3);
		GridPane.setConstraints(field_subway, 1, 3);
		GridPane.setConstraints(bt_subway, 2, 3);
		
		station_input_pane.getChildren().addAll(label_bus, label_subway, field_bus, field_subway, bt_bus, bt_subway, label_busline, field_busline, label_subwayline, field_subwayline);
		station_input_pane.setAlignment(Pos.CENTER);
		
		BorderPane p2_bottom_pane = new BorderPane();
		p2_bottom_pane.setCenter(bt_next);
		p2_bottom_pane.setBottom(page2);
		BorderPane stationpage_pane = new BorderPane();
		stationpage_pane.setTop(label_station_input);
		stationpage_pane.setCenter(station_input_pane);
		stationpage_pane.setBottom(p2_bottom_pane);
		BorderPane.setAlignment(label_station_input, Pos.CENTER);
		BorderPane.setAlignment(bt_next, Pos.BASELINE_CENTER);
		BorderPane.setAlignment(page2, Pos.BOTTOM_RIGHT);
		
		
		Scene station_scene = new Scene(stationpage_pane, 500, 500);
		
	
	/*Action will happen after pressing button in cover pages (p1)*/
		bt_construct.setOnAction(new SwitchScene(stage, station_scene));
		
	
	/*pressing "Comfirm" button for constructing bus line and subway station (p2)*/
		ArrayList<BusLine> busline = new ArrayList<>();
		bt_bus.addEventHandler(MouseEvent.MOUSE_CLICKED, new ConstructBusLine(busline, field_busline, field_bus, num_bus_line));
		
		ArrayList<SubwayLine> subwayline = new ArrayList<>(); 
		bt_subway.addEventHandler(MouseEvent.MOUSE_CLICKED, new ConstructSubwayStation(subwayline,field_subwayline, field_subway, bt_subway));
		
		
	/*set transfer scene*/
		Label page3 = new Label("P3");
		Label p3_top_choicebus = new Label("check your bus line(s):");
		Label p3_top_choicesubway = new Label("check your subway line:");
		
		TextField p3_toptextbus = new TextField();
		p3_toptextbus.setEditable(false);
		TextField p3_toptextsubway = new TextField();
		p3_toptextsubway.setEditable(false);
		
		ArrayList<String> name_busline = new ArrayList<>();
		ObservableList<String> observable_busname = FXCollections.observableArrayList(name_busline);
		ChoiceBox<String> choice_busline = new ChoiceBox<String>(observable_busname);
		ArrayList<String> name_subwayline = new ArrayList<>();
		ObservableList<String> observable_subwayname = FXCollections.observableArrayList(name_subwayline);
		ChoiceBox<String> choice_subwayline = new ChoiceBox<String>(observable_subwayname);
		choice_busline.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue,
					java.lang.Number newValue) {
				p3_toptextbus.setText(bus_stops_group[(int) newValue]);
				}
		});
		choice_subwayline.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue,
					java.lang.Number newValue) {
				p3_toptextsubway.setText(subway_stations_group[(int) newValue]);
				}
		});
		
		
		GridPane p3_topane = new GridPane();
		p3_topane.setHgap(10);
		p3_topane.setVgap(10);
		GridPane.setConstraints(p3_top_choicebus, 0, 0);
		GridPane.setConstraints(choice_busline, 1, 0);
	    GridPane.setConstraints(p3_toptextbus, 2, 0);
		GridPane.setConstraints(p3_top_choicesubway, 0, 1);
		GridPane.setConstraints(choice_subwayline, 1, 1);
		GridPane.setConstraints(p3_toptextsubway, 2, 1);
		p3_topane.setAlignment(Pos.CENTER);
		p3_topane.getChildren().addAll(p3_top_choicebus, choice_busline, p3_toptextbus, p3_top_choicesubway, choice_subwayline, p3_toptextsubway);
		
		BorderPane set_trans_pane = new BorderPane();
		set_trans_pane.setTop(p3_topane);
		
		
		Scene setTransfer_scene = new Scene(set_trans_pane, 500, 500);
		
		
	/*pressing "NEXT" button on page2*/
		bt_next.setOnAction(new SwitchAndDo1(stage, setTransfer_scene, observable_busname, busline, observable_subwayname, subwayline));
		
	
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
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












