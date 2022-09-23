import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

public class Polynomial {
    private double[] coefficients;

    public Polynomial() {
        // Set polynominal to zero
            //like reinitalize it somehow...
        this.coefficients = new double[]{0};
    }

    public Polynomial(double[] x) {
        //takes an array of double as an argument and sets coefficient
        this.coefficients = x;
    }

    public Polynomial add(Polynomial x) { //idk if this argument is right
        //Making new polynomial
        Polynomial polynomial2 = new Polynomial();
        polynomial2.coefficients = new double[Math.max(this.coefficients.length, x.coefficients.length)];
        for (int i = 0; i < x.coefficients.length; i++) {
            polynomial2.coefficients[i] += x.coefficients[i];
        }
        for (int i = 0; i < this.coefficients.length; i++){
            polynomial2.coefficients[i] += this.coefficients[i];
        }
        return polynomial2;
        
    }
    
    public double evaluate(double x) {
        double sum = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            double partial_sum = pow(x, i);
            partial_sum = partial_sum * coefficients[i];
            sum += partial_sum;
        }
        return sum; 
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    private static double pow(double x, int power) {
        if (power == 0) {
            return 1;
        }
        double y = x;
        for (int i = 1; i < power; i++) {
            y = y*x;
        }
        return y;
    }

    private void printCoefficients() {
        for (int i = 0; i < this.coefficients.length; i++) {
            System.out.print(this.coefficients[i] + "x^" + i + " ");
        }
    }

    // public static void main(String[] args) {
    //     Polynomial a = new Polynomial(new double[]{6, -2, 0, 5});
    //     Polynomial b = new Polynomial(new double[]{1,-2,1});

    //     //System.out.println(pow(-1, 0));
    //     // System.out.println(a.hasRoot(1));
    //     System.out.println(Arrays.toString(b.add(a).coefficients));
    // }
}