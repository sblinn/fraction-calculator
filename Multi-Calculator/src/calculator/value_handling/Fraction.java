package calculator.value_handling;


/**
 * Class <code>Fraction</code> represents fraction objects with numerator
 * and denominator values and has methods for performing arithmetic on them.
 * <code>Fraction</code> objects are implemented as instances of this class.
 * <code>Fraction</code> instance's numerator and denominator values cannot be 
 * changed directly once instantiated, only by using the supplied arithmetic 
 * methods. 
 * 
 * @author sarablinn
 * 
 *
 */
public class Fraction {

	private int numerator;
	private int denominator;
	
	/**
	 * Creates a <code>Fraction</code> object using two integers for the 
	 * numerator and denominator.
	 * @param numer
	 * @param denom
	 */
	public Fraction (int numer, int denom) {
		if (denom == 0) {
			String message = "Denominator value cannot be 0.";
			throw new IllegalArgumentException(message);
		}
		else if (denom < 0) {
			this.numerator = -(numer);
			this.denominator = -(denom);
		}
		else {
			this.numerator = numer;
			this.denominator = denom;
		}
	}
	
	/**
	 * Creates a <code>Fraction</code> from an <code>integer</code>.
	 * @param numer
	 */
	public Fraction (int numer) {
		this(numer, 1);
	}
	
	/**
	 * Creates a zero value <code>Fraction</code>.
	 */
	public Fraction () {
		this(0, 1);
	}
	
	public int getNumerator() {
		return numerator;
	}
	
	public int getDenominator() {
		return denominator;
	}
	
	/**
	 * Converts the <code>Fraction</code> to a <code>String</code>.
	 * @return <code>String</code> 
	 */
	public String toString() {
		if (denominator == 1) {
			return numerator + "";
		}
		return numerator + "/" + denominator;
	}
	
	/**
	 * Converts the <code>Fraction</code> to a <code>double</code>.
	 * @return <code>double</code>
	 */
	public double toDouble() {
		return (double)numerator/denominator;
	}
	
	/**
	 * Determines if two <code>Fraction</code> instances are of equal value.
	 * @param other <code>Fraction</code>
	 * @return boolean <code>True</code> if both <code>Fraction</code>
	 * instances are equal
	 */
	public boolean equals(Fraction other) {
		return (this.toDouble() == other.toDouble() );
	}
	
	/*
	 * Simplifies and modifies the current <code>Fraction</code> to its 
	 * lowest terms using <code>gcd</code>.
	 * @see <code>gcd</code>
	 */
	public void toLowestTerms() {
		int gcd = gcd(numerator,denominator);
		numerator = numerator / gcd;
		denominator = denominator / gcd;
	}
	
	/**
	 * Determines the greatest common denominator (GCD or GCM) of two integers.
	 * GCD is the largest positve integer that divides two integers
	 * evenly.
	 * @param a
	 * @param b
	 * @return <code>int</code>
	 * @see <code>lcm</code>
	 */
	public static int gcd(int a, int b) {
		while (a != 0 && b != 0) { // while a & b are not zero
			int rem = a % b;	// find the remainder of a divided by b
			a = b;			// set a to b
			b = rem;		// set b to the remainder you found
		}
		return a;
	}
	
	// LCM * GCD 
	/**
	 * Determines the least common multiple (LCM) of two integers. 
	 * LCM is the smallest pos int that is a multiple of two ints.
	 * @param a 
	 * @param b
	 * @return <code>int</code> lcm
	 * @see <code>gcd</code>
	 */
	public static int lcm(int a, int b) {
		int top = a * b;
		if (top < 0) { // if its negative make it positive.
			top = -top;
		}
		return top / (gcd(a, b));
	}
	
	/**
	 * Performs addition on the current <code>Fraction</code> addend.
	 * @param other <code>Fraction</code> addend
	 * @return <code>Fraction</code> sum
	 */
	public Fraction add(Fraction other) {
		this.toLowestTerms(); other.toLowestTerms(); 
		int lcm = lcm(this.denominator, other.denominator); 
		int mult1 = lcm / this.denominator;
		int mult2 = lcm / other.denominator;
		this.numerator = (this.numerator * mult1) + (other.numerator * mult2);
		this.denominator = lcm;
		return this;
	}
	
	/**
	 * Performs subtraction on the current <code>Fraction</code> minuend.
	 * @param other <code>Fraction</code> subtrahend
	 * @return <code>Fraction</code> difference
	 */
	public Fraction subtract(Fraction other) {
		this.toLowestTerms(); other.toLowestTerms();
		int lcm = lcm(this.denominator, other.denominator); 
		int mult1 = lcm / this.denominator;
		int mult2 = lcm / other.denominator;
		this.numerator = (this.numerator * mult1) - (other.numerator * mult2);
		this.denominator = lcm;
		return this;
	}
	
	/**
	 * Performs multiplication on the current <code>Fraction</code> multiplicand.
	 * @param other <code>Fraction</code> multiplier
	 * @return <code>Fraction</code> product
	 */
	public Fraction multiply(Fraction other) {
		this.numerator = this.numerator * other.numerator;
		this.denominator = this.denominator * other.denominator;
		this.toLowestTerms();
		return this;
	}
	
	/**
	 * Performs division on the current <code>Fraction</code>.
	 * @param other <code>Fraction</code> divisor
	 * @return <code>Fraction</code> quotient
	 */
	public Fraction divide(Fraction other) {
		if (other.toDouble() == 0.0) {
			throw new IllegalArgumentException("Cannot divide by 0.");
		}
		else {
			this.numerator = this.numerator * other.denominator;
			this.denominator = this.denominator * other.numerator;
			this.toLowestTerms();
			return this;
		}
	}

	
}
