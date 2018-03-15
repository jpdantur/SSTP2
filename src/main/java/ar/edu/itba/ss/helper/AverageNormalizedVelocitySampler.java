package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.BandadasDeAgentesAutopropulsados;
import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by scamisay on 14/03/18.
 */
public class AverageNormalizedVelocitySampler {

    private List<Integer> nList;
    private List<Double> lList;
    private int M;
    private double rc;
    private boolean periodicContourCondition;
    private int steps;
    private RandomDataGenerator rng;
    double eta;
    double speed;

    public AverageNormalizedVelocitySampler(List<Integer> nList,
                                            List<Double> lList,
                                            int m, double rc, double eta,
                                            int steps, RandomDataGenerator rng,
                                            double speed
    ) {
        this.nList = nList;
        this.lList = lList;
        M = m;
        this.rc = rc;
        this.eta=eta;
        this.periodicContourCondition = true;
        this.steps = steps;
        this.rng = rng;
        this.speed = speed;
    }

    public List<List<Pair>> sampleEta(Double from, Double step, Double to){
        List<Double> dom = createDom(from, step, to);
        return IntStream.range(0, nList.size())
                .boxed()
                .map(i -> sampleEtaN(nList.get(i), lList.get(i),  dom))
                .collect(Collectors.toList());
    }

    private List<Double> createDom(Double from, Double step, Double to) {
        List<Double> dom = new ArrayList<>();
        for(Double x = from; x <= to ; x += step){
            dom.add(x);
        }
        return dom;
    }

    private List<Pair> sampleEtaN(Integer N, Double L, List<Double> dom){
        List<Pair> pairs = new ArrayList<>();
        for(Double eta : dom){
            List<Particle> particles = new ParticleGenerator().generate(N, L, speed, rng);
            BandadasDeAgentesAutopropulsados alg = new BandadasDeAgentesAutopropulsados(particles, L,
                    M, rc,eta, periodicContourCondition, this.steps, rng);
            alg.run(null);
            double va = averageNormalizedVelocity(alg.getParticles(), speed);
            pairs.add(new Pair(eta, va));
        }
        return pairs;
    }

    private Double averageNormalizedVelocity(List<Particle> particles, double speed){
        Double sumX = particles.stream().map(Particle::getVx).reduce(Double::sum).get();
        Double sumY = particles.stream().map(Particle::getVy).reduce(Double::sum).get();
        Double module = sqrt(pow(sumX,2) + pow(sumY,2));
        return module/(particles.size()*speed);
    }

    public class Pair{
        private Double x;
        private Double y;

        public Pair(Double x, Double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("%.6f %.6f", x, y);
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }
    }

}
