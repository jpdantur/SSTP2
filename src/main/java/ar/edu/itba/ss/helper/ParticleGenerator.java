package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by scamisay on 08/03/18.
 */
public class ParticleGenerator {

    public List<Particle> generate(Integer N, Double L, Double radix) {
        int seed = 50000;// new Double(Math.random()*100000).longValue();
        RandomDataGenerator generator = new RandomDataGenerator(new JDKRandomGenerator(seed));
        return IntStream.range(0,N)
                .mapToObj(i ->new Particle(generator.nextUniform(0,L,true), generator.nextUniform(0,L,true), radix))
                .collect(Collectors.toList());
    }
}
