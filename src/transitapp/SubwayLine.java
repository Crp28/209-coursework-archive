/**
 * A bus line that contains bus stops and transfer stations.
 */
public class SubwayLine extends Line{
	
	
	private static final double SUBWAY_COST_PER_STATION = 0.50;
	
	/**
	 * Takes user input of names of subway stations, separated by comma, orderly construct into a SubwayLine.
	 */
	public SubwayLine(String name, String s) {
		super(name, s);
		this.type = "Subway";
	}
	
	/**
	 * Get the cost of taking this subway.
	 * @param traveled stations passed in this subway ride
	 * @return int representing cost before considering cap
	 */
	@Override
	public double getCost(int traveled) {
		return traveled * SUBWAY_COST_PER_STATION;
	}
	
	@Override
	public String toString() {
		String s = "Subway Line " + this.getName() + ": \n";
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
