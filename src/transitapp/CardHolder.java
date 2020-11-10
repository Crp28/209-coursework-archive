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
	
	/** 
	 * Record data about the user depending on the given information.
	 */
	public CardHolder(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	
}
