public class Fraction {
    private int numerator;
    private int denominator;

    public Fraction(int numerator, int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.numerator = numerator;
        this.denominator = denominator;
        simplify(); // Simplify the fraction upon initialization
    }

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
        simplify();
    }

    public int getDenominator() {
        return denominator;
    }

    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.denominator = denominator;
        simplify();
    }

    // Helper method to calculate the Greatest Common Divisor (GCD) using recursion
    private int calculateGcd(int a, int b) {
        if (b == 0) {
            return a;
        }
        return calculateGcd(b, a % b);
    }

    // Simplify the fraction by dividing both numerator and denominator by their GCD
    private void simplify() {
        int gcd = calculateGcd(numerator, denominator);
        numerator /= gcd;
        denominator /= gcd;
    }

    public Fraction addFraction(Fraction secondFraction) {
        int newNumerator = (numerator * secondFraction.denominator) + (secondFraction.numerator * denominator);
        int newDenominator = denominator * secondFraction.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction subtractFraction(Fraction secondFraction) {
        int newNumerator = (numerator * secondFraction.denominator) - (secondFraction.numerator * denominator);
        int newDenominator = denominator * secondFraction.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction multiplyFraction(Fraction secondFraction) {
        int newNumerator = numerator * secondFraction.numerator;
        int newDenominator = denominator * secondFraction.denominator;
        return new Fraction(newNumerator, newDenominator);
    }

    public Fraction divideFraction(Fraction secondFraction) {
        if (secondFraction.numerator == 0) {
            throw new ArithmeticException("Cannot divide by zero.");
        }
        int newNumerator = numerator * secondFraction.denominator;
        int newDenominator = denominator * secondFraction.numerator;
        return new Fraction(newNumerator, newDenominator);
    }

    @Override
    public String toString() {
        return numerator + " / " + denominator;
    }

    public void displayImproper() {
        int wholePart = numerator / denominator;
        int remainder = numerator % denominator;

        if (remainder == 0) {
            System.out.println("The fraction is not an improper fraction.");
            System.out.println(this.toString()); // Use toString method to display the fraction
        } else {
            System.out.println(wholePart + " " + remainder + "/" + denominator);
        }
    }

}
