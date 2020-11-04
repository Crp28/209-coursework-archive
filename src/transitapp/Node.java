import java.util.ArrayList;
/**
 * A node in a transit line. It can be a bus stop or subway station. It may act as a transfer node.
 */
public class Node {
	
	private final String name;
	private ArrayList<Node> transfer;
	
	/**
	 * Construct a new Node using the inputed name.
	 * @param name String representing name of this node.
	 */
	public Node(String name) {
		this.name = name;
	}
	
	/**
	 * Take the inputed Node and set it to a transfer node of this Node.
	 * @param n Node representing the transfer node of this Node.
	 */
	public void addTransfer(Node n) {
		this.transfer.add(n);
	}
	
	/**
	 * Get the name of this Node.
	 * @return name of this Node.
	 */
	public String getName() {
		return this.name;
	}
	
	/**
	 * Get the ArrayList of transfer nodes of this Node.
	 * @return list of transfer nodes of this Node.
	 */
	public ArrayList<Node> getTransfer(){
		return this.transfer;
	}
	
	/**
	 * Judges if the inputed node is a transfer node of this node
	 * @return true if the node is a transfer node of this node
	 */
	public boolean isTransfer(Node node) {
		return this.transfer.contains(node);
	}
}