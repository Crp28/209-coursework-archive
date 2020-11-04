import java.util.ArrayList;

/**
 * A bus line that contains bus stops and transfer stations.
 */
public class BusLine extends Line{
	
	
	private static final double BUS_COST = 2.00;
	
	/**
	 * Takes user input of names of bus stops, separated by comma, orderly construct into a BusLine.
	 */
	public BusLine(String s) {
		super(s);
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

}