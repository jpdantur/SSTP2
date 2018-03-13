package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.Particle;
import org.apache.commons.math3.random.JDKRandomGenerator;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.apache.commons.math3.util.FastMath;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by scamisay on 08/03/18.
 */
public class ParticleGenerator {

    public List<Particle> generate(int N, double L, double radix, double speed) {
        int seed = 50000;// new double(Math.random()*100000).longValue();
        RandomDataGenerator generator = new RandomDataGenerator(new JDKRandomGenerator(seed));
        return IntStream.range(0,N)
                .mapToObj(i ->new Particle(generator.nextUniform(0,L,true),
                        generator.nextUniform(0,L,true),
                        radix,
                        speed,
                        generator.nextUniform(0,2* FastMath.PI,true)))
                .collect(Collectors.toList());
    }
}
