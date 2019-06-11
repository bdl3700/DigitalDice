package src;

/**
 * This class provides the implementation of two varations of D10; The first
 * returns values between 0-9, the second gives multiples of 10 between 0-90
 * (inclusive).
 * 
 * @author Bryce L
 *
 */
public class D10 extends Dice {

	public final boolean PERCENTDICE;

	/**
	 * Creates a new D10
	 * 
	 * @param percentDice : whether or not the dice returns values as multiples of
	 *        ten
	 */
	public D10(boolean percentDice) {
		super(10);
		PERCENTDICE = percentDice;
	}

	public int roll() {
		int rollPlusOne = super.roll();
		int roll = rollPlusOne - 1;

		if (PERCENTDICE)
			return roll * 10;

		return roll;
	}
	
	/**
	 * Returns a string describing the type of D10
	 */
	@Override
	public String type() {
		if (PERCENTDICE) {
			return "D10p";
		}
		return "D10";
	}

	public static void main(String[] args) {

		// tests if a standard D10 returns acceptable values (0-9).
		D10 d10 = new D10(false);
		for (int i = 0; i < 1000; i++) {
			int rollD10 = d10.roll();
//			System.out.println(rollD10); // would print each roll of the die
			if (rollD10 > 9 || rollD10 < 0) {
				System.out.println(false);
				break;
			}
			if (i == 999) // prints true if 1000 test rolls succeed;
				System.out.println(true);
		}

		// tests if a percentile D10 returns acceptable values (multiples of ten between
		// 0-90 Inclusive)
		D10 d90 = new D10(true);
		for (int i = 0; i < 1000; i++) {
			int rollD90 = d90.roll();
//			System.out.println(rollD90); //would print each roll of the die
			if (rollD90 > 90 || rollD90 < 0 || (rollD90 % 10 != 0)) {
				System.out.println(false);
				break;
			}
			if (i == 999) // prints true if 1000 test rolls succeed;
				System.out.println(true);
		}
	}

}
