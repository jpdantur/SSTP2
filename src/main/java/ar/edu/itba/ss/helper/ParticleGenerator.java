package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.util.FastMath;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by scamisay on 08/03/18.
 */
public class ParticleGenerator {

    public List<Particle> generate(int N, double L, double speed, RandomDataGenerator generator) {
        return IntStream.range(0,N)
                .mapToObj(i ->new Particle(generator.nextUniform(0,L,true),
                        generator.nextUniform(0,L,true),
                        speed,
                        generator.nextUniform(0,2* FastMath.PI,true)))
                .collect(Collectors.toList());
    }

    public List<Particle> generate(int N, double L, double radix) {
        long seed = 1000L;//new Double(Math.random()*100000).longValue();
        Random generator = new Random(seed);
        return IntStream.range(0,N)
                .mapToObj(i ->new Particle(i,generator.nextDouble()*L, generator.nextDouble()*L, radix))
                .collect(Collectors.toList());
    }
}
