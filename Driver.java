import java.util.Arrays;

public class Driver {
    public static void main(String [] args) throws Exception{
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,0,0,5};
        int [] p1 = {0,1,2,3};
        Polynomial poly1 = new Polynomial(c1,p1);
        double [] c2 = {0,-2,0,0,-9};
        int [] p2 = {0,1,2,3,4};
        Polynomial poly2 = new Polynomial(c2, p2);
        Polynomial s = poly1.add(poly2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
        System.out.println("1 is a root of s");
        else
        System.out.println("1 is not a root of s");

        Polynomial t = poly1.multiply(poly2);
        System.out.println(Arrays.toString(t.coefficients));
        System.out.println(Arrays.toString(t.powers));
        poly1.saveToFile("test.txt");
        Polynomial k = Polynomial.takesFile("test.txt");
        System.out.println(Arrays.toString(k.coefficients));
        System.out.println(Arrays.toString(k.powers));
        System.out.println(Arrays.toString(poly1.coefficients));
        System.out.println(Arrays.toString(poly1.powers));
    }
}