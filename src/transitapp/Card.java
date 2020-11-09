package transitapp;

import java.util.ArrayList;

/**
 * A travel card owned by a CardHolder. It knows the current balance in the card
 * and keeps track of the status of the card. It also records the trip history
 * of the user when using this card.
 */
public class Card {
	
	public static final boolean NORMAL = true, SUSPENDED = false;
	private static int count = 0;
	public int id;
	public ArrayList<Trip> trip_history;
	public CardHolder owner;
	private boolean status = NORMAL;
	private double balance = 19.0;    // All new cards start with a $19 balance
	
	
	/** Each new card will have an id that equals id of the last produced card +1.
	 * 
	 */
	Card(CardHolder owner) {
		id=++count;
		this.owner = owner;
	}
	
	/**
	 * Counts how many cards have been produced so far.
	 * 
	 * @return the number of cards produced
	 */
	public static int getCount(){
		return count;
	}
	
	/**
	 * Returns the balance in this card.
	 * 
	 * @return a double represents the current balance in the card
	 */
	public double getBalance() {
		return this.balance;
	}
	
	/**
	 * Returns who owns the card.
	 * 
	 * @return CardHolder for the card
	 */
	public CardHolder getHolder() {
		return this.owner;
	}
	
	/**
	 * Returns the trip history of the card.
	 * 
	 * @return ArrayList<Trip> that records trip history for the card
	 */
	public ArrayList<Trip> getTrip() {
		return this.trip_history;
	}
	
	
	/**
	 * Returns id of the card.
	 * 
	 * @return id of the card
	 */
	public double getId() {
		return this.id;
	}
	
	/**
	 * Add balance to the card, then update its status according to the condition. 
	 * Returns the balance after charging
	 * 
	 * @param amount double represents the amount adding into the card
	 * @return the balance in the card after adding balance
	 */
	public double addBalance(double amount) {
		this.balance += amount;
		this.updateStatus();
		
		return this.getBalance();	
	}
	
	/**
	 * Update the status for the card. If the balance moves from positive to
	 * negative, suspend the card; if the balance moves from negative to positive,
	 * enable the card to be used. Do nothing if it does not meet any above cases.
	 * Return the card's status after the update.
	 * 
	 * @return the status of the card after updating it
	 */
	public boolean updateStatus() {
		if (this.balance < 0.0 && this.status == NORMAL) {
			this.status = SUSPENDED;
		} else if (this.balance >= 0.0 && this.status == SUSPENDED) {
			this.status = NORMAL;
		}
		
		return this.status;
	}
	
	/**
	 * Record a Trip.
	 */
	public void addTrip(Trip trip) {
		this.trip_history.add(trip);
	}
	
	
	/**
	 * Return string representation of this card
	 *
	 * @return s   The string representation of this card
	 */
	public String toString() {
		String s = "The card " + this.id +
				 " owned by " + this.owner +
				 " has balance of " + this.balance +
				 " dollars.";
				 
		return s;
	}
}















