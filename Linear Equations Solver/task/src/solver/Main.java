package solver;

import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

    double a = sc.nextDouble();
    double b = sc.nextDouble();
    double c = sc.nextDouble();
    double d = sc.nextDouble();
    double e = sc.nextDouble();
    double f = sc.nextDouble();

    // Pattern for x = ce-bf / ae-bd
    double x = (c*e - b*f) / (a * e - b*d) ;
    // Pattern for y = cd-af / bd-ae
    double y = (f - c * (d/a)) / (e - b * (d/a));
    System.out.println(x + " " + y);

    }
}
