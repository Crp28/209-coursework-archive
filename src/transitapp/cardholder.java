package transitapp;

import java.util.ArrayList;
import java.util.Scanner;

public class cardholder {
	
	private String name;
	private String email;
	private int id;
	private int funds;
	private static ArrayList<Integer> cards = new ArrayList<Integer>();
	private static ArrayList<String> trips = new ArrayList<String>();
	
	// constructor
	public cardholder(String name, String email, int id, int funds) {
		this.name = name;
		this.email = email;
		this.id = id;
		this.funds = funds;
	}
	
	public cardholder(String name, String email, int id) {
		this.name = name;
		this.email = email;
		this.id = id;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	public int getId() {
		return id;
	}
	

	public static void addCards(int id) {
		for(int i = 0; i < cards.size(); i++) {
			if (id != cards.get(i)) {
				cards.add(id);
			}
		}
	}
	
	public static void removeCards(int id) {
		for(int i = 0; i < cards.size(); i++) {
			if (id == cards.get(i)) {
				cards.remove(cards.get(i));
			}
		}
	}
	
	public static void getCards(ArrayList<Integer> cards) {
		ArrayList<Integer> card = new ArrayList<Integer>();
		System.out.println("List of cards: ");
		for (int i = 0; i < card.size(); i++) { 		      
	          System.out.println(card.get(i)); 		
	     }   
	}
	
	public static void getTrips(ArrayList<String> trips) {
		ArrayList<String> trip = new ArrayList<String>();
		System.out.println("List of trips: ");
		for (int i = 0; i < trip.size(); i++) { 		      
	          System.out.println(trip.get(i)); 		
	     }   
	}
	
	public static void cardStatus(ArrayList<Integer> cards) {
		System.out.println("Enter the number, 1: add a card, 2: remove a card, 3: check info ");
		Scanner sc = new Scanner(System.in);
		int num = sc.nextInt();
		switch (num) {
		case 1:
			Scanner newCardSc = new Scanner(System.in);
			System.out.println("Enter your card id: ");
			int newCard = newCardSc.nextInt();
			addCards(newCard);
			System.out.println("Card " + newCard + " added.");
			break;
		case 2:
			Scanner oldCardSc = new Scanner(System.in);
			System.out.println("Enter your card id: ");
			int oldCard = oldCardSc.nextInt();
			removeCards(oldCard);
			System.out.println("Card " + oldCard + " removed.");
			break;
		case 3:
			getCards(cards);
			System.out.println(cards);
			break;
		}
	}
	
	
	public static void getCardholderName(String holderName) {
		System.out.println("The cardholder's name is: " + holderName);
	}
		
	public static void getCardholderEmail(String holderEmail) {
		System.out.println("The cardholder's email is: " + holderEmail);
	}
	
	public static void getCardID(int cardID) {
		System.out.println("The card id is: " + cardID);
	}
	
	public String toString() {
		return "Cardholder's info: " + this.getName() + ", " + this.getId() + ", "  + this.getEmail();
	}
	
	public static void main(String[] arg) {
		Scanner sc = new Scanner(System.in);
			
		System.out.println("Enter your name: ");
		String holderName = sc.next();
		getCardholderName(holderName);
		
		System.out.println("Enter your email: ");
		String holderEmail = sc.next();
		getCardholderEmail(holderEmail);
		
		System.out.println("Enter card id: ");
		int cardID  = sc.nextInt();
		getCardID(cardID);
		
		System.out.println("Check your status: ");
		cardStatus(cards);
		
		cardholder ch = new cardholder(holderName, holderEmail, cardID);
		System.out.println(ch);
		
	} 
}
