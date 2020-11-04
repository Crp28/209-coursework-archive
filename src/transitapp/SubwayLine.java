/**
 * A bus line that contains bus stops and transfer stations.
 */
public class SubwayLine extends Line{
	
	
	private static final double SUBWAY_COST_PER_STATION = 0.50;
	
	/**
	 * Takes user input of names of subway stations, separated by comma, orderly construct into a SubwayLine.
	 */
	public SubwayLine(String s) {
		super(s);
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
}