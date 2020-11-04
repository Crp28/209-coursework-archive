import java.util.ArrayList;

/**
 * A transit line that be either a BusLine or SubwayLine.
 */
public abstract class Line {
	
	private ArrayList<Node> nodes;
	
	/**
	 * Takes user input of names of Nodes, separated by comma, orderly construct into a Line.
	 */
	public Line(String s) {
		String[] names = s.split(",");
		for (String name: names) {
			nodes.add(new Node(name));
		}
	}
	
	/**
	 * Gets the number of Nodes a person needs to travel from [start] to [finish]. 
	 * @param start Node representing the starting point
	 * @param finish Node representing the ending point
	 * @return int showing number of nodes passed, or -1 if the inputed node not in this line.
	 */
	public int getNumTraveled(Node start, Node finish) {
		if (nodes.contains(start) && nodes.contains(finish))
			return Math.abs(nodes.indexOf(finish)-nodes.indexOf(start));
		return -1;
	}
	
	public abstract double getCost(int num);
	
	/**
	 * Get the Nodes in this Line.
	 * @return ArrayList of Nodes.
	 */
	public ArrayList<Node> getNodes(){
		return this.nodes;
	}
	
	/**
	 * Set two nodes to transfer nodes for each other.
	 * Precondition: [transferto] must be a node name that exists in [line], [transfer] must be a node name that exists in this line.
	 * @param line target line the other node is on
	 * @param transferto the node to transfer to on other line
	 * @param transfer the node on this line
	 */
	public void hasTransferto(Line line, String transferto, String transfer) {
		int indexline = 0;
		int indexthis = 0;
		for (int i = 0; i < line.getNodes().size(); i++) {
			if (line.getNodes().get(i).getName() == transferto) {
				indexline = i;
				break;
			}
		}
		for (int i = 0; i < this.getNodes().size(); i++) {
			if (this.getNodes().get(i).getName() == transfer) {
				indexthis = i;
				break;
			}
		}
		line.getNodes().get(indexline).addTransfer(this.getNodes().get(indexthis));
		this.getNodes().get(indexthis).addTransfer(line.getNodes().get(indexline));
	}
}