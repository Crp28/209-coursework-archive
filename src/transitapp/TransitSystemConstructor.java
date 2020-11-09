package transitapp;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

class ConstructBusLine implements EventHandler<MouseEvent>{
	
	private TextField text_bus;
	private ArrayList<BusLine> busline;
	private int num_bus_line;
	
	public ConstructBusLine (ArrayList<BusLine> busline, TextField text_bus, int num_bus_line) {

		this.text_bus = text_bus;
		this.num_bus_line = num_bus_line;
		this.busline = busline;

		
	}

	@Override
	public void handle(MouseEvent event) {
		
		this.num_bus_line += 1;
		BusLine b1 = new BusLine("Bus Line NO." + this.num_bus_line, this.text_bus.getText());
		busline.add(b1);
		this.text_bus.clear();

//		System.out.println(busline.get(this.num_bus_line));
//		System.out.println(this.text_bus.getText());
		
	}
	
	
	
}

class ConstructSubwayStation implements EventHandler<MouseEvent>{
	
	private TextField text_subway;
	private SubwayLine subwayline;
	private int num_subway_line;
	
	public ConstructSubwayStation(SubwayLine subwayline, TextField text_subway, int num_subway_line) {
		
		this.text_subway = text_subway;
		this.subwayline = subwayline;
		this.num_subway_line = num_subway_line;
		
	}

	@Override
	public void handle(MouseEvent event) {
		
		this.subwayline = new SubwayLine("Subway Line NO." + this.num_subway_line, this.text_subway.getText());
		this.text_subway.clear();
		
//		System.out.println(this.text_subway.getText());
//		System.out.println(this.subwayline);
		
	}
	
}
