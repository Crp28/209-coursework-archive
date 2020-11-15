package transitapp;

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
	
	/** 
	 * Change email address for the CardHolder. Return the new email address.
	 * 
	 * @param new_email   the new email of the CardHolder
	 * @Return email address of the CardHolder after updating it
	 */
	public String updateEmail(String new_email) {
		this.email = new_email;
		return this.email;
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
	 * Add the whole trip history of the given card into CardHolder's trip history.
	 * If the card is not owned by the CardHolder, do nothing.
	 * 
	 * @param card  Card with trip history
	 */
	public void addCardTrip(Card card) {
		
		if (this.cards.contains(card)) {
			this.trips.addAll(card.getTrip());
		}
	}
	
	/** 
	 * Add all trips on all cards owned by CardHolder.
	 */
	public void addAllTrip() {
		
		for (Card card : this.cards) {
			this.addCardTrip(card);
		}
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
	 * @param card   the Card needs to be suspended   
	 */
	public void suspendCard(Card card) {
		card.suspend();
	}
	
	
	
	/** 
	 * Check the most recent three trips for the CardHolder. Return these trips
	 * in a form of ArrayList<Trip>.
	 * 
	 * @return ArrayList<Trip>   the list with three trips
	 */
	public ArrayList<Trip> recent_trips() {
		
		ArrayList<Trip> sub;
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
