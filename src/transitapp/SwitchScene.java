import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
/**
 * The class will help us switch to the scene that we want.
 * Take a stage and a specified scene as input.
 */
class SwitchScene implements EventHandler<ActionEvent>{
		
	private Stage stage;
	private Scene scene;
	
	
	public SwitchScene(Stage stage, Scene scene) {
		this.stage = stage;
		this.scene = scene;
	}
	
	@Override
	public void handle(ActionEvent event) {
		this.stage.setScene(this.scene);
		
	}
}

/**
 * this class helps us construct the choice boxes for buslines/stops and subwaylines/stations.
 * take observable list "name_busline" and "name_subwayline" as input which can be updated in main.
 * input list "busline" and "subwayline" are the two list that constructed by TransitSystemConstructor.java.
 * iterating in "busline" and "subwayline" , adding lines' name in observable list for constructing choice box
 * and creating array for listener input are things this class do.
 * At the end it helps us switch to page 3 which is set transfer scene. 
 */
class SwitchAndDo1 implements EventHandler<ActionEvent>{
	
	private Stage stage;
	private Scene scene;
	private ObservableList<String> name_busline;
	private ArrayList<BusLine> busline;
	private ObservableList<String> name_subwayline;
	private ArrayList<SubwayLine> subwayline;
	private Label warninglabel;
	
	
	public SwitchAndDo1(Stage stage, Scene scene, ObservableList<String> observable_busname, ArrayList<BusLine> busline, ObservableList<String> name_subwayline, ArrayList<SubwayLine> subwayline, Label lb) {
		this.stage = stage;
		this.scene = scene;
	    this.name_busline = observable_busname;
	    this.busline = busline;
	    this.name_subwayline = name_subwayline;
	    this.subwayline = subwayline;
	    this.warninglabel = lb;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		
		
		try{		
			this.name_subwayline.add(this.subwayline.get(0).getName());
			String temp_stations = new String();
			for (Node temp_subwaystations: this.subwayline.get(0).getNodes()) {
				if (temp_subwaystations.equals(this.subwayline.get(0).getNodes().get(this.subwayline.get(0).getNodes().size()-1))) {
					temp_stations += temp_subwaystations.getName();
				}else {
					temp_stations += temp_subwaystations.getName() + ", ";
				}
			}
			
			ArrayList<String> temp_bus_stops = new ArrayList<String>();
			ArrayList<ArrayList<Node>> nested_busnode = new ArrayList<ArrayList<Node>>();
			for (BusLine bl: this.busline) {
				this.name_busline.add(bl.getName());
				nested_busnode.add(bl.getNodes());
			}
			for (ArrayList<Node> lst_bus_stops: nested_busnode) {
				String temp_stops_name = new String();
				for(Node temp_bus_node: lst_bus_stops) {
					if (temp_bus_node.equals(lst_bus_stops.get(lst_bus_stops.size()-1))){
						temp_stops_name += temp_bus_node.getName();
					}
					else{
						temp_stops_name += temp_bus_node.getName() + ", ";
					}
				}
				temp_bus_stops.add(temp_stops_name);
			}
			
			this.stage.setScene(this.scene);
		}catch(Exception e){
			this.warninglabel.setText("Construct ONE subway line.");
		}
		
		
		
	    
		
  }
}

/**
 * This event handler takes a inputed email and set the 
 * active CardHolder to the one this email is referring to,
 * while switching the scene to the user control panel.
 */
class SwitchAndDo2 implements EventHandler<ActionEvent>{
	private HashMap<String, CardHolder> holders;
	private TextField email;
	private Label warning;
	private Stage stage;
	private Scene scene;
	
	public SwitchAndDo2(Stage stage, Scene scene, HashMap<String, CardHolder> holders, TextField email, Label warning) {
		this.holders = holders;
		this.email = email;
		this.warning = warning;
		this.stage = stage;
		this.scene = scene;
	}

	@Override
	public void handle(ActionEvent arg0) {
		if (holders.containsKey(email.getText())) {
			Main.activeholder = this.holders.get(email.getText());
			warning.setText(null);
			this.stage.setScene(scene);
		}
		else {
			warning.setText("Email does not exist.");
		}
		
	}
	
}


class Logout implements EventHandler<ActionEvent>{
	
	private Stage stage;
	private Scene scene;
	private VBox output;
	
	
	public Logout(Stage stage, Scene scene, VBox output) {
		this.stage = stage;
		this.scene = scene;
		this.output = output;
	}
	
	@Override
	public void handle(ActionEvent event) {
		output.getChildren().clear();
		this.stage.setScene(this.scene);
		Main.activeholder = null;
		
	}
}