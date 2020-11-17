

import java.util.ArrayList;

/**
 * A class represents a user of the travel system who owns at least one travel card.
 * It consists of information about the user (e.g. name and email) and some methods 
 * applied to update the information or change cards' status for the user.
 * It has access to public class Card.
 */
public class CardHolder {

	private String name;  
	private String email;
	private ArrayList<Card> cards = new ArrayList<Card>();
	private ArrayList<Trip> trips = new ArrayList<Trip>();
	
	/** 
	 * Record data about the user depending on the given information.
	 */
	public CardHolder(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	//getter
	public String getName() {
		return this.name;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public ArrayList<Card> getCards() {
		return this.cards;
	}
	
	public ArrayList<Trip> getTrips() {
		return this.trips;
	}
	
	/** 
	 * Change name for the CardHolder. Return the new name.
	 * 
	 * @param new_name   the new name of the CardHolder
	 * @Return name of the CardHolder after updating it
	 */
	public String updateName(String new_name) {
		this.name = new_name;
		return this.name;
	}
	
	
	/** 
	 * Add a card that belongs to the CardHolder.
	 * 
	 * @param card  Card that will be added
	 */
	public void addCard(Card card) {
		this.cards.add(card);
	}
	
	/**
	 * remove the specific card at the inputed index in this cardholder's card list
	 * @param index index of the card in this cardholder's card list
	 */
	public void removeCard(int index) {
		this.cards.remove(index);
	}
	
	
	

	
	/** 
	 * Remove all trips in trip history and clear the cards' trip history.
	 */
	public void clearTrips() {
		
		for (Card card : this.cards) {
			card.clearTrip();
		}
		this.trips.clear();
	}
	
	/** 
	 * Add a trip into the trip history of the CardHolder
	 * 
	 * @param trip   the added Trip
	 */
	public void addTrip(Trip trip) {
		
		this.trips.add(trip);
		
	}
	
	/** 
	 * Track the transit cost for the CardHolder depending on the trip history.
	 * Return the cost
	 * 
	 * @return double   the total transit cost of CardHolder's Trips
	 */
	public double transit_cost() {
		
		double cost = 0.0;
		for (Trip trip : this.trips) {
			cost += trip.totalCost();
		}
		return cost;
	}
	
	
	// if a card is stolen, the user may want to suspend the card
	/** 
	 * Suspend a Card for the user          
	 * 
	 * @param card the Card needs to be suspended   
	 */
	public void suspendCard(Card card) {
		card.suspend();
	}
	
	/** 
	 * Reactivate a Card for the user          
	 * 
	 * @param card the Card needs to be activated   
	 */
	public void activateCard(Card card) {
		card.activate();
	}
	
	/** 
	 * Check the most recent three trips for the CardHolder. Return these trips
	 * in a form of ArrayList<Trip>.
	 * 
	 * @return ArrayList<Trip>   the list with three trips
	 */
	public ArrayList<Trip> recent_trips() {
		
		ArrayList<Trip> sub = new ArrayList<Trip>();
		int lst = this.trips.size() - 1;
		for (int i = lst ; i >= lst - 2 ; i = i - 1) {
			sub.add(this.trips.get(i));
		}
		return sub;
	}
	
	
	public String toString() {
		String s = "name: " + this.name +
				 "; e-mail: " + this.email +
				 "." ;
				 
		return s;
	}
}