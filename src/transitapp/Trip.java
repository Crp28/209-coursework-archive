import java.util.ArrayList;

public class Trip {
	
	private Card card;
	private String date;
	private Node depart;
	private Node arrive;
	private int timeSeconds;
	private boolean finished = false;
	private ArrayList<Line> route_line = new ArrayList<>();
	private ArrayList<Node[]> route_nodes = new ArrayList<>();
	
	public Trip(Card card, Node node, Line line, String date) {
		this.card = card;
		this.date = date;
		this.depart = node;
		this.arrive = null;
		this.timeSeconds = 0;
		this.route_line.add(line);
		if (line.type == "Bus") {
			this.card.pay(2.0);
		}
		Node[] n = new Node[2];
		n[0] = node;
		this.route_nodes.add(n);
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
	
	public ArrayList<Line> getLines(){
		return this.route_line;
	}
	
	public ArrayList<Node[]> getSegments(){
		return this.route_nodes;
	}
	
	/**
	 * Returns if the trip is finished.
	 * @return true iff the trip is finished, else false
	 */
	public boolean isFinished() {
		return this.finished;
	}
	
	/**
	 * Return the total cost generated by this trip right now
	 * Precondition: the Trip has only complete segments
	 * @return double representing dollars costed by this trip
	 */
	public double totalCost() {
		double tc = 0.0;
		for (int i=0; i < this.route_line.size(); i++) {
			tc += this.route_line.get(i).getCost(this.route_line.get(i).getNumTraveled(this.route_nodes.get(i)[0], this.route_nodes.get(i)[1]));
		}
		if (tc > 6.0) {
			tc = 6.0;
		}
		return tc;
	}
	
	/**
	 * Calculate the cost incurred by the last segment.
	 * @param segment an array with format [Line, ArrayList[Node1, Node2]], where the Node1, Node2 represents start, end of the last segment in this Trip
	 * @return double representing cost before reaching $6 cap. If the cap is already reached, return 0.0
	 */
	public double calcLastSegmentCost() {
		Node[] segment = this.route_nodes.get(this.route_nodes.size()-1);
		Line line = this.route_line.get(this.route_line.size()-1);
		double cost = line.getCost(line.getNumTraveled(segment[0], segment[1]));
		this.route_line.remove(this.route_line.size()-1);
		this.route_nodes.remove(this.route_nodes.size()-1);
		double tc = this.totalCost();
		this.route_line.add(line);
		this.route_nodes.add(segment);
		if (cost > 6.0-tc) {
			return 6.0-tc;
		}
		return cost;
	}
	
	/**
	 * Manually close a Trip instance.
	 * @return The finished Trip
	 */
	public Trip close() {
		this.finished = true;
		return this;
	}
	
	/**
	 * Update the this trip and make the card pay. If the inputed Node is a destination of the last segment, update the trip's arrive and timeSeconds attribute as well as record the destination of this segment in route.
	 * If the inputed Node is a starting location of a segment, check if the last destination is a transfer node of this input as well as time elapsed(strictly less than 2h);
	 * if it satisfies all requirements, update route and timeSeconds.
	 * If it satisfies neither condition, return -1.
	 * @param node The Node related to this travel segment that need to be updated into this Trip
	 * @param line The Line this travel segment is on
	 * @time time elapsed in seconds since last update
	 * @return Returns the updated Trip, or "INSUFFICIENT BALANCE" if the card related to this trip has insufficient balance, or -1 if requirements are not met (i.e. not transfer node or over time limit).
	 */
	public int update(Node node, Line line, int time) {
		Node[] lastsegment = this.route_nodes.get(this.route_nodes.size()-1);
		Line lastline = this.route_line.get(this.route_line.size()-1);
			
			if (!(lastsegment[1] == null)) {
				if (node == lastsegment[1] || lastsegment[1].getTransfer().contains(node)) {
					if (this.timeSeconds < 7200) {
						if (line.type == "Bus") {
							if (BusLine.BUS_COST > 6.0-this.totalCost()) {
								this.card.pay(6.0-this.totalCost());
							} else {
								this.card.pay(2.0);
							}
						}
						Node[] newSegment = new Node[2];
						newSegment[0] = node;
						this.route_nodes.add(newSegment);
						this.route_line.add(line);
						this.timeSeconds += time;
						return 0;
					}
				}
			}
			else if (lastline.equals(line)) {
				if (lastsegment[1] == null) {
					this.route_nodes.get(this.route_nodes.size()-1)[1] = node;  //why this would not work???
					this.arrive = node;								//why???
					this.timeSeconds += time;
					if (line.type == "Subway") {
						this.card.pay(this.calcLastSegmentCost());
						return 0;
					}
				}
			}
			else {
				this.close();
			}
			return -1;
		}
	
	public String toString() {
		String s = "";
		for (int i=0; i <this.route_line.size(); i++) {
			s += this.route_line.get(i) + ": " + this.route_nodes.get(i)[0] + ", " + this.route_nodes.get(i)[1];
		}
		return s;
	}
}