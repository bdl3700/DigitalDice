package digitalDice;

/**
 * This class provides the structure of an application that handles digital dice
 * of various sizes, planned dice include a D4, D6, D8, D10, D12, and a D10
 * percentile dice.
 * 
 * TODO: create a framework that allows for rolling multiple dice at once and
 * will sum the rolled values or return individual values depending on need.
 * 
 * TODO: create a nice visual interface to allow for easy use.
 * 
 * TODO: different types of rolls: quick D20, multiple die summed, etc
 * 
 * @author Bryce L
 *
 */
public class Application {
	
	// Sets up the standard format dice that return 1-# of sides.
	private Dice d4 = new Dice(4);
	private Dice d6 = new Dice(6);
	private Dice d8 = new Dice(8);
	private Dice d12 = new Dice(12);
	
	// Sets up the 2 D10 dice.
	private D10 d10 = new D10(false); //normal D10
	private D10 d10p = new D10(true); //percentile D10
	
	private int quickD20() {
		return 0;
	}

	public static void main(String[] args) {
		

	}

}
