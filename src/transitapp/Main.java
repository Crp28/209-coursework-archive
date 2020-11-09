// These is a placeholder package and placeholder class
// Feel free to rename or remove these when you add in your own code (just make sure to add/commit/push any changes made,
//		and let your teammates know to pull the changes. Follow the workflow in the a2 instructions)

public class Main{
	public static void main(String[] args) {
		BusLine bl = new BusLine("b1", "a,b,c,d");
		System.out.println(bl);
		SubwayLine sl = new SubwayLine("sl", "r,i,g,q");
		System.out.println(sl);
		System.out.println(sl.getNodes().get(1));
		sl.hasTransferto(bl, "c", "i");
		System.out.println(bl.getNodes());
		System.out.println(sl.getNodes());
		System.out.println(sl.getNodes().get(1));
		System.out.println(bl.getNodes().get(0));
	}
}
