package transitapp;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * this class handle the event of constructing a bus line after user pressing the comfirm button beside bus stop input textfield.
 * take a empty list "busline" and two TextField "text_busline" and "text_bus" as input, then form a busline using BusLine class
 *"num_bus_line" just for testing propose now
 * empty list "busline" store the information of busline in main for future usage.
 */
class ConstructBusLine implements EventHandler<MouseEvent>{
	
	private TextField text_bus;
	private ArrayList<BusLine> busline;
	private int num_bus_line;
	private TextField text_busline;
	
	
	public ConstructBusLine (ArrayList<BusLine> busline, TextField text_busline, TextField text_bus, int num_bus_line) {

		this.text_bus = text_bus;
		this.num_bus_line = num_bus_line;
		this.busline = busline;
		this.text_busline = text_busline;
		

		
	}

	@Override
	public void handle(MouseEvent event) {
		
		
		BusLine b1 = new BusLine("Bus Line " + this.text_busline.getText(), this.text_bus.getText());
		busline.add(b1);
		this.text_bus.clear();
		this.text_busline.clear();
		
		System.out.println(busline.get(this.num_bus_line));
		this.num_bus_line += 1;
		System.out.println(this.text_bus.getText());
		System.out.println(b1.getNodes());
		
	}
	
	
	
}

/**
 * this class handle the event of constructing a subway line after user pressing the comfirm button beside subway stations input textfield.
 * take a empty list "subwayline" and two TextField "text_subwayline" and "text_subway" as input, then form a subwayline using SubwayLine class
 * empty list "busline" store the information of busline in main for future usage.
 */
class ConstructSubwayStation implements EventHandler<MouseEvent>{
	
	private TextField text_subway;
	private ArrayList<SubwayLine> subwayline;
	private TextField text_subwayline;
	private Button bt_subway;
	
	public ConstructSubwayStation(ArrayList<SubwayLine> subwayline, TextField text_subwayline, TextField text_subway, Button bt_subway) {
		
		this.text_subway = text_subway;
		this.subwayline = subwayline;
		this.text_subwayline = text_subwayline;
		this.bt_subway = bt_subway;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		this.subwayline.add(new SubwayLine("Subway Line " + this.text_subwayline.getText(), this.text_subway.getText())); 
		this.text_subway.clear();
		this.text_subwayline.clear();
		this.bt_subway.setDisable(true);
		
		
		
		System.out.println(this.text_subway.getText());
		System.out.println(this.subwayline);
		
	}
	
}
