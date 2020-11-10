import java.util.ArrayList;

public class Trip {
	
	private Card card;
	private String date;
	private Node depart;
	private Node arrive;
	private int timeSeconds;
	private ArrayList<Object[]> route;
	
	public Trip(Card card, Node node, Line line, String date) {
		this.card = card;
		this.date = date;
		this.depart = node;
		this.timeSeconds = 0;
		this.route.add(new Object[2]);
		this.route.get(0)[0] = line;
		this.route.get(0)[1] = new ArrayList<Node>();
		((ArrayList<Node>) this.route.get(0)[1]).add(node);
	}
	
	/**
	 * Get the departing node of this Trip.
	 * @return the departing Node 
	 */
	public Node getDepart() {
		return this.depart;
	}
	
	/**
	 * Get the arriving node of this Trip.
	 * @return the arriving Node 
	 */
	public Node getArrive() {
		return this.arrive;
	}
	
	/**
	 * Get the card making this Trip.
	 * @return the card related to this Trip 
	 */
	public Card getCard() {
		return this.card;
	}
	
	/**
	 * Get the date of this Trip.
	 * @return the String representing date
	 */
	public String getDate() {
		return this.date;
	}
	
	/**
	 * Get the time spent from the start of Trip.
	 * @return time spent in seconds
	 */
	public int getTimeElapsed() {
		return this.timeSeconds;
	}
	
	/**
	 * Get the route of this Trip.
	 * @return ArrayList representing the route with format [[Line_1, [start_1, end_1]],...]
	 */
	public ArrayList<Object[]> getRoute(){
		return this.route;
	}
	/**
	 * Return the total cost generated by this trip
	 * Precondition: the Trip has already finished
	 * @return double representing dollars costed by this trip
	 */
	public double totalCost() {
		double tc = 0.0;
		for (Object[] segment : route) {
			tc += ((Line) segment[0]).getCost(((Line) segment[0]).getNumTraveled(((ArrayList<Node>)segment[1]).get(0), ((ArrayList<Node>)segment[1]).get(1)));
		}
		if (tc > 6.0) {
			tc = 6.0;
		}
		return tc;
	}
	
	/**
	 * Update the this trip. If the inputed Node is a destination of the last segment, update the trip's arrive and timeSeconds attribute as well as record the destination of this segment in route.
	 * If the inputed Node is a starting location of a segment, check if the last destination is a transfer node of this input as well as time elapsed(strictly less than 2h);
	 * if it satisfies all requirements, update route and timeSeconds.
	 * @param node The Node related to this travel segment that need to be updated into this Trip
	 * @param line The Line this travel segment is on
	 * @time time elapsed in seconds since last update
	 * @return Returns the updated Trip, or "INSUFFICIENT BALANCE" if the card related to this trip has insufficient balance, or -1 if requirements are not met (i.e. not transfer node or over time limit).
	 */
	public Object update(Node node, Line line, int time) {
		Object[] list = this.route.get(this.route.size()-1);
			if (this.card.getBalance() <= 0) {
				return "INSUFFICIENT BALANCE";
			}
			if (((Line)list[0]).equals(line)) {
				if (((ArrayList<Node>)list[1]).size() == 1) {
					((ArrayList<Node>)list[1]).add(node);
					this.arrive = node;
					this.timeSeconds += time;
					return this;
				}
			}
			else if (((ArrayList<Node>)list[1]).size() == 2) {
				if (node == ((ArrayList<Node>)list[1]).get(1) || ((ArrayList<Node>)list[1]).get(1).getTransfer().contains(node)) {
					if (this.timeSeconds < 7200) {
						Object[] newSegment = new Object[2];
						newSegment[0] = line;
						newSegment[1] = new ArrayList<Node>();
						((ArrayList<Node>)newSegment[1]).add(node);
						this.route.add(newSegment);
						this.timeSeconds += time;
						return this;
					}
				}
			}
			return -1;
		}
	
	public String toString() {
		String s = "The Cardholder of card " + this.card.getId() + "made a trip on " + this.date + "for " + ((double) this.timeSeconds)/60 + "minutes with route:\n";
		for (Object[] array: route) {
			s += ((ArrayList<Node>)array[1]).get(0) + "--" + ((ArrayList<Node>)array[1]).get(1) + "by ";
			s += ((Line) array[0]).getName();
			s += "\n";
		}
		return s;
	}
}