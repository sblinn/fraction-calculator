package calculator.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

import calculator.value_handling.Calculator;
import calculator.value_handling.Fraction;
import calculator.value_handling.ValueSorter;


/**
 * <code>CalculatorButtonListener</code> class implements 
 * <code>ActionListener</code> and performs arithmetic operations when 
 * calculator buttons are clicked.
 * 
 * @author sarablinn
 *
 */
public final class CalculatorButtonListener implements ActionListener {

	//private static FractionCalculatorWindow gui = new FractionCalculatorWindow();
	private Calculator gui = new Calculator();
	private ValueSorter sorter = new ValueSorter();

	// fields set to "", w/o, will prompt nullPointerException 
	// after operator is input.
	private static Fraction frac1;
	private static Fraction frac2;
	private static Fraction fracSolution;
	private static String operand1 = "";
	private static String operand2 = "";
	private static String operator = "";
	private static String solution = "";
	
	

	/**
	 * Invoked when calculator buttons are clicked.
	 * @param e <code>ActionEvent</code>
	 */
	public void actionPerformed(ActionEvent e) {
		JButton jb = (JButton) e.getSource();
		String name = jb.getName();

		switch (name) {
		case "AC": 
			if (solution == "") {
				updateHistory();
			}
			gui.clearText();
			operand1 = "";
			operand2 = "";
			operator = "";
			solution = "";
			System.out.print("\n");
			break;
		case "=":
			gui.clearText();
			gui.setTextTo(checkEquation());
			updateHistory();
			operand1 = solution;
			operand2 = "";
			operator = "";
			System.out.print(" " + name + " " + gui.getUserInputText() + "\n");
			break;
			// set operator variable, clear display, set display to show operator. 
		case "convert":
			String str = convert();
			gui.clearText();
			gui.setTextTo(str);
			gui.addHistory(operand1 + " = " + solution + "\n");
			operand1 = solution;
			break;
		case "+": case "-": case "÷": case "*": 
			gui.clearText();
			gui.setTextTo(name);
			if (operator != ""){ //if operator is pressed twice
				gui.clearText();
				gui.setTextTo(checkEquation());
				operand1 = solution;
				operand2 = "";
				operator = "";
				
			}
			operator = name; 
			if (operand1 == solution && operand1 != "" && operand2 == "") {
				System.out.print(operand1);
			}
			System.out.print(" " + name + " ");
			break;

		case "+/-":
			makeNegative();
			break;
			
		case "%":
			makePercentage();
			break;
			
		case ".": case "/":

		case "1": case "2": case "3": case "4": case "5": 
		case "6": case "7": case "8": case "9": case "0":  
			// issue: user completes prior function, inputs a number immediately after
			// no operator yet, operand1 previous set to solution.
			if (gui.getUserInputText().equals("invalid") 
					|| gui.getUserInputText().equals("invalid: no value")) {
				System.out.println(gui.getUserInputText());
				gui.clearText();
			}
			if (operand1 == solution && operand1 != "" && operator == "") {
				// program knows to reset function due to the next button pressed being 
				// a number. if the next button had been an operator, function would have 
				// continued with the previous function's solution.
				gui.clearText();
				operand1 = "";
				solution = "";
			}
			setVal(name);
			gui.setTextTo(name);
			System.out.print(name);
			break; 
		}
	}  

	/**
	 * Sets the operand field values accordingly.
	 * @param value <code>String</code>
	 */
	private void setVal(String value){
		// if the operator doesn't yet exist, append value to operand1.
		if (operator == ""){
			operand1 += value;
		}
		// if the operator exists—-which implies operand1 is done,
		// append to operand2.
		else if (operator != "" && operand2 == ""){
			gui.clearText();
			operand2 += value;
		}
		else {
			operand2 += value;
		}
	}
	
	/**
	 * Makes current value negative.
	 */
	private void makeNegative() {
		if (operator == "" && operand1.equals(gui.getUserInputText()) ) {
			// NEG TO POS
			if (operand1.contains("-")) {
				operand1 = operand1.substring(1, operand1.length());
				gui.clearText();
				gui.setTextTo(operand1);
			}
			// POS TO NEG
			else {
				operand1 = "-" + operand1;
				gui.clearText();
				gui.setTextTo(operand1);
			}
		}
		else if (operator != "") {
			// NEG TO POS
			if (operand2.contains("-")) {
				operand2 = operand2.substring(1, operand2.length());
				gui.clearText();
				gui.setTextTo(operand2);
			}
			// POS TO NEG
			else {
				operand2 = "-" + operand2;
				gui.clearText();
				gui.setTextTo(operand2);
			}
		}
	}
	
	/**
	 * Makes current value into a decimal representing percentage.
	 */
	private void makePercentage() {
		if (operator == "" && operand1.equals(gui.getUserInputText()) ) {
			// FRACTION TO DOUBLE
			if (operand1.contains("/")) {
				operand1 = Double.toString(sorter.fractionStrToDouble(operand1));
			}
			// INTEGER/DOUBLE TO PERCENTAGE DECIMAL
			double d = (Double.parseDouble(operand1)) * 0.01;
			operand1 = Double.toString(d);
			gui.clearText();
			gui.setTextTo(operand1);
		}
		else if (operator != "") {
			// FRACTION TO DOUBLE
			if (operand2.contains("/")) {
				operand2 = Double.toString(sorter.fractionStrToDouble(operand2));
			}
			// INTEGER/DOUBLE TO PERCENTAGE DECIMAL
			double d = (Double.parseDouble(operand2)) * 0.01;
			operand2 = Double.toString(d);
			gui.clearText();
			gui.setTextTo(operand2);
		}

	}
	
