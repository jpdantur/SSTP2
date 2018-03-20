package ar.edu.itba.ss;

import ar.edu.itba.ss.domain.Particle;
import ar.edu.itba.ss.helper.CIMPrinter;
import ar.edu.itba.ss.helper.ParticleGenerator;
import ar.edu.itba.ss.helper.Printer;

import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class TP1App {
    private static final Double RADIX = .1;

    public static void main(String[] args) {
        Integer N, M;
        Double L, rc;
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

        List<Particle> particles = new ParticleGenerator().generate(N, L, RADIX);
        CIMPrinter printer = new CIMPrinter(particles, L, M, 2., periodicContourCondition, 0);
        printer.printFiles();

    }
}