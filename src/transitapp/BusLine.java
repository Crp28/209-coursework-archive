package transitapp;

/**
 * A bus line that contains bus stops and transfer stations.
 */
public class BusLine extends Line{
	
	private static final double BUS_COST = 2.00;
	
	/**
	 * Takes user input of names of bus stops, separated by comma, orderly construct into a BusLine.
	 */
	public BusLine(String name, String s) {
		super(name, s);
		this.type = "Bus";
	}
	
	/**
	 * Get the cost of taking this bus.
	 * @param traveled stops passed in this bus ride
	 * @return int representing cost before considering cap
	 */
	@Override
	public double getCost(int traveled) {
		return BUS_COST;
	}
	
	@Override
	public String toString() {
		String s = "Bus Line " + this.getName() + ": \n";
		for (Node n : this.getNodes()) {
			if (n != this.getNodes().get(this.getNodes().size()-1))
				s += n.getName() + ", ";
			else {
				s += n.getName();
			}
		}
		return s;
	}

}
