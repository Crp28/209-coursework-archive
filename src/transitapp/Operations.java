import java.util.ArrayList;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;


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


class ShowDashboard implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public ShowDashboard(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		output.getChildren().clear();
		Label balance_reminder = new Label("No card is low on balance.");
		ArrayList<Integer> low_balance_cards = new ArrayList<>();
		balance_reminder.setTextFill(Color.GREEN);
		String cards = "Currently holding cards: ";
		String temp = "";
		for (Card c: Main.activeholder.getCards()) {
			if (c.equals(Main.activeholder.getCards().get(Main.activeholder.getCards().size()-1))) {
				temp += c.getId();
			} else {
				temp += c.getId() + ", ";
			}

			if (c.getBalance() < 6.0) {
				low_balance_cards.add(c.getId());
			}
		}
		if (temp.equals("")) {
			cards += "None";
		}else {
			cards += temp;
		}
		
		if (!low_balance_cards.isEmpty()) {
			String s = "Card(s) have low balance: ";
			for (Integer i: low_balance_cards) {
				if (i.equals(low_balance_cards.get(low_balance_cards.size()-1))) {
					s += i;
				}else {
					s += i + ", ";
				}
			}
			balance_reminder.setText(s);
			balance_reminder.setTextFill(Color.CRIMSON);
		}
		
		output.getChildren().add(new Label(cards));
		output.getChildren().add(new Label("Accumulated " + Main.activeholder.getTrips().size() + " trips in this period."));
		output.getChildren().add(balance_reminder);
	}
	
}


class ShowUserInfo implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public ShowUserInfo(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		TextField name = new TextField(Main.activeholder.getName());
		name.setAlignment(Pos.CENTER);
		name.setMaxWidth(300);
		Button changename = new Button("Update Name");
		Label name_changed = new Label();
		output.getChildren().clear();
		output.getChildren().add(new Label("Email: " + Main.activeholder.getEmail()));
		output.getChildren().add(new Label("Name: "));
		output.getChildren().add(name);
		output.getChildren().add(name_changed);
		output.getChildren().add(changename);

		changename.setOnAction(new ChangeName(name, name_changed));
		
		
		
	}
	
}

class ChangeName implements EventHandler<ActionEvent>{
	
	private TextField name;
	private Label name_changed;
	
	public ChangeName(TextField name, Label name_changed) {
		this.name = name;
		this.name_changed = name_changed;
	}

	@Override
	public void handle(ActionEvent event) {
		Main.activeholder.updateName(name.getText());
		this.name_changed.setText("Success! Name updated to: " + Main.activeholder.getName());
		name_changed.setTextFill(Color.GREEN);
	}
	
}

class ShowTrips implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public ShowTrips(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		output.getChildren().clear();
		output.getChildren().add(new Label("789"));
		
	}
	
}