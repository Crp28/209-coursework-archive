package transitapp;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
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
	
	
	public SwitchAndDo1(Stage stage, Scene scene, ObservableList<String> observable_busname, ArrayList<BusLine> busline, ObservableList<String> name_subwayline, ArrayList<SubwayLine> subwayline) {
		this.stage = stage;
		this.scene = scene;
	    this.name_busline = observable_busname;
	    this.busline = busline;
	    this.name_subwayline = name_subwayline;
	    this.subwayline = subwayline;
	}
	
	@Override
	public void handle(ActionEvent event) {
		
		ArrayList<String> temp_bus_stops = new ArrayList<String>();
		ArrayList<ArrayList<Node>> nested_busnode = new ArrayList<ArrayList<Node>>();
		for (BusLine bl: this.busline) {
			this.name_busline.add(bl.getName());
			nested_busnode.add(bl.getNodes());
		}
		for (ArrayList<Node> lst_bus_stops: nested_busnode) {
			String temp_stops_name = new String();
			for(Node temp_bus_node: lst_bus_stops) {
				temp_stops_name += temp_bus_node.getName() + ", ";
			}
			temp_bus_stops.add(temp_stops_name);
		}
		String[] bus_stops_group = new String[this.name_busline.size()];
		temp_bus_stops.toArray(bus_stops_group);
		Main.bus_stops_group = bus_stops_group;
		
		this.name_subwayline.add(this.subwayline.get(0).getName());
		String temp_stations = new String();
		for (Node temp_subwaystations: this.subwayline.get(0).getNodes()) {
			temp_stations += temp_subwaystations.getName() + ", ";
		}
		String[] subway_station_groups = new String[1];
		subway_station_groups[0] = temp_stations;
		Main.subway_stations_group = subway_station_groups;
		
	    this.stage.setScene(this.scene);
		
  }
}