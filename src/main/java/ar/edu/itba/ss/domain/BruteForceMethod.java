package ar.edu.itba.ss.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by scamisay on 08/03/18.
 */
public class BruteForceMethod {

    private Double rc;
    private List<Particle> particles = new ArrayList<>();
    private boolean periodicContourCondition;

    private Duration timeElapsed;

    public BruteForceMethod(Double rc, List<Particle> particles, boolean periodicContourCondition) {
        this.rc = rc;
        this.particles = particles;
        this.periodicContourCondition = periodicContourCondition;
    }

    public Map<Particle, List<Particle>> calculate(){
        Instant b = Instant.now();

        Map<Particle, List<Particle>> output =  particles.stream()
                .collect(Collectors.toMap(p->p, this::calculateParticleNeighbours));

        Instant e = Instant.now();
        timeElapsed = Duration.between(b, e);
        return output;
    }

    private List<Particle> calculateParticleNeighbours(Particle particle){
        return particles.stream()
                        .filter(p -> p.isCloseEnough(particle, rc))
                        .collect(Collectors.toList());
    }

    public Duration getTimeElapsed() {
        return timeElapsed;
    }
}
