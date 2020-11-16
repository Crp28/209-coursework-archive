import java.util.ArrayList;
import java.util.HashMap;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;


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
		String cards = "Currently holding card ids: ";
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
		Label cards_str = new Label(cards);
		cards_str.setWrapText(true);
		cards_str.setTextAlignment(TextAlignment.CENTER);
		Label avg_cost = new Label();
		double tc = 0.0;
		for (Trip trip: Main.activeholder.getTrips()) {
			tc += trip.totalCost();
		}
		String ac = "Average cost per trip for this period: ";
		ac += tc/Main.activeholder.getTrips().size();
		avg_cost.setText(ac);
		output.getChildren().add(cards_str);
		output.getChildren().add(new Label("Accumulated " + Main.activeholder.getTrips().size() + " trips in this period."));
		output.getChildren().add(avg_cost);
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

class ManageCards implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public ManageCards(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		Label remove_description = new Label("or select a following card to remove: ");
		Label confirmation_message = new Label();
		Button add_card = new Button("Get a New Card");
		Button remove_card = new Button("Confirm");
		ArrayList<Integer> ids = new ArrayList<>();
		for (Card c: Main.activeholder.getCards()) {
			ids.add(c.getId());
		}
		ObservableList<Integer> list_cards = FXCollections.observableArrayList(ids);
		ChoiceBox<Integer> choices = new ChoiceBox<>(list_cards);
		choices.setPrefWidth(50);
		
		add_card.setOnAction(new AddCard(confirmation_message, list_cards));
		remove_card.setOnAction(new RemoveCard(confirmation_message, choices, list_cards));
		
		output.getChildren().clear();
		this.output.getChildren().addAll(add_card, remove_description, choices, remove_card, confirmation_message);
	}
	
}

class AddCard implements EventHandler<ActionEvent>{
	
	private Label confirmation_message;
	private ObservableList<Integer> list_cards;
	
	public AddCard(Label confirmation_message, ObservableList<Integer> list_cards) {
		this.confirmation_message = confirmation_message;
		this.list_cards = list_cards;
	}

	@Override
	public void handle(ActionEvent event) {
		Card new_c = new Card(Main.activeholder);
		Main.activeholder.addCard(new_c);
		this.list_cards.add(new_c.getId());
		confirmation_message.setText("Successfully added card: " + new_c.getId());
		confirmation_message.setTextFill(Color.GREEN);
	}
	
}

class RemoveCard implements EventHandler<ActionEvent>{
	
	private Label confirmation_message;
	private ChoiceBox<Integer> choices;
	private ObservableList<Integer> list_cards;
	
	public RemoveCard(Label confirmation_message, ChoiceBox<Integer> choices, ObservableList<Integer> list_cards) {
		this.choices = choices;
		this.confirmation_message = confirmation_message;
		this.list_cards = list_cards;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			Main.activeholder.removeCard(choices.getSelectionModel().getSelectedIndex());
			this.list_cards.remove(choices.getSelectionModel().getSelectedIndex());
			this.confirmation_message.setText("Card removed.");
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			this.confirmation_message.setText("Why DIDN'T you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
	
}


class AddBalance implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public AddBalance(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		Label description = new Label("Select a card: ");
		Label confirmation_message = new Label();
		ArrayList<Integer> ids = new ArrayList<>();
		for (Card c: Main.activeholder.getCards()) {
			ids.add(c.getId());
		}
		ObservableList<Integer> list_cards = FXCollections.observableArrayList(ids);
		ChoiceBox<Integer> choices = new ChoiceBox<>(list_cards);
		Button add_10 = new Button("Add $10");
		Button add_20 = new Button("Add $20");
		Button add_50 = new Button("Add $50");
		HBox buttons = new HBox(30, add_10, add_20, add_50);
		buttons.setAlignment(Pos.CENTER);
		
		add_10.setOnAction(new AddTen(choices, confirmation_message));
		add_20.setOnAction(new AddTwenty(choices, confirmation_message));
		add_50.setOnAction(new AddFifty(choices, confirmation_message));
		
		output.getChildren().clear();
		output.getChildren().addAll(description, choices, buttons, confirmation_message);
		VBox.setMargin(buttons, new Insets(50,0,0,0));
	}
}

class AddTen implements EventHandler<ActionEvent>{
	
	private ChoiceBox<Integer> choices;
	private Label confirmation_message;
	
	public AddTen(ChoiceBox<Integer> choices, Label confirmation_message) {
		this.choices = choices;
		this.confirmation_message = confirmation_message;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try {
			double curr_balance = Main.activeholder.getCards().get(choices.getSelectionModel().getSelectedIndex()).addBalance(10.0);
			this.confirmation_message.setText("Added $10 to card " + choices.getValue() + ". Current balance: $" + curr_balance + ".");
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			this.confirmation_message.setText("Why DIDN'T you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
}

class AddTwenty implements EventHandler<ActionEvent>{
	
	private ChoiceBox<Integer> choices;
	private Label confirmation_message;
	
	public AddTwenty(ChoiceBox<Integer> choices, Label confirmation_message) {
		this.choices = choices;
		this.confirmation_message = confirmation_message;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try {
			double curr_balance = Main.activeholder.getCards().get(choices.getSelectionModel().getSelectedIndex()).addBalance(20.0);
			this.confirmation_message.setText("Added $20 to card " + choices.getValue() + ". Current balance: $" + curr_balance + ".");
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			this.confirmation_message.setText("Why DIDN'T you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
}

class AddFifty implements EventHandler<ActionEvent>{
	
	private ChoiceBox<Integer> choices;
	private Label confirmation_message;
	
	public AddFifty(ChoiceBox<Integer> choices, Label confirmation_message) {
		this.choices = choices;
		this.confirmation_message = confirmation_message;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try {
			double curr_balance = Main.activeholder.getCards().get(choices.getSelectionModel().getSelectedIndex()).addBalance(50.0);
			this.confirmation_message.setText("Added $50 to card " + choices.getValue() + ". Current balance: $" + curr_balance + ".");
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(java.lang.ArrayIndexOutOfBoundsException e) {
			this.confirmation_message.setText("Why DIDN'T you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
}