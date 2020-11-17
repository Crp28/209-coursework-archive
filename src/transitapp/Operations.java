import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
			this.confirmation_message.setText("Why don't you select a card??");
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
		
		choices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				for (Card c : Main.activeholder.getCards()) {
					if (c.getId() == list_cards.get((int) newValue)) {
						confirmation_message.setTextFill(Color.BLACK);
						confirmation_message.setText("Card " + c.getId() + " has balance $" + c.getBalance() + ".");
					}
				}				
			}
			
		});
		
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
			this.confirmation_message.setText("Why don't you select a card??");
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
			this.confirmation_message.setText("Why don't you select a card??");
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
			this.confirmation_message.setText("Why don't you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
}

class SuspendCard implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public SuspendCard(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent arg0) {
		Label suspend_des = new Label("Select a card to suspend:");
		Label activate_des = new Label("Select a card to activate:");
		Label confirmation_message = new Label();
		ArrayList<Integer> normal_cards = new ArrayList<>();
		ArrayList<Integer> suspended_cards = new ArrayList<>();
		for (Card c: Main.activeholder.getCards()) {
			if (c.getStatus()) {
				normal_cards.add(c.getId());
			} else if (!c.getStatus() && c.getBalance() > 0.0){
				suspended_cards.add(c.getId());
			}
		}
		ObservableList<Integer> normal_cards_ob = FXCollections.observableArrayList(normal_cards);
		ObservableList<Integer> suspended_cards_ob = FXCollections.observableArrayList(suspended_cards);
		ChoiceBox<Integer> normal_choices = new ChoiceBox<>(normal_cards_ob);
		ChoiceBox<Integer> suspended_choices = new ChoiceBox<>(suspended_cards_ob);
		Button suspend = new Button("Suspend");
		Button activate = new Button("Activate");
		
		VBox sus_box = new VBox(20, suspend_des, normal_choices, suspend);
		sus_box.setAlignment(Pos.CENTER);
		VBox act_box = new VBox(20, activate_des, suspended_choices, activate);
		act_box.setAlignment(Pos.CENTER);
		HBox format = new HBox(60, sus_box, act_box);
		format.setAlignment(Pos.CENTER);
		
		suspend.setOnAction(new Suspend(normal_choices, normal_cards_ob, suspended_cards_ob, confirmation_message));
		activate.setOnAction(new Activate(suspended_choices, normal_cards_ob, suspended_cards_ob, confirmation_message));
		
		output.getChildren().clear();
		output.getChildren().addAll(format, confirmation_message);
	}
	
}

class Suspend implements EventHandler<ActionEvent>{
	
	private ChoiceBox<Integer> normal_choices;
	private ObservableList<Integer> normal_cards_ob;
	private ObservableList<Integer> suspended_cards_ob;
	private Label confirmation_message;
	
