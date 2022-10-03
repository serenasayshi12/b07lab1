import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
    double[] coefficients;
    int[] powers;

    public Polynomial() {
        // Set polynominal to zero
            //like reinitalize it somehow...
        this.coefficients = new double[]{0};
        this.powers = new int[]{0};
    }

    public Polynomial(double[] x, int[] y) {
        //takes an array of double as an argument and sets coefficient
        this.coefficients = x;
        this.powers = y;
    }
    //5x^2+5x+1 -> [5,5,1] [2,1,0]
    //x^2+1 -> [1,1] [2,0]

    public Polynomial add(Polynomial x) { //idk if this argument is right
        int h = 0;
        double[] coefficients_draft = new double[this.coefficients.length + x.coefficients.length];
        int [] powers_draft = new int[this.coefficients.length + x.coefficients.length];
        boolean [] usedIndex1 = new boolean[x.coefficients.length];
        boolean [] usedIndex2 = new boolean[this.coefficients.length];
        // //New polynomial for the sum at the end
        for (int i = 0; i < x.powers.length;i++) {
            for (int j = 0; j < this.powers.length;j++) {   
                if (x.powers[i] == this.powers[j] && x.coefficients[i] + this.coefficients[j] != 0) {
                    coefficients_draft[h] = x.coefficients[i] + this.coefficients[j];
                    powers_draft[h] = x.powers[i];
                    usedIndex1[i] = true;
                    usedIndex2[j] = true;
                    h++;
                }
            }  
        }
        for (int i = 0; i < x.powers.length; i++) {
            if (usedIndex1[i] == false && x.coefficients[i] != 0) {
                coefficients_draft[h] = x.coefficients[i];
                powers_draft[h] = x.powers[i];
                h++;
            }
        }
        for (int i = 0; i < this.powers.length; i++) {
            if (usedIndex2[i] == false && this.coefficients[i] != 0) {
                coefficients_draft[h] = this.coefficients[i];
                powers_draft[h] = this.powers[i];
                h++;
            }
        }
        
        // Get rid of excess 0's at the ends of arrays
        double[] coefficients_final_trim = new double[h];
        int[] powers_final_trim = new int[h];
        for (int i = 0; i < h; i++) {
            coefficients_final_trim[i] = coefficients_draft[i];
            powers_final_trim[i] = powers_draft[i];
        }

        return new Polynomial(coefficients_final_trim, powers_final_trim);      
    }

    public Polynomial multiply(Polynomial x){   
        int n = this.coefficients.length * x.coefficients.length; // maximum length of product
        int h = 0; // index for coefficients_draft and powers_draft
        int f = 0; // index for coefficients_final and powers_final
        // raw product of polynomial
        double[] coefficients_draft = new double[n]; 
        int[] powers_draft = new int[n];
        // aggregated variables
        double[] coefficients_final = new double[n];
        int[] powers_final = new int[n];

        //nested for loop through both, multiplying coefficients and adding exponents, and then combine all that have the same power 
        for (int i = 0; i < this.powers.length; i++){
            for (int j = 0; j < x.powers.length;j++){
                coefficients_draft[h] = this.coefficients[i] * x.coefficients[j];
                powers_draft[h] = this.powers[i]+x.powers[j];
                h++;
            }
        }

        // System.out.println(Arrays.toString(coefficients_draft));
        // System.out.println(Arrays.toString(powers_draft));
        
        // Stores the indices of variables already added to final equation.
        boolean[] index_used = new boolean[powers_draft.length];

        for (int i = 0; i < powers_draft.length; i++) {
            if (index_used[i]) continue; // continue to next variable if current one is accounted for
            double coefficients_sum = coefficients_draft[i];
            for (int j = i; j < powers_draft.length; j++){
                if (powers_draft[i] == powers_draft[j] && i != j && index_used[j] == false){
                    coefficients_sum += coefficients_draft[j]; // keep adding coefficients with the same power that have not been already used
                    index_used[j] = true; // mark the current coefficient as used
                }
                    // when you go to the first entry, and combine all the coefficients with that power
                    //make an empty array and add the coefficients in that array with the same power, and then sum it together 
            }
            if (coefficients_sum != 0) {
                coefficients_final[f] = coefficients_sum;
                powers_final[f] = powers_draft[i];
                f++;
            }
        }
        
        // Get rid of excess 0's at the ends of arrays
        double[] coefficients_final_trim = new double[f];
        int[] powers_final_trim = new int[f];
        for (int i = 0; i < f; i++) {
            coefficients_final_trim[i] = coefficients_final[i];
            powers_final_trim[i] = powers_final[i];
        }

        // for (int p = 0; p < usedIndex.length; p++){
        //     if (usedIndex[p] == false){
        //         powers_final[p] = powers_draft[p];
        //         coefficients_final[p] = coefficients_draft[p];
        //     }
        // }
        return new Polynomial(coefficients_final_trim, powers_final_trim);
        
    }

    public static Polynomial takesFile(String x) throws Exception {
        Scanner scan = new Scanner(new File(x));
        int h = 0; //keeps index of character array that was used last
        int g = 0; //keeps index of string_array
        //5-3x2+7x8
        String s1 = scan.nextLine();
        scan.close();
        //System.out.println(s1);
        // char[] character_array = new char[s1.length()];
        // // System.out.println(character_array);
        // for (int i = 0; i < s1.length(); i++)
        // {
        //     character_array[i] = s1.charAt(i);
        //     // System.out.println(character_array[i]);

        // }
        String[] string_array = new String[s1.length()];

        for (int i = 0; i < s1.length(); i++) {           
            if (i == 0 && s1.charAt(i) == '-') continue; //handle if expression begins with '-'
            else if(s1.charAt(i)== '+'|| s1.charAt(i)== '-') {
                string_array[g] = s1.substring(h,i);
                g++;
                h = i;
            } else if (i == s1.length()-1) {
                string_array[g] = s1.substring(h,i+1);
                g++;
            }
        }
        // for (int i =0; i < g; i++){
        //     System.out.println(string_array[i]);
        // }
        
        int f = 0;
        double[] coefficients_draft = new double[g];
        int[] powers_draft = new int[g];

        for (int i = 0; i < g; i++){
            String[] temp_array = string_array[i].split("x");
            if (temp_array[0].charAt(0) == '+') {
                String coefficient = temp_array[0].substring(1, temp_array[0].length());
                coefficients_draft[f] = Double.parseDouble(coefficient);
            } else if (temp_array[0].charAt(0) == '-'){
                String coefficient = temp_array[0].substring(1, temp_array[0].length());
                coefficients_draft[f] = Double.parseDouble(coefficient) * -1; 
            } else { // handle if no sign (the beginning of expression contains a positive coefficient)
                coefficients_draft[f] = Double.parseDouble(temp_array[0]);
            }
            if (temp_array.length < 2) { // x^0
                powers_draft[f] = 0;
            } else if (temp_array[1] == "") { // x^1
                powers_draft[f] = 1;
            } else { // x^n, n > 1
                powers_draft[f] = Integer.parseInt(temp_array[1]);
            }
            f++;
        }
        return new Polynomial(coefficients_draft, powers_draft);
    }

    public void saveToFile(String x) throws Exception{ 
        //get a polynomial and save it into the file
        String s1 = "";
        for (int i =0; i< this.coefficients.length; i++)
        {
            if (this.coefficients[i] > 0 && i != 0){
                s1 += "+";
            }
            // else if(this.coefficients[i] < 0){
            //     s1 += "-";
            // }
            if (this.coefficients[i] != 0.0){
                s1 += this.coefficients[i];
                if (this.powers[i] != 0){
                    s1 += "x"; // cannot be 0 
                    if (this.powers[i] != 1){
                        s1 += this.powers[i];  //cannot be 0 or 1
                    }
                }   
            }
            
        }
        FileWriter myWriter = new FileWriter(x);
        myWriter.write(s1);
        myWriter.close();
    }
        

    
    
    public double evaluate(double x) { //x is the value of x
        double sum = 0;
        for (int i = 0; i < this.coefficients.length; i++) {
            double partial_sum = pow(x, this.powers[i]);
            partial_sum = partial_sum * this.coefficients[i];
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

    public static void main(String[] args) throws Exception{
        Polynomial a = new Polynomial(new double[]{-6, -2, 0, 5}, new int[]{0, 1, 2, 3});
        // Polynomial b = new Polynomial(new double[]{1,-2, 1}, new int[]{0, 1, 2});

        // Polynomial a = new Polynomial(new double[]{1, 2}, new int[]{0, 1});
        // Polynomial b = new Polynomial(new double[]{1, 1}, new int[]{0, 1});

        // System.out.println(Arrays.toString(a.add(b).coefficients));
        // System.out.println(Arrays.toString(a.add(b).powers));
        // Polynomial c = a.multiply(b);
        // System.out.println(Arrays.toString(c.coefficients));
        // System.out.println(Arrays.toString(c.powers));
        // System.out.println(a.evaluate(1));
        // System.out.println(Arrays.toString(Polynomial.takesFile("test.txt").coefficients));
        // System.out.println(Arrays.toString(Polynomial.takesFile("test.txt").powers));
//         a.saveToFile("output.txt");
    }
    
}
