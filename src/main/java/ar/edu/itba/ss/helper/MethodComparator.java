package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.BruteForceMethod;
import ar.edu.itba.ss.domain.CellIndexMethod;
import ar.edu.itba.ss.domain.Particle;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by scamisay on 08/03/18.
 */
public class MethodComparator {

    private Double L;
    private Integer M;
    private Double rc;
    private Double radix;

    public MethodComparator(Double l, Integer m, Double rc, Double radix) {
        L = l;
        M = m;
        this.rc = rc;
        this.radix = radix;
    }

    /**
     * Hago variar N
     */
    public List<ComparisonResult> testOverParticles(Integer from, Integer step, Integer to){
        List<ComparisonResult> results = new ArrayList<>();
        for(Integer N = from; N <= to ; N += step){
            ComparisonResult result = compareMethods(N, M, L, rc, radix);
            results.add(result);
        }
        return results;
    }

    /**
     * Hago variar M
     */
    public List<ComparisonResult> testOverCells(Integer from, Integer step, Integer to, Integer N){
        List<ComparisonResult> results = new ArrayList<>();
        for(Integer M = from; M <= to ; M += step){
            ComparisonResult result = compareMethods(N, M, L, rc, radix);
            results.add(result);
        }
        return results;
    }

    private ComparisonResult compareMethods(Integer N, Integer M, Double L, Double rc, Double radix){
        List<Particle> particles = new ParticleGenerator().generate(N, L, radix);

        Instant b = Instant.now();
        CellIndexMethod cim = new CellIndexMethod(M, L, rc, particles, false);
        cim.calculate();
        Instant e = Instant.now();
        Duration timeCIM = Duration.between(b, e);

        BruteForceMethod bf = new BruteForceMethod(rc, particles, false);
        bf.calculate();

        ComparisonResult result = new ComparisonResult(M, timeCIM, bf.getTimeElapsed());
        System.out.println(result.toString());
        return result;
    }

    public class ComparisonResult{
        Integer variableValue;
        Duration cim;
        Duration bf;

        public ComparisonResult(Integer variableValue, Duration cim, Duration bf) {
            this.variableValue = variableValue;
            this.cim = cim;
            this.bf = bf;
        }

        @Override
        public String toString() {
            return String.format(" %d",  cim.toMillis());
            //return String.format("%d, %d, %d", variableValue, cim.toMillis(), bf.toMillis());
        }
    }
}
