package ar.edu.itba.ss;
import ar.edu.itba.ss.domain.Particle;
import ar.edu.itba.ss.helper.ParticleGenerator;
import ar.edu.itba.ss.helper.Printer;
import org.apache.commons.math3.random.*;
import org.apache.commons.math3.util.FastMath;
import java.util.*;



public class App 
{

    public static void main( String[] args ) {

        //System.out.println(30.55%25);
        //System.out.println(-30.22%25);
        int n, m, t;
        double l, rc, eta, speed;
        boolean periodicContourCondition;
        while (true) {
            try {
                Scanner sc = new Scanner(System.in);
                System.out.print("N:");
                n = sc.nextInt();

                System.out.print("L:");
                l = sc.nextDouble();

                //System.out.print("M:");
                m = (int) FastMath.round(l-1);

                //System.out.print("rc:");
                rc = 1.0;

                System.out.print("eta:");
                eta = sc.nextDouble();

                //System.out.print("speed:");
                speed = 0.03;

                System.out.print("steps:");
                t = sc.nextInt();


                //System.out.print("periodicContourCondition:");
                periodicContourCondition = true;
                break;
            } catch (Exception e) {
                System.out.println("Vuelva a intentar introducir los valores");
            }
        }
        int seed = 5000;
        RandomDataGenerator rng = new RandomDataGenerator(new JDKRandomGenerator());

        List<Particle> particles = new ParticleGenerator().generate(n, l,speed, rng);
        Printer printer = new Printer(particles, l, m, rc,eta, periodicContourCondition, 0);
        for (int i=0;i<t;i++) {
            long start = System.currentTimeMillis();
            for (Particle p:particles) {
                p.clearNeighbours();
            }
            printer.calculate();
            printer.printFiles(i);
            printer.update(rng);
            long end = System.currentTimeMillis();
            System.out.println(i + " - " + (end-start) + " ms");
        }

    }
}