	public Suspend(ChoiceBox<Integer> normal_choices, ObservableList<Integer> normal_cards_ob, ObservableList<Integer> suspended_cards_ob, Label confirmation_message) {
		this.normal_choices = normal_choices;
		this.normal_cards_ob = normal_cards_ob;
		this.suspended_cards_ob = suspended_cards_ob;
		this.confirmation_message = confirmation_message;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			for (Card c: Main.activeholder.getCards()) {
				if (this.normal_choices.getSelectionModel().getSelectedItem().equals(c.getId())) {
					this.normal_cards_ob.remove((Integer) c.getId());
					this.suspended_cards_ob.add((Integer) c.getId());
					Collections.sort(suspended_cards_ob);
					Main.activeholder.suspendCard(c);
					this.confirmation_message.setText("Card " + c.getId() + " suspended.");
					break;
				}
			}
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(Exception e){
			this.confirmation_message.setText("Why don't you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
	
}

class Activate implements EventHandler<ActionEvent>{
	
	private ChoiceBox<Integer> suspended_choices;
	private ObservableList<Integer> normal_cards_ob;
	private ObservableList<Integer> suspended_cards_ob;
	private Label confirmation_message;
	
	public Activate(ChoiceBox<Integer> suspended_choices, ObservableList<Integer> normal_cards_ob, ObservableList<Integer> suspended_cards_ob, Label confirmation_message) {
		this.suspended_choices = suspended_choices;
		this.normal_cards_ob = normal_cards_ob;
		this.suspended_cards_ob = suspended_cards_ob;
		this.confirmation_message = confirmation_message;
	}

	@Override
	public void handle(ActionEvent event) {
		try {
			for (Card c: Main.activeholder.getCards()) {
				if (this.suspended_choices.getSelectionModel().getSelectedItem().equals(c.getId())) {
					this.suspended_cards_ob.remove((Integer) c.getId());
					this.normal_cards_ob.add((Integer) c.getId());
					Collections.sort(normal_cards_ob);
					Main.activeholder.activateCard(c);
					this.confirmation_message.setText("Card " + c.getId() + " reactivated.");
					break;
				}
			}
			this.confirmation_message.setTextFill(Color.GREEN);
		} catch(Exception e){
			this.confirmation_message.setText("Why don't you select a card??");
			this.confirmation_message.setTextFill(Color.CRIMSON);
		}
		
	}
	
}

class CheckTrips implements EventHandler<ActionEvent>{
	
	private VBox output;
	
	public CheckTrips(VBox output) {
		this.output = output;
	}

	@Override
	public void handle(ActionEvent event) {
		Label recent_trips = new Label();
		String s = "";

			int count = 0;
			for (int i=Main.activeholder.getTrips().size()-1; i>0 && count<3; i--) {
				//if (Main.activeholder.getTrips().get(i).isFinished()) {
					s += Main.activeholder.getTrips().get(i).toString() + "\n";
					count++;
				//}
			}

		recent_trips.setText(s);
		
		output.getChildren().clear();
		output.getChildren().add(recent_trips);
	}
	
}

class TapCard implements EventHandler<ActionEvent>{
	
	private VBox output;
	private ArrayList<BusLine> busline;
	private ArrayList<SubwayLine> subwayline;
	
	public TapCard(VBox output, ArrayList<BusLine> busline, ArrayList<SubwayLine> subwayline) {
		this.output = output;
		this.busline = busline;
		this.subwayline = subwayline;
	}

	@Override
	public void handle(ActionEvent event) {
		Label warning = new Label();
		ArrayList<Card> cards = new ArrayList<>();
		for (Card c: Main.activeholder.getCards()) {
			if (c.getStatus()) {
				cards.add(c);
			}
		}
		ObservableList<Card> cards_ob = FXCollections.observableArrayList(cards);
		ChoiceBox<Card> card_choices = new ChoiceBox<>(cards_ob);
		
		Button select_card = new Button("Select");
		
		select_card.setOnAction(new TapCardPrep(this.output, this.busline, this.subwayline, cards_ob, card_choices, warning));
		
		this.output.getChildren().clear();
		this.output.getChildren().addAll(card_choices, select_card, warning);
		
}

class TapCardPrep implements EventHandler<ActionEvent>{
	private VBox output;
	private ArrayList<BusLine> busline;
	private ArrayList<SubwayLine> subwayline;
	private ObservableList<Card> cards_ob;
	private ChoiceBox<Card> card_choices;
	private Card card;
	private Label warning;
	
	public TapCardPrep(VBox output, ArrayList<BusLine> busline, ArrayList<SubwayLine> subwayline, ObservableList<Card> cards_ob, ChoiceBox<Card> card_choices, Label warning) {
		this.output = output;
		this.busline = busline;
		this.subwayline = subwayline;
		this.cards_ob = cards_ob;
		this.card_choices = card_choices;
		this.warning = warning;
	}

	@Override
	public void handle(ActionEvent arg0) {
		try {
			this.card = cards_ob.get(card_choices.getSelectionModel().getSelectedIndex());
			Label message = new Label();

			Label date_sign = new Label("Date: ");
			Label time_elapsed_sign = new Label("Time elapsed since last tap: ");
			TextField date_entered = new TextField();
			date_entered.setPromptText("mm-dd-yyyy");
			TextField seconds_entered = new TextField();
			seconds_entered.setPromptText("seconds");
			Button tap = new Button("Tap");
			tap.setPrefSize(80, 40);
			
			ArrayList<Line> lines = new ArrayList<>();
			for (BusLine bl: this.busline) {
				lines.add(bl);
			}
			for (SubwayLine sl: this.subwayline) {
				lines.add(sl);
			}
			ObservableList<Line> lines_ob = FXCollections.observableArrayList(lines);
//			if (!this.card.getTrip().isEmpty()) {
//				if (!this.card.getTrip().get(this.card.getTrip().size()-1).isFinished()) {
//					if (this.card.getTrip().get(this.card.getTrip().size()-1).getSegments().get(this.card.getTrip().get(this.card.getTrip().size()-1).getSegments().size()-1)[1] == null) {
//						lines_ob.clear();
//						lines_ob.add(this.card.getTrip().get(this.card.getTrip().size()-1).getLines().get(this.card.getTrip().get(this.card.getTrip().size()-1).getLines().size()-1));
//					}
//				}
//			}
			// because Trip won't work so all this part and below is broken...
			ObservableList<Node> selected_line = FXCollections.observableArrayList(new ArrayList<Node>());
			ChoiceBox<Line> line_choices = new ChoiceBox<>(lines_ob);
			ChoiceBox<Node> node_choices = new ChoiceBox<>(selected_line);
			
			HBox line_node_box = new HBox(40, line_choices, node_choices);
			
			line_choices.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
					selected_line.clear();
					for (Node n: lines_ob.get((int) newValue).getNodes()) {
						selected_line.add(n);
					}
				}
			});

			tap.setOnAction(new TapCardUpdate(this.output, this.card, line_choices, node_choices, date_entered, seconds_entered, message));
			
			this.output.getChildren().clear();
			this.output.getChildren().addAll(line_node_box, date_sign, date_entered, time_elapsed_sign, seconds_entered, tap, message);
			
		} catch(Exception e) {
			this.warning.setText("Why don't you select a card??");
			this.warning.setTextFill(Color.CRIMSON);
		}
		
	}
		
	}
	
}

