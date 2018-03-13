package ar.edu.itba.ss.domain;

import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

public class CellIndexMethod {

    private Integer M;
    private Double L;
    private Double rc;
    private List<Particle> particles = new ArrayList<>();
    private Cell[][] environment;
    private boolean periodicContourCondition;

    private Duration timeElapsed;

    public CellIndexMethod(Integer m, Double l, Double rc, List<Particle> particles, boolean periodicContourCondition) {
        M = m;
        L = l;
        this.rc = rc;
        this.particles = particles;
        this.periodicContourCondition = periodicContourCondition;

        initializeEnvironment();
    }

    private void initializeEnvironment() {
        //creo cells
        environment = new Cell[M][M];
        Double offset = L/M;

        //calculo rangos del environment
        List<Range> ranges = DoubleStream.iterate(0., d -> d + offset)
                .limit(M).boxed()
                .map(d -> new Range(d, d+ offset))
                .collect(Collectors.toList());

        //inicializo environment con celdas y asigno las particulas a cada celda
        for (Integer x = 0; x < M; x++) {
            for (Integer y = 0; y < M; y++) {
                Range rangex = ranges.get(x);
                Range rangey = ranges.get(y);

                Cell cell = new Cell(rangex, rangey);
                environment[x][y] = cell;

                particles.stream()
                        .filter(cell::isInside)
                        .forEach(cell::addParticle);
            }
        }

        //calculo vecinos para cada celda
        for (Integer x = 0; x < M; x++) {
            for (Integer y = 0; y < M; y++) {
                environment[x][y].setNeigbours(calculateParticleNeighbours(x,y));
            }
        }
    }

    public Map<Particle, List<Particle>> calculate(){
        Instant b = Instant.now();

        Map<Particle, List<Particle>> output =  particles.stream()
                .collect(Collectors.toMap(p->p, this::calculateParticleNeighbours));

        Instant e = Instant.now();
        timeElapsed = Duration.between(b, e);
        return output;
    }

    public Duration getTimeElapsed() {
        return timeElapsed;
    }

    /***
     * (x,y+1) (x+1,y+1)
     * (x,y)   (x+1,y)
     *         (x+1,y-1)
     **/
    private Set<Cell> calculateParticleNeighbours(Integer x, Integer y){
        Cell cell1, cell2, cell3, cell4;
        if(periodicContourCondition){
            cell1 = environment[x][(y + 1) % M];
            cell2 = environment[(x + 1) % M][(y + 1) % M];
            cell3 = environment[(x + 1) % M][y];
            cell4 = environment[(x + 1) % M][((y - 1) % M) < 0 ? ((y - 1) % M) + M : (y - 1) % M];
        }else{
            cell1 = nullIfOutOfBounds(x, y+1);
            cell2 = nullIfOutOfBounds(x + 1,y + 1);
            cell3 = nullIfOutOfBounds(x + 1,y);
            cell4 = nullIfOutOfBounds(x + 1,y - 1);
        }

        return Stream.of(
                cell1,
                cell2,
                cell3,
                cell4
        ).filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toCollection(HashSet::new));
    }

    public Set<Cell> generateSimpleNeighbourCells(Integer x, Integer y){
        Cell cell1, cell2, cell3, cell4;
        cell1 = nullIfOutOfBounds(x, y+1);
        cell2 = nullIfOutOfBounds(x + 1,y + 1);
        cell3 = nullIfOutOfBounds(x + 1,y);
        cell4 = nullIfOutOfBounds(x + 1,y - 1);

        return Stream.of(
                cell1,
                cell2,
                cell3,
                cell4
        ).filter(Objects::nonNull)
                .distinct()
                .collect(Collectors.toCollection(HashSet::new));
    }


    private Cell nullIfOutOfBounds(Integer x, int y) {
        if(x < 0 || x >= M){
            return null;
        }else if(y < 0 || y >= M){
            return null;
        }else{
            return environment[x][y];
        }
    }


    /**
     * traer celdas vecinas
     * por cada celda traer particulas
     * filtrar las particulas que esten a menos de rc de la particle
     */
    private List<Particle> calculateParticleNeighbours(Particle particle){
        List<Particle> particles = Stream.concat(
                particle.getCell().getNeighbours().stream()
                        .map(Cell::getParticles)
                        .flatMap(List::stream)
                        .filter(p -> particle.isNeighbourCloseEnough(p, rc, periodicContourCondition))
                ,
                particle.getOtherParticlesInCell().stream()
                        .filter(p -> p.isCloseEnough(particle, rc))
        ).collect(Collectors.toList());

        particles.forEach( p -> p.addNeighbour(particle));
        particles.forEach( p -> particle.addNeighbour(p));

        return particles;
    }

}