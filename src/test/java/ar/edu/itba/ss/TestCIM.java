package ar.edu.itba.ss;

import ar.edu.itba.ss.domain.Particle;
import ar.edu.itba.ss.helper.ParticleGenerator;
import ar.edu.itba.ss.helper.Printer;

import java.util.List;
import java.util.Scanner;

public class TestCIM {
    private static final double RADIX = .1;
    private static final double SPEED = 0.03;

    public static void main(String[] args) {
        int N, M;
        double L, rc;
        boolean periodicContourCondition;
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print("N:");
                N = sc.nextInt();

                System.out.print("L:");
                L = sc.nextDouble();

                System.out.print("M:");
                M = sc.nextInt();

                System.out.print("rc:");
                rc = sc.nextDouble();

                System.out.print("periodicContourCondition:");
                periodicContourCondition = sc.nextBoolean();
                break;
            } catch (Exception e) {
                System.out.println("Vuelva a intentar introducir los valores");
            }
        }

        List<Particle> particles = new ParticleGenerator().generate(N, L, RADIX,SPEED);
        Printer printer = new Printer(particles, L, M, rc, periodicContourCondition, 0);
        printer.printFiles();

    }
}