package calculator.value_handling;


/**
 * <code>ValueSorter</code> class is a helper class for processing,
 * analyzing and converting values of types <code>int</code>, <code>double</code>,
 * <code>String</code> and <code>Fraction</code> for ease of use in other classes.
 * Some methods print to the console for testing purposes.
 * 
 * @author sarablinn
 *
 */
public class ValueSorter {


	/**
	 * Converts a <code>String</code> to a <code>Fraction</code>. Assumes that
	 * the <code>String</code> is already properly formatted to become a 
	 * <code>Fraction</code>. Returns null if it failed.  
	 * @param input <code>String</code>
	 * @return <code>Fraction</code> <code>null</code> if creation failed
	 * @catch <code>NumberFormatExeption</code>
	 */
	public Fraction getFraction(String input) {
		try {
			if (input.contains("/")) {
				int numer = Integer.parseInt(input.substring(0, input.indexOf("/")));
				int denom = Integer.parseInt(input.substring((input.indexOf("/")+1), input.length()));
				Fraction frac = new Fraction(numer, denom);
				return frac;
			}
			Fraction frac = new Fraction(Integer.parseInt(input));
			return frac;
		}
		catch (NumberFormatException e) {
			System.out.println("Value not valid to become a Fraction");
		}
		return null;
	}

	/**
	 * Checks if a <code>String</code> is propertly formatted to become 
	 * a <code>Fraction</code>.
	 * @param str 
	 * @return <code>boolean</code>
	 */
	public boolean isFractionable(String str) {
		String posStr = str;
		if (isInteger(posStr)) {
			return true;
		}
		// check if the fraction has a negative sign and if it is 
		// in the correct location.
		if(str.contains("-")) { 
			if ( str.indexOf("-") != 0 ) {
				return false;
			}
			else {
				posStr = str.substring(1);
			}
		}
		// split the string at the bar to check that the numer & denom are valid
		if (posStr.contains("/")) { 
			String subStr1 = posStr.substring(0, posStr.indexOf("/"));
			String subStr2 = posStr.substring((posStr.indexOf("/") +1), 
					posStr.length());
			if(isInteger(subStr1) && isInteger(subStr2)) {
				if(subStr2.equals("0")) {
					return false;
				}
				return true;
			}
		}
		return false; 
	}
	
	/**
	 * Checks if the provided <code>String</code> is a fraction already in
	 * the proper format.
	 * @param str
	 * @return <code>boolean</code>
	 */
	public boolean isFraction(String str) {
		if(isFractionable(str) && str.contains("/")) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a <code>String</code> is a rational number, meaning it is
	 * a integer, decimal, or fraction. Uses <code>isNumber</code> and 
	 * <code>isValidFraction</code> methods.
	 * @param str
	 * @return <code>boolean</code>
	 */
	public boolean isValidValue(String str) {
		if (isNumber(str) || isFractionable(str)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if a <code>String</code> is a number, in this case, that it is
	 * either an integer or a double and not a fraction. Uses 
	 * <code>isInteger</code> and <code>isDouble</code> methods.
	 * @param str
	 * @return <code>boolean</code>
	 */
	public boolean isNumber(String str) {
		if(isInteger(str) || isDouble(str)) {
			return true;
		}
		return false;
	}
	
	/**
	 * Checks if a <code>String</code> is an <code>int</code>.
	 * @param str
	 * @return <code>boolean</code>
	 */
	public boolean isInteger(String str) {
		if (str == null) {
			return false;
		}
		try {
			@SuppressWarnings("unused")
			int d = Integer.parseInt(str);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
	
	/**
	 * Checks if a <code>String</code> is a <code>double</code>. If the 
	 * <code>String</code> is <code>null</code>, automatically returns 
	 * <code>false</code>. Catches <code>NumberFormatException</code> and returns 
	 * <code>false</code>
	 * @param str
	 * @return <code>boolean</code>
	 */
	public boolean isDouble(String str) {
		try {
			@SuppressWarnings("unused")
			double d = Double.parseDouble(str);
			if(str != null && str.contains(".")) {
				return true;
			}
		} catch (NumberFormatException nfe) {
			return false;
		}
		return false;
	}
	
	/**
	 * Converts a <code>double</code> to a <code>Fraction</code> and
	 * returns it using <code>doubleToFraction(String)</code> method.
	 * @param d 
	 * @return <code>Fraction</code>
	 */
	public Fraction doubleToFraction(double d) {
		String str = Double.toString(d);
		return doubleToFraction(str);
	}
	
	/**
	 * Converts a <code>String</code> representation of a <code>double</code> 
	 * to a <code>Fraction</code> and returns it.
	 * @param str
	 * @return <code>Fraction</code>
	 */
	public Fraction doubleToFraction(String str) {
		double d = Double.parseDouble(str);
		String subStr = str.substring((str.indexOf(".") + 1));
		int length = subStr.length();
		int denom = (int) Math.pow(10, length);
		int numer = (int) (d * denom);
		Fraction fraction = new Fraction(numer,denom);
		fraction.toLowestTerms();
		return fraction;
	}
	
	/**
	 * Converts a <code>String</code> representation of a <code>Fraction</code>
	 * to a <code>double</code> and returns it.
	 * @param str of a <code>Fraction</code>
	 * @return <code>double</code>
	 */
	public double fractionStrToDouble(String str) {
		Fraction fraction = getFraction(str);
		double d = (fraction.toDouble());
		return d;
	}
	
	public int numFractions(String str1, String str2) {
		if(isFractionable(str1) && isFractionable(str2)) {
			return 2;
		}
		if(isFractionable(str1) || isFractionable(str2)) {
			return 1;
		}
		return 0;
	}
	
}


