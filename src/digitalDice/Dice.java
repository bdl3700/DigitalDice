package digitalDice;

import java.util.Random;

/**
 * This Class provides the framework for Dice objects. Each dice has a number of
 * sides. This class is effective implementation for any dice that when rolled
 * returns a value between 1 and its number of sides.
 * 
 * @author Bryce L
 *
 */
public class Dice {

	public final int SIDES;
	Random rand = new Random();

	/**
	 * Creates a new Dice Object. Standard dice return values from 1 - the number of
	 * sides they have.
	 * 
	 * @param sides : the number of sides the dice has.
	 */
	public Dice(int sides) {
		SIDES = sides;
	}

	public int roll() {
		int rolledNumber = rand.nextInt(SIDES) + 1;
		return rolledNumber;
	}

	public static void main(String[] args) {
		// tests if a six sided die provides acceptable values (1-6) on 1000 rolls and
		// stops rolling if anything is out of line

		Dice d = new Dice(6);
		for (int i = 0; i < 1000; i++) {
			int roll = d.roll();
//			System.out.println(roll);  // would print each rolled value.
			if (roll > d.SIDES || roll < 1) {
				System.out.println(false);
				break;
			}
			if (i == 999) // prints true if all 1000 test rolls succeed
				System.out.println(true);
		}

	}

}