	/**
	 * Checks that all required field elements are sufficient to perform 
	 * solve operation. If field element requirements are met, then solve is 
	 * performed and a formatted <code>String</code> of the solution is returned. 
	 * Otherwise, an "invalid" <code>String</code> is returned to be displayed.
	 * @return <b>String</b> 
	 * @see <code>solve()</code>
	 * @see <code>formatSolution()</code>
	 */
	private String checkEquation() {
		if (operand1 != "" && operand2 != "" && operator != "") {
			solve(); 
			return solution;
		} 
		else if (operand1 != "" && operand2 == "" && operator == "") {
			// if operand1 is a valid value, return it as the solution.
			// else, return invalid.
			if(sorter.isValidValue(operand1)) {
				solution = operand1;
				return solution;
			}
			return "invalid";
		}
		else if (operand1 != "" && operator != ""){
			operand2 = operand1;
			solve();
			return solution;
		}
		// if user only inputs an operator
		else { // if (operator != "") 
			// since no valid equation was input, clear the operator
			operator = "";
			return "invalid: no value"; //do something to prevent clicking anything but AC!!!
		}
	}

	/**
	 * Performs operation according to field values, sets <code>solution</code> 
	 * <code>String</code> field and returns its value.
	 * @returns <code>String</code>
	 */
	private String solve() {
		// FRACTION + FRACTION
		if (operand1.contains("/") || operand2.contains("/") ) {
			if (sorter.isFractionable(operand1) && sorter.isFractionable(operand2)) {
				frac1 = (Fraction) sorter.getFraction(operand1);
				frac2 = (Fraction) sorter.getFraction(operand2);
					switch(operator) {
					case "+":
						fracSolution = frac1.add(frac2);
						break;
					case "-":
						fracSolution = frac1.subtract(frac2);
						break;
					case "*":
						fracSolution = frac1.multiply(frac2);
						break;
					case "/":
						fracSolution = frac1.divide(frac2);
						break;
					}
				solution = fracSolution.toString();
				return solution;
			}
		}
		// FRACTION + DOUBLE
		// if one operand is a fraction and one is a double, convert the fraction to a double,
		// then recall this solve() method to solve the equation once it contains two doubles.
		if (operand1.contains("1") && sorter.isDouble(operand2)) {
			operand1 = Double.toString(sorter.fractionStrToDouble(operand1));
			solve();
		}
		// DOUBLE + FRACTION 
		if (sorter.isDouble(operand1) && operand2.contains("/")) {
			operand2 = Double.toString(sorter.fractionStrToDouble(operand2));
			solve();
		}
		// INTEGER + INTEGER OR DOUBLE + DOUBLE OR INT + DOUBLE
		else if (sorter.isNumber(operand1) && sorter.isNumber(operand2)){
			double op1double = Double.parseDouble(operand1);
			double op2double = Double.parseDouble(operand2);
			double sol = 0;
			switch (operator){
			case "+": 
				sol = op1double + op2double; break;
			case "-": 
				sol = op1double - op2double; break;
			case "*":
				sol = op1double * op2double; break;
			case "÷":
				sol = op1double / op2double; break;
			}
			solution = formatSolution(sol);
		}
		return solution;
	}

	/**
	 * Formats the provided <code>double</code> solution variable, return 
	 * it as a <code>String</code>. Checks for integers to avoid parsing 
	 * unnecessary decimal values.
	 * @param d <code>double</code>
	 * @returns <code>String</code>
	 */
	private String formatSolution(double d){
		if (d == Math.floor(d)){
			int sol = (int) d;
			solution = new Integer(sol).toString();
			return solution;
		}
		else { 
			solution = new Double(d).toString();
			return solution;
		} 
	}
	
	/**
	 * Converts the current value displayed on the user input field
	 * from <code>double</code> to <code>Fraction</code> or 
	 * vice versa, and returns it as a <code>String</code>. 
	 * @return <code>String</code>
	 */
	private String convert() {
		String str = gui.getUserInputText();
		if(str.isEmpty() || !sorter.isValidValue(str)) {
			return "error: nothing to convert";
		}
		Fraction fraction = null;
		if(sorter.isNumber(str)) {
			if(sorter.isInteger(str)) {
				int numer = Integer.parseInt(str);
				fraction = new Fraction(numer, 1);
			}
			else if(sorter.isDouble(str)) {
				double d = Double.parseDouble(str);
				fraction = sorter.doubleToFraction(d);
			}
			solution = fraction.toString();
		}
		else if(sorter.isFractionable(str)) {
			double d = sorter.fractionStrToDouble(str);
			//solution = Double.toString(d);
			solution = formatSolution(d);
		}
		return solution;
	}
	
	/**
	 * Updates the <code>history</code> text area with previous equations.
	 */
	public void updateHistory() {
		String equation = operand1 + operator + operand2;
		if (!operand1.isEmpty() && !operand2.isEmpty()) {
			gui.addHistory(operand1 + " " + operator + " " + operand2 + " " 
					+ " = " + solution + "\n");
		}
		else if(!equation.isEmpty()) {
			gui.addHistory(equation + "\n");
		}
	}


}

