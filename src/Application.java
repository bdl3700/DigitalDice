package src;

import javax.swing.text.JTextComponent;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.File;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Container;
import java.awt.Dimension;


/**
 * This class provides the structure of an application that handles digital dice
 * of various sizes, planned dice include a D4, D6, D8, D10, D12, D20, and a D10
 * percentile dice.
 * 
 * TODO: create a nice visual interface to allow for easy use.
 * 
 * TODO: different types of rolls: quick D20, multiple die summed, etc
 * 
 * @author Bryce L
 *
 */
public class Application extends JPanel implements ActionListener, KeyListener{
	
	private static final long serialVersionUID = 1L;
	
	// Sets up the standard format dice that return 1-# of sides.
	private static Dice d4 = new Dice(4);
	private static Dice d6 = new Dice(6);
	private static Dice d8 = new Dice(8);
	private static Dice d12 = new Dice(12);
	private static Dice d20 = new Dice(20);
	
	// Sets up the 2 D10 dice.
	private static D10 d10 = new D10(false); //normal D10
	private static D10 d10p = new D10(true); //percentile D10
	
	// Sets up buttons for each type of dice.
	private static JButton d4Button;
	private static JButton d6Button;
	private static JButton d8Button;
	private static JButton d10Button;
	private static JButton d10pButton;
	private static JButton d12Button;
	private static JButton d20Button;
	
	private int lastRollTotal;
	private static JLabel lastRollLabel = new JLabel("Last roll: ");
	private static JLabel activeDiceLabel = new JLabel("Active Dice: ");
	
	//sets up other buttons
	private static JButton roll;
	private static JButton quickD20;
	private static JButton clearDice;
	
	private static ArrayList<Dice> dice; //currently active dice go in here
	
	/**
	 * sets up the basic objects for each set of dice.
	 */
	public Application() {
		super();
		
		dice = new ArrayList<>();
		
	}
	
	/**
	 * Does a quick roll of a D20 and then clears the list of active dice.
	 */
	private void quickD20() {
		lastRollTotal = d20.roll();
		lastRollLabel.setText("Last roll: " + lastRollTotal);
	}
	
	/**
	 * Rolls each active dice and totals them. Then clears the list of dice.
	 */
	private void roll() {
		int rollTotal = 0;
		for (Dice d : dice) {
			rollTotal += d.roll();
		}
		lastRollTotal = rollTotal;
		lastRollLabel.setText("Last roll: " + lastRollTotal);
		clearDice();
	}
	
	/**
	 * removes all dice from the ArrayList dice.
	 */
	private void clearDice() {
		for (Dice d : dice) {
			dice.remove(d);
		}
		activeDiceLabel.setText("");
	}
	
	/**
	 * Updates the label that lists active Dice.
	 */
	private static void updateActiveDiceLabel() {
		String label = "";
		
		for (Dice d : dice) {
			label += d.type() + ", ";
		}
		
		activeDiceLabel.setText("Active Dice: " + label);
	}

	
	/**
	 * Create a GridBag Layout for the main panel and add JButtons for each die
	 * type, a printout for the current list of dice, and another for the last roll.
	 * 
	 * @param pane
	 */
	public static void addComponentsToPane(Container pane) {

		pane.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();

		d4Button = new JButton("D4");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		pane.add(d4Button, c);
		d4Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D4 Button is pressed.
				
				dice.add(d4);
				updateActiveDiceLabel();
				
				System.out.println("D4 Clicked");
			}
		});

//		redButton = new JButton("Red");
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 0.0;
//		c.gridx = 1;
//		c.gridy = 0;
//		pane.add(redButton, c);
//		redButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// everything that happens when the Red Button is Pressed
//				typeSelected = "Red";
//
//				if (placingDefenders)
//					placingDefenders = false;
//				else
//					placingDefenders = true;
//				System.out.println("RedButton Clicked");
//			}
//		});
//
//		greenButton = new JButton("Green");
//		c.fill = GridBagConstraints.HORIZONTAL;
//		c.weightx = 0.0;
//		c.gridx = 2;
//		c.gridy = 0;
//		pane.add(greenButton, c);
//		greenButton.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// everything that happens when the Green Button is Pressed
//				typeSelected = "Green";
//
//				if (placingDefenders)
//					placingDefenders = false;
//				else
//					placingDefenders = true;
//				System.out.println("GreenButton Clicked");
//			}
//		});
//
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(activeDiceLabel, c);

		Application panel = new Application(); // Creates the app instance

		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 1.0; // request any extra vertical space
		c.anchor = GridBagConstraints.PAGE_END; // bottom of space
		c.insets = new Insets(0, 0, 0, 0); // top padding
		c.gridx = 0; // aligned with the left of the screen
		c.gridwidth = 4; // 4 columns wide
		c.gridy = 1; // second row
		pane.add(panel, c);

	}
	
	public static void main(String[] args) {
		// Schedule a job for the event-dispatching thread:
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						JFrame app = new JFrame("RPG Dice");
						app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

						addComponentsToPane(app.getContentPane());

						app.pack();
						app.setVisible(true);
					}
				});

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
