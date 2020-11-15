import java.util.HashMap;

import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


class SignUpCardHolder implements EventHandler<MouseEvent>{
	
	private HashMap<String, CardHolder> holders;
	private TextField email;
	private TextField name;
	private Label warning;
	private Label really;
	
	public SignUpCardHolder(HashMap<String, CardHolder> holders, TextField email, TextField name, Label warning, Label really) {
		this.holders = holders;
		this.email = email;
		this.name = name;
		this.warning = warning;
		this.really = really;
	}

	@Override
	public void handle(MouseEvent event) {
		if (holders.containsKey(email.getText())) {
			warning.setText("Email already exist.");
			really.setText(null);
		}else if (email.getText().equals("") || name.getText().equals("")){
			warning.setText("Input cannot be empty.");
			really.setText(null);
		}
		else {
			CardHolder holder = new CardHolder(name.getText(), email.getText());
			holders.put(email.getText(), holder);
			really.setText("Luckily no need for Email varification!");
			warning.setText(null);
			email.clear();
			name.clear();
			System.out.println(holders);//testing
		}
	}
}
