package calculator.value_handling;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import calculator.listeners.CalculatorButtonListener;
import calculator.listeners.TextAreaListener;
import net.miginfocom.swing.MigLayout;

/**
 * <code>Calculator</code> builds the calculator graphical user interface. 
 * The actual run method is called in the <code>Application</code> class.
 * 
 * @author sarablinn
 *
 */
public class Calculator {

	/**
	 * top level container for the calculator gui.
	 */
	private static JFrame c;

	/**
	 * <code>ActionListener</code> for all the calculator buttons.
	 */
	private static CalculatorButtonListener buttonListener = new CalculatorButtonListener();
	
	/**
	 * <code>ActionListener</code> for updating the <code>history</code> JTextArea.
	 */
	private static TextAreaListener textAreaListener = new TextAreaListener();

	/**
	 * <code>history</code> displays the history of input past equations.
	 */
	private static JTextArea history = new JTextArea();
	
	/**
	 * <code>userInput</code> displays the current user input.
	 */
	private static JTextField userInput = new JTextField();
	
	
	/**
	 * default <code>Calculator</code> class constructor.
	 */
	public Calculator() {
//		runCalculator();
	}
	
//----------------------------------------------------------------------------
	
	/**
	 * Returns the <code>String</code> text currently in the <code>userInput</code>
	 * text field.
	 * @return <code>String</code>
	 */
	public String getUserInputText() {
		return userInput.getText();
	}
	
	public JTextArea getTextArea() {
		return history;
	}
	
	/**
	 * Sets the user input text field to the provided <code>String</code> text.
	 * @param text
	 */
	public void setTextTo(String text) {
		userInput.setText(getUserInputText() + text);
		userInput.requestFocus();
	}
	
	/**
	 * Resets the user input text field.
	 */
	public void clearText() {
		userInput.setText("");
		userInput.requestFocus();
	}
	
	/**
	 * Appends the provided <code>String</code> text to the <code>history</code>
	 * text area.
	 * @param text <code>String</code>
	 */
	public void addHistory(String text) {
		history.append(text);
	}
	
//----------------------------------------------------------------------------

	/**
	 * Launch the Fraction Calculator application. Package access only.
	 */
	protected void runCalculator() {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				initialize();
			}
		});
	}
	
	/**
	 * Initializes all the contents of the frame and sets all the appropriate
	 * Listeners for the Calculator.
	 */
	private static void initialize() {
		c = new JFrame("Calculator");
		c.setResizable(false);
		c.setBounds(100,100, 300, 500);
		c.setLayout(new MigLayout());
		
		// USERINPUT PANEL
		JPanel userInputPanel = new JPanel();
		userInputPanel.setLayout(new MigLayout("insets 5 0 5 0", "[][]", "[]")); // T L B R
		// must input JTextArea during creation of JScrollPane
		JScrollPane scroller = new JScrollPane(history);
		scroller.setPreferredSize(new Dimension(c.getWidth(), 75));
		userInputPanel.add(scroller, "span, wrap");
		
		userInput.setPreferredSize(new Dimension(c.getWidth(), 28));
		userInputPanel.add(userInput, "span");
		userInput.requestFocus();

		JPanel keypad = new JPanel();
		keypad.setLayout(new MigLayout("insets 0 0 0 0", "[]5[]5[]5[]", "[]5[]5[]5[]"));
		
		
		// MIGLAYOUT: COLUMN ROW WIDTH HEIGHT
		String[] keys = { 
										// INDEX AT END OF ROW:
				"convert", 					// 0
				"AC", "+/-", "%", "÷",		// 4
				"7", "8", "9", "*",	 		// 8
				"4", "5", "6", "-", 		// 12
				"1", "2", "3", "+", 		// 16 
				"0", ".", "/", "=" }; 		// 20
		for (int i = 0; i < keys.length; i++) {
			JButton button = new JButton(keys[i]);
			button.addActionListener(buttonListener);
			// can change ButtonListener class to ask for btn.getText() if all 
			// the button text can accurately write equations, 
			// i.e. "+/-" won't work because it doesn't accurately 
			// display a negative value.
			button.setName(keys[i]); 
			
			switch(i) {
			case 0: 
				keypad.add(button, "cell 0 0 2 1, wrap"); break;
			case 1: // "="
				button.addActionListener(textAreaListener);
				keypad.add(button); break;
			case 4: case 8: case 12: case 16: case 20: 
				keypad.add(button, "wrap"); break;
			default: 
				keypad.add(button); break;
				
			}
		}
		
		c.getContentPane().add(userInputPanel, BorderLayout.CENTER);
		c.getContentPane().add(keypad, BorderLayout.SOUTH);
		
		try {
			c.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			c.pack();
			c.setVisible(true);
		}
		catch (Exception e) {
		}
	}

}
