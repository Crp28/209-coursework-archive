package transitapp;

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