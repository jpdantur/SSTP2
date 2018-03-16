package ar.edu.itba.ss;

import ar.edu.itba.ss.helper.AverageNormalizedVelocitySampler;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Created by scamisay on 15/03/18.
 */
public class MainRo {

    public static void main( String[] args ) {
        /*List<Integer> nList = Arrays.asList(40, 100, 400, 4000);
        List<Double> lList = Arrays.asList(3.1, 5., 10., 31.6);*/

        List<Integer> nList = Arrays.asList(4000);
        List<Double> lList = Arrays.asList( 31.6);

        int M = 5;
        Double rc = 1.;
        Double eta = 0.5;
        int steps = 500;
        int seed = 5000;
        RandomDataGenerator rng = new RandomDataGenerator(new JDKRandomGenerator(seed));
        double speed = 0.3;

        List<Double> roVector = Arrays.asList(.001,.01,0.05, .1,.25,.5,.7, 1., 1.5, 2.);

Instant b = Instant.now();

        AverageNormalizedVelocitySampler sampler = new AverageNormalizedVelocitySampler(
                nList, lList, M, rc, eta, steps, rng, speed
        );
        List<List<AverageNormalizedVelocitySampler.Pair>> results
                = sampler.sampleRo(40, roVector,eta);
        String output = printToFile(results, nList);
        System.out.print(output);

        Instant e = Instant.now();
        System.out.println( Duration.between(b, e) );
    }
//m 10 PT44.568S
//m 5 PT1M28.543S
    private static String printToFile(List<List<AverageNormalizedVelocitySampler.Pair>> results, List<Integer> nList) {
        StringBuffer sb = new StringBuffer();
        for(int y =0; y < results.get(0).size(); y++){
            sb.append(String.format("%.6f ",results.get(0).get(y).getX()));
            for(int x = 0; x < nList.size(); x++){
                sb.append(String.format("%.6f ",results.get(x).get(y).getY()));
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