class TapCardUpdate implements EventHandler<ActionEvent> {
	
	private VBox output;
	private Card card;
	private ChoiceBox<Line> line_choices;
	private ChoiceBox<Node> node_choices;
	private TextField date_entered;
	private TextField seconds_entered;
	private Label message;
	
	public TapCardUpdate(VBox output, Card card, ChoiceBox<Line> line_choices, ChoiceBox<Node> node_choices, TextField date_entered, TextField seconds_entered, Label message) {
		this.output = output;
		this.card = card;
		this.line_choices = line_choices;
		this.node_choices = node_choices;
		this.date_entered = date_entered;
		this.seconds_entered = seconds_entered;
		this.message = message;
	}

	@Override
	public void handle(ActionEvent event) {
		if (this.line_choices.getSelectionModel().getSelectedIndex() == -1 || this.date_entered.getText() == null || this.seconds_entered.getText() == null) {
			message.setText("All fields are REQUIRED!");
			message.setTextFill(Color.CRIMSON);
		}else {
			Node node = this.node_choices.getSelectionModel().getSelectedItem();
			Line line = this.line_choices.getSelectionModel().getSelectedItem();
			String date = this.date_entered.getText();
			int seconds = 0;
			try {
				seconds = Integer.parseInt(this.seconds_entered.getText());
			}catch(Exception e) {
				this.message.setText("Enter a valid integer for time elapsed.");
				this.message.setTextFill(Color.CRIMSON);
			}
			
			
			
			if (card.getTrip().isEmpty()) {
				Trip t = new Trip(this.card, node, line, date);
				this.card.addTrip(t);
				Main.activeholder.addTrip(t);
				this.output.getChildren().clear();
				this.output.getChildren().add(new Label("Success."));
			} else {
				if (card.getTrip().get(card.getTrip().size()-1).update(node, line, seconds) == -1) {
					Trip t = new Trip(this.card, node, line, date);
					this.card.addTrip(t);
					Main.activeholder.addTrip(t);
					this.output.getChildren().clear();
					this.output.getChildren().add(new Label("Success."));
				} else {
					this.output.getChildren().clear();
					this.output.getChildren().add(new Label("Success."));
				}
			}
		}
		
		
		
		
	}
	
}

class EndPeriod implements EventHandler<ActionEvent>{
	
	private VBox output;
	private HashMap<String, CardHolder> holders;
	
	public EndPeriod(VBox output, HashMap<String, CardHolder> holders) {
		this.output = output;
		this.holders = holders;
	}

	@Override
	public void handle(ActionEvent arg0) {
		for (CardHolder h: this.holders.values()) {
			h.clearTrips();
		}
		Label terminate = new Label("Period terminated. All Trip records are reset.");
		output.getChildren().clear();
		output.getChildren().add(terminate);
	}
	
}

class DashboardA implements EventHandler<ActionEvent>{
	
	private VBox output;
	private HashMap<String, CardHolder> holders;
	
	public DashboardA(VBox output, HashMap<String, CardHolder> holders) {
		this.output = output;
		this.holders = holders;
	}

	@Override
	public void handle(ActionEvent event) {
		Label users_count = new Label();
		Label trips_count = new Label();
		Label revenue = new Label();
		
		String user = "Total ";
		user += this.holders.values().size() + " users in system.";
		users_count.setText(user);
		
		String trips = "Total ";
		int tnum = 0;
		for (CardHolder h: this.holders.values()) {
			tnum += h.getTrips().size();
		}
		trips += tnum + " trips in this period.";
		trips_count.setText(trips);
		
		String rev = "Total ";
		double money = 0.0;
		for (CardHolder h: this.holders.values()) {
			money += h.transit_cost();
		}
		rev += money +" revenue earned in this period.";
		revenue.setText(rev);
		
		output.getChildren().clear();
		output.getChildren().addAll(users_count, trips_count, revenue);
		
	}
}