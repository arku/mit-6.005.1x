package warmup;

import java.util.Set;
import java.util.HashSet;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {

        assert(!((a == 0) && (b == 0) && (c == 0)));

        Set<Integer> roots = new HashSet<Integer>();
        double discriminant = Math.pow(b, 2) - (4.0 * a * c);
        System.out.println(discriminant);
        double sqrt = Math.sqrt(discriminant);

        // Check to see if the roots are imaginary
        if (! Double.isNaN(sqrt) && isWholeNumber(sqrt)) {
            int discriminantSquareRoot  = (int) sqrt;
            double firstRoot = (-b + discriminantSquareRoot) / (2.0 * a);
            double secondRoot = (-b - discriminantSquareRoot) / (2.0 * a);

            if (a == 0) {
                if(b != 0 && isWholeNumber((double)-c/b))
                    roots.add(-c/b);
            }
            else if (isWholeNumber(firstRoot)) {
                roots.add((int)firstRoot);
                roots.add((int)secondRoot);
            }
            else if (c == 0 && isWholeNumber(Math.sqrt(-b/a)))
                roots.add((int)Math.sqrt(-b/a));
        }
        return roots;
    }

    private static boolean isWholeNumber(double number) {
        return number == (int) number;
    }

    
    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible solutions are:");
        Set<Integer> result = roots(1, -4, 3);
        System.out.println(result);
    }

    /* Copyright (c) 2016 MIT 6.005 course staff, all rights reserved.
     * Redistribution of original or derived work requires explicit permission.
     * Don't post any of this code on the web or to a public Github repository.
     */
}
