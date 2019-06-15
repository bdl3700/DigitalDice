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
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Container;
import java.awt.Dimension;


/**
 * This class provides the structure of an application that handles digital dice
 * of various sizes, dice include a D4, D6, D8, D10, D12, D20, and a D10
 * percentile dice. Basic functionality is such that one can create a list of
 * dice to roll and then recieve the sum of the numbers provided. The user has
 * the options of a list of dice of their selection, or a quick D20 roll.
 * 
 * TODO: Add a keyListener such that one can operate the application through use
 * of the keyboard without a mouse.
 * 
 * @author Bryce L
 *
 */
public class Application extends JPanel implements ActionListener{
	
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
	
	private static int lastRollTotal;
	private static JLabel lastRollLabel = new JLabel("Last roll: ");
	private static JLabel activeDiceLabel = new JLabel("Active Dice: ");
	
	//sets up other buttons
	private static JButton quickD20;
	private static JButton roll;
	private static JButton clearDice;
	
	private static ArrayList<Dice> dice; //currently active dice go in here
	
	private long lastKeyPressTime = 0;
	private long minKeyPressInterval = 200;
	
	/**
	 * sets up the basic objects for each set of dice including a keyEventDispatcher.
	 */
	public Application() {
		super();
		
		dice = new ArrayList<>();

		//adds a new KeyEventDispatcher to handle all key presses before they get to the other events.
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

			@Override
			public boolean dispatchKeyEvent(KeyEvent e) {
				// TODO Add each Key
				
				if (System.currentTimeMillis() - lastKeyPressTime > minKeyPressInterval) {
					
					//when enter is pressed
					if (e.getKeyChar() == KeyEvent.VK_ENTER) {
						roll();
						
						System.out.println("Dice Rolled");
					}
					
					//when the 4 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_4) {
						dice.add(d4);
						updateActiveDiceLabel();
						
						System.out.println("D4 Added");
					}
					
					//when the 6 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_6) {
						dice.add(d6);
						updateActiveDiceLabel();
						
						System.out.println("D6 Added");
					
					}
					
					//when the 8 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_8) {
						dice.add(d8);
						updateActiveDiceLabel();
						
						System.out.println("D8 Added");
					}
					
					//when the 9 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_9) {
						dice.add(d10);
						updateActiveDiceLabel();
						
						System.out.println("D10 Added");
					}
					
					//when the 0 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_0) {
						dice.add(d10p);
						updateActiveDiceLabel();
						
						System.out.println("D10p Added");
					}
					
					//when the 1 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_1) {
						dice.add(d12);
						updateActiveDiceLabel();
						
						System.out.println("D12 Added");
					}
					
					//when the 2 key is pressed
					if (e.getKeyChar() == KeyEvent.VK_2) {
						dice.add(d20);
						updateActiveDiceLabel();
						
						System.out.println("D20 Added");
					}
					
					//when the delete or backspace buttons are pressed
					if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE || e.getKeyChar() == KeyEvent.VK_DELETE) {
						clearDice();
						
						System.out.println("Active Dice Cleared");
					}
					
					//when the period key or backslash buttons are pressed
					if (e.getKeyChar() == KeyEvent.VK_PERIOD || e.getKeyChar() == KeyEvent.VK_BACK_SLASH) {
						quickD20();
						
						System.out.println("quickD20 Rolled");
					}
					
					//if an action is performed then the keystroke timer restarts.
					lastKeyPressTime = System.currentTimeMillis();
				}
				
				//don't keep trying to handle key strokes
				return true;
			}
			
		});
	}
	
	/**
	 * Does a quick roll of a D20 leaving the active dice as they are.
	 */
	private static void quickD20() {
		lastRollTotal = d20.roll();
		lastRollLabel.setText("Last roll: " + lastRollTotal);
	}
	
	/**
	 * Rolls each active dice and totals them.
	 */
	private static void roll() {
		int rollTotal = 0;
		for (Dice d : dice) {
			rollTotal += d.roll();
		}
		lastRollTotal = rollTotal;
		lastRollLabel.setText("Last roll: " + lastRollTotal);
	}
	
	/**
	 * removes all dice from the ArrayList dice.
	 */
	private static void clearDice() {
		dice = new ArrayList<>();
		
		activeDiceLabel.setText("Active Dice: ");
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
		
		d6Button = new JButton("D6");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 0;
		pane.add(d6Button, c);
		d6Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D6 Button is pressed.
				
				dice.add(d6);
				updateActiveDiceLabel();
				
				System.out.println("D6 Clicked");
			}
		});
		
		d8Button = new JButton("D8");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 2;
		c.gridy = 0;
		pane.add(d8Button, c);
		d8Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D8 Button is pressed.
				
				dice.add(d8);
				updateActiveDiceLabel();
				
				System.out.println("D8 Clicked");
			}
		});
		
		d10Button = new JButton("D10");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 3;
		c.gridy = 0;
		pane.add(d10Button, c);
		d10Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D10 Button is pressed.
				
				dice.add(d10);
				updateActiveDiceLabel();
				
				System.out.println("D10 Clicked");
			}
		});
		
		d10pButton = new JButton("D10p");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 4;
		c.gridy = 0;
		pane.add(d10pButton, c);
		d10pButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D10p Button is pressed.
				
				dice.add(d10p);
				updateActiveDiceLabel();
				
				System.out.println("D10p Clicked");
			}
		});
		
		d12Button = new JButton("D12");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 5;
		c.gridy = 0;
		pane.add(d12Button, c);
		d12Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D12 Button is pressed.
				
				dice.add(d12);
				updateActiveDiceLabel();
				
				System.out.println("D12 Clicked");
			}
		});
		
		d20Button = new JButton("D20");
		c.weightx = 0.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 0;
		pane.add(d20Button, c);
		d20Button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the D20 Button is pressed.
				
				dice.add(d20);
				updateActiveDiceLabel();
				
				System.out.println("D20 Clicked");
			}
		});

		//adds the activeDiceLabel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 5;
		c.weightx = 0;
		c.gridx = 0;
		c.gridy = 1;
		pane.add(activeDiceLabel, c);
		
		//adds the lastRollLabel
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 2;
		c.weightx = 0;
		c.gridx = 5;
		c.gridy = 1;
		pane.add(lastRollLabel, c);
		
		quickD20 = new JButton("Quick D20");
		c.weightx = 0.0;
		c.gridwidth = 1;
		c.gridx = 0;
		c.gridy = 2;
		pane.add(quickD20, c);
		quickD20.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the quickD20 Button is pressed.
				
				quickD20();
				
				System.out.println("quickD20 Clicked");
			}
		});
		
		roll = new JButton("Roll");
		c.weightx = 0.0;
		c.gridwidth = 5;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 1;
		c.gridy = 2;
		pane.add(roll, c);
		roll.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the roll Button is pressed.
				
				roll();
				
				System.out.println("Roll Clicked");
			}
		});
		
		clearDice = new JButton("Clear Dice");
		c.weightx = 0.0;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 6;
		c.gridy = 2;
		pane.add(clearDice, c);
		clearDice.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// everything that happens when the clearDice Button is pressed.
				
				clearDice();
				
				System.out.println("Clear Dice Clicked");
			}
		});

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
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
