package calculator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JTextArea;

import calculator.value_handling.Calculator;

public class TextAreaListener implements ActionListener, Runnable{

	private static Calculator gui = new Calculator();
	private JTextArea hist = gui.getTextArea();
	
	@Override
	public void actionPerformed(ActionEvent ae) {
		Runnable runnable = new TextAreaListener();
		Thread thread = new Thread(runnable);
		thread.start();
		// when the data is added to the JTextArea, it will always scroll down.
		//hist.setCaretPosition(hist.getText().length() - 1);
	}
		

	@Override
	public void run() {
		hist.validate();
	}
	

}
