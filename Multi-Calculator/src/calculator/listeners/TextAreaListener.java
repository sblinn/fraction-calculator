package calculator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

import calculator.value_handling.Calculator;

/**
 * <code>TextAreaListener</code> implements ActionListener and Runnable to
 * continuously update the operation history to the <code>JTextArea</code> 
 * of the calculator.
 * @author sarablinn
 *
 */
public class TextAreaListener implements ActionListener, Runnable{

	private static Calculator gui = new Calculator();
	private JTextArea hist = gui.getTextArea();
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Runnable runnable = new TextAreaListener();
		Thread thread = new Thread(runnable);
		thread.start();
	}
		

	@Override
	public void run() {
		hist.validate();
	}
	

}
