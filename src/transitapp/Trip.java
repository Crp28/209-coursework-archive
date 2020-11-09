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
	 * Update the this trip. If the inputed Node is a destination of a segment, update the trip's arrive and timeSeconds attribute as well as record the destination of this segment in route.
	 * If the inputed Node is a starting location of a segment, check if the last destination is a transfer node of this input as well as time elapsed;
	 * if it satisfies all requirements, update route.
	 * @return Returns the updated Trip, or -1 if requirements are not met (i.e. not transfer node or over time limit).
	 */
	public Object update(Node node, Line line, int time) {
		
	}
	
	public String toString() {
		String s = "The Cardholder of card " + this.card.getId() + "made a trip on " + this.date + "for " + ((double) this.timeSeconds)/60 + "minutes with route:\n";
		for (Object[] array: route) {
			s += ((ArrayList<Node>)array[1]).get(0) + "--" + ((ArrayList<Node>)array[1]).get(1) + "by ";
			s += ((Line) array[0]).getName();
			s += "\n";
		}
	}
}