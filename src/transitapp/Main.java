

import java.util.ArrayList;
import java.util.HashMap;

import javafx.application.Application;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
	
	private static int num_bus_line = 0; //testing purpose
	public static int chosen_busline_index;
	public static String chosen_stop;
	public static String chosen_station;
	private ArrayList<BusLine> busline;
	private ArrayList<SubwayLine> subwayline;
	private HashMap<String, CardHolder> holders = new HashMap<>();
	public static CardHolder activeholder;
	
	
	private void initUI(Stage stage) {
	
	/*below is the cover scene part, including a BoardPane with a button on it*/	
		//label part of the cover
		Label page1 = new Label("P1");
		Label label_cover_title = new Label("Rontoto Transit System");
		label_cover_title.setFont(new Font("Cambria", 32));
		//button part of the cover page
		Image image = new Image("/attachment/plan-map-vector-icon-png_264735.png/");
		ImageView imageView =new ImageView(image);
		imageView.setFitHeight(75);
		imageView.setFitWidth(90);
		Button bt_construct = new Button("Construct transit system..");
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
		Label label_warning = new Label();
		label_subway.setFont(new Font("Cambria", 18));
		label_bus.setFont(new Font("Cambria", 18));
		label_busline.setFont(new Font("Cambria", 18));
		label_subwayline.setFont(new Font("Cambria", 18));
		label_warning.setFont(new Font("Cambria", 15));
		label_warning.setTextFill(Color.CRIMSON);
		
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
		GridPane.setConstraints(label_warning, 0, 4);
		GridPane.setConstraints(bt_subway, 2, 3);
		
		station_input_pane.getChildren().addAll(label_bus, label_subway, field_bus, field_subway, bt_bus, bt_subway, label_busline, field_busline, label_subwayline, field_subwayline, label_warning);
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
		
	
	/*pressing "Confirm" button for constructing bus line and subway station (p2)*/
		busline = new ArrayList<>();
		bt_bus.addEventHandler(MouseEvent.MOUSE_CLICKED, new ConstructBusLine(busline, field_busline, field_bus, num_bus_line));
		
		subwayline = new ArrayList<>(); 
		bt_subway.addEventHandler(MouseEvent.MOUSE_CLICKED, new ConstructSubwayStation(subwayline,field_subwayline, field_subway, bt_subway, label_warning));
		
		
	/*set transfer scene*/
		Label page3 = new Label("P3");
		Label p3_top_choicebus = new Label("Select a bus stop...");
		Label p3_top_choicesubway = new Label("Select a subway station...");
		
		Button set_intersect = new Button("Set intersect!");
		Button btn_p3tonext = new Button("READY TO GO!");
		
		ArrayList<String> non_ob_sb = new ArrayList<String>();
		ObservableList<String> selected_busstops = FXCollections.observableArrayList(non_ob_sb);
		ChoiceBox<String> choice_busstops = new ChoiceBox<String>(selected_busstops);
		ArrayList<String> non_ob_ss = new ArrayList<String>();
		ObservableList<String> selected_stations = FXCollections.observableArrayList(non_ob_ss);
		ChoiceBox<String> choice_stations = new ChoiceBox<String>(selected_stations);
		
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
				chosen_busline_index = ((int) newValue);
				selected_busstops.clear();
				for (Node node: busline.get((int) newValue).getNodes()) {
					selected_busstops.add(node.getName());
				}
			}
		});
		choice_subwayline.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue,
					java.lang.Number newValue) {
				selected_stations.clear();
				for (Node node: subwayline.get((int) newValue).getNodes()) {
					selected_stations.add(node.getName());
				}
			}
		});
		choice_stations.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue,
					java.lang.Number newValue) {
				chosen_station = selected_stations.get((int) newValue);
			}
			
		});
		choice_busstops.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends java.lang.Number> observable, java.lang.Number oldValue,
					java.lang.Number newValue) {
				try {
					chosen_stop = selected_busstops.get((int) newValue);
				} 
				catch(ArrayIndexOutOfBoundsException e){
					chosen_stop = "";
				}
				
			}
			
		});
		set_intersect.addEventHandler(MouseEvent.MOUSE_CLICKED, new SetIntersectBS(subwayline, busline));
		
		GridPane p3_topane = new GridPane();
		//Appearance design needed.
		p3_topane.setHgap(10);
		p3_topane.setVgap(10);
		GridPane.setConstraints(p3_top_choicebus, 0, 0);
		GridPane.setConstraints(choice_busline, 0, 1);
	    GridPane.setConstraints(choice_busstops, 1, 1);
		GridPane.setConstraints(p3_top_choicesubway, 0, 3);
		GridPane.setConstraints(choice_subwayline, 0, 4);
		GridPane.setConstraints(choice_stations, 1, 4);
		
		p3_topane.setAlignment(Pos.CENTER);
		p3_topane.getChildren().addAll(choice_stations, choice_busstops, p3_top_choicebus, choice_busline, p3_top_choicesubway, choice_subwayline);
		
		BorderPane set_trans_pane = new BorderPane();
		set_trans_pane.setTop(p3_topane);
		set_trans_pane.setBottom(btn_p3tonext);
		BorderPane.setAlignment(btn_p3tonext, Pos.BOTTOM_RIGHT);
		set_trans_pane.setCenter(set_intersect);
		BorderPane.setAlignment(set_intersect, Pos.BOTTOM_CENTER);
		
		
		Scene setTransfer_scene = new Scene(set_trans_pane, 500, 500);
		
		
	/*pressing "NEXT" button on page2*/
		bt_next.setOnAction(new SwitchAndDo1(stage, setTransfer_scene, observable_busname, busline, observable_subwayname, subwayline, label_warning));
		
	/*set up cardholder-admin selection scene*/
		Label user_admin_selection_description = new Label("Are you a...?");
		user_admin_selection_description.setFont(new Font("Cambria", 26));
		Button select_admin = new Button("Login as Admin");
		select_admin.setPrefSize(200, 200);
		Button select_cardholder = new Button("Login as Cardholder");
		select_cardholder.setPrefSize(200, 200);
		
		HBox selection_hbox = new HBox(60, select_admin, select_cardholder);
		selection_hbox.setAlignment(Pos.CENTER);
		
		BorderPane user_admin_pane = new BorderPane();
		user_admin_pane.setTop(user_admin_selection_description);
		BorderPane.setAlignment(user_admin_selection_description, Pos.CENTER);
		BorderPane.setMargin(user_admin_selection_description, new Insets(30,0,0,0));
		user_admin_pane.setCenter(selection_hbox);
		Scene setSelection_scene = new Scene(user_admin_pane, 500, 500);
		
		
	/*pressing "READY TO GO!" button on page3*/
		btn_p3tonext.setOnAction(new SwitchScene(stage, setSelection_scene));
		
		
		
	/*Set up Cardholder login scene*/
		Label enter_email = new Label("Email:" );
		enter_email.setFont(new Font("Times New Roman", 20));
		TextField email_entered = new TextField();
		email_entered.setMaxWidth(300);
		Button login = new Button("login");
		Label warning_email_not_exist = new Label();
		warning_email_not_exist.setTextFill(Color.CRIMSON);
		Hyperlink btn_newuser = new Hyperlink("New User? Sign up here --->");  //direct to sign up page
		btn_newuser.setBorder(Border.EMPTY);
		btn_newuser.setStyle("-fx-text-fill: #00a2ff;");
		
		VBox login_vbox = new VBox(30, enter_email, email_entered, warning_email_not_exist);
		VBox.setMargin(email_entered, new Insets(0,0,0,100));
		VBox.setMargin(enter_email, new Insets(0,0,0,100));
		VBox.setMargin(warning_email_not_exist, new Insets(0,0,0,100));
		BorderPane login_pane = new BorderPane();
		
		login_pane.setCenter(login);
		BorderPane.setMargin(login, new Insets(50,0,0,0));
		login_pane.setBottom(btn_newuser);
		login_pane.setTop(login_vbox);
		BorderPane.setMargin(login_vbox, new Insets(100,0,0,0));
		BorderPane.setAlignment(btn_newuser, Pos.BOTTOM_RIGHT);
		
		
		
		Scene setLogin_scene = new Scene(login_pane, 500, 500);
		
	/*pressing "Login as Cardholder" button on selection scene*/
		select_cardholder.setOnAction(new SwitchScene(stage, setLogin_scene));
		
	/*Set up sign up scene*/
		Label enter_email_copy = new Label("Email:" );
		TextField email_entered_copy = new TextField();
		Label enter_name = new Label("Name: ");
		TextField name_entered = new TextField();
		Label warning_existing = new Label();
		warning_existing.setTextFill(Color.CRIMSON);
		Label reallyLOL = new Label();
		reallyLOL.setTextFill(Color.GREEN);
		Hyperlink btn_backtologin = new Hyperlink("Already a Cardholder? Sign in --->"); //need SwitchScene handler to go back to login scene
		btn_backtologin.setBorder(Border.EMPTY);
		btn_backtologin.setStyle("-fx-text-fill: #00a2ff;");
		Button btn_signup = new Button("Sign Up");
		
		GridPane signup_grid = new GridPane();
		GridPane.setConstraints(enter_email_copy, 0, 0);
		GridPane.setMargin(enter_email_copy, new Insets(20,20,20,20));
		GridPane.setConstraints(email_entered_copy, 2, 0);
		GridPane.setMargin(email_entered_copy, new Insets(20,20,20,20));
		GridPane.setConstraints(warning_existing, 0, 2);
		GridPane.setConstraints(enter_name, 0, 1);
		GridPane.setMargin(enter_name, new Insets(20,20,20,20));
		GridPane.setConstraints(name_entered, 2, 1);
		GridPane.setMargin(name_entered, new Insets(20,20,20,20));
		GridPane.setConstraints(btn_signup, 1, 3);
		
		signup_grid.getChildren().addAll(enter_email_copy, email_entered_copy, warning_existing, enter_name, name_entered, btn_signup, reallyLOL);
		signup_grid.setAlignment(Pos.CENTER);
		
		BorderPane signup_pane = new BorderPane();
		signup_pane.setCenter(signup_grid);
		BorderPane.setMargin(signup_pane, new Insets(0,0,50,0));
		signup_pane.setBottom(btn_backtologin);
		signup_pane.setTop(reallyLOL);
		BorderPane.setAlignment(btn_backtologin, Pos.BOTTOM_RIGHT);
		
		btn_signup.addEventHandler(MouseEvent.MOUSE_CLICKED, new SignUpCardHolder(this.holders, email_entered_copy, name_entered, warning_existing, reallyLOL));
		
		
		Scene setSignup_scene = new Scene(signup_pane, 500, 500);
	
		
	/*pressing hyperlink on login scene*/
		btn_newuser.setOnAction(new SwitchScene(stage, setSignup_scene));
	/*pressing hyperlink on signup scene*/
		btn_backtologin.setOnAction(new SwitchScene(stage, setLogin_scene));
		
	/*Set up User control panel*/
		Image mysteriousman = new Image("/attachment/man.png/");
		ImageView imageMysteriousman =new ImageView(mysteriousman);
		imageMysteriousman.setFitHeight(80);
		imageMysteriousman.setFitWidth(90);
		Button logout = new Button("Logout");
		logout.setPrefSize(120, 70);
		Button add_card = new Button("Add Card");
		add_card.setPrefSize(120, 70);
		Button add_balance = new Button("Add Banlance");
		add_balance.setPrefSize(120, 70);
		Button suspend_card = new Button("Suspend Card");
		suspend_card.setPrefSize(120, 70);
		
		HBox control_tabs = new HBox(10, imageMysteriousman, add_card, add_balance, suspend_card);
		control_tabs.setStyle("-fx-background-color: #2ec3e7;");
		control_tabs.setAlignment(Pos.CENTER);
		
		Hyperlink dashboard = new Hyperlink("Dashboard");
		dashboard.setBorder(Border.EMPTY);
		dashboard.setStyle("-fx-text-fill: #00a2ff;");
		
		VBox control_links = new VBox(20, dashboard);
		
		BorderPane control_panel = new BorderPane();
		control_panel.setTop(control_tabs);
		control_panel.setCenter(logout);
		control_panel.setLeft(control_links);
		
		logout.setOnAction(new SwitchScene(stage, setSelection_scene));
		
		Scene set_User_control_panel_scene = new Scene(control_panel, 500, 500);
		
		
		
	/*log the user in*/
		login.setOnAction(new SwitchAndDo2(stage, set_User_control_panel_scene, this.holders, email_entered, warning_email_not_exist));
		
		
		
		
		
		
		
		
		
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