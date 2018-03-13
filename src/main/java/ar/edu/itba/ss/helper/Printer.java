package ar.edu.itba.ss.helper;

import ar.edu.itba.ss.domain.CellIndexMethod;
import ar.edu.itba.ss.domain.Particle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.joining;

/**
 * Created by scamisay on 08/03/18.
 */
public class Printer {

    private List<Particle> particles;
    private double L;
    private int M;
    private double rc;
    private boolean periodicContourCondition;
    private int selectedParticleIndex;

    private static final String FILE_NAME_NEIGHBOURS = "neigbours.txt";
    private static final String FILE_NAME_OVITO = "ovito.xyz";

    private static final List<Double> COLOR_OUT_OF_SELECTION = Arrays.asList(0.63922,   0.76078,   0.76078);
    private static final List<Double> COLOR_NEIGBOURH = Arrays.asList(1.00000,   0.30196,   0.58039);
    private static final List<Double> COLOR_SELECTED = Arrays.asList(1.00000,   0.80000,   0.00000);

    private Map<Particle, List<Particle>> calculated;
    private Map<Particle, List<Double>> particlesColors = new HashMap<>();

    public Printer(List<Particle> particles, double l, int m, double rc, boolean periodicContourCondition, int selectedParticleIndex) {
        if(particles == null){
            throw new RuntimeException("Todos los argumentos son obligatorios");
        }

        if(particles.isEmpty() || particles.size()<2){
            throw new RuntimeException("Tiene que haber al menos dos particulas");
        }

        if(selectedParticleIndex >= particles.size() || selectedParticleIndex < 0){
            throw new RuntimeException("Paricula seleccionada fuera de rango");
        }

        this.particles = particles;
        L = l;
        M = m;
        this.rc = rc;
        this.periodicContourCondition = periodicContourCondition;
        this.selectedParticleIndex = selectedParticleIndex;

        calculate();
        calculateColors();
    }

    private void calculateColors() {
        Particle selectedParticle = particles.get(selectedParticleIndex);
        particlesColors.put(selectedParticle, COLOR_SELECTED);
        List<Particle> selected = calculated.get(selectedParticle);
        selected.forEach(p-> particlesColors.put(p, COLOR_NEIGBOURH));
        particles.stream()
                .filter(p -> !selected.contains(p))
                .filter(p -> !selectedParticle.equals(p))
                .forEach(p-> particlesColors.put(p, COLOR_OUT_OF_SELECTION));
    }

    public void printFiles(){
        printNeighbours();
        printForOvito();
    }

    private void printStringToFile(String filename, String content){
        try {
            Files.write(Paths.get(filename), content.getBytes());
        } catch (IOException e) {
            System.out.println("No se pudo crear el archivo "+filename);
        }
    }

    private void printForOvito() {
        printStringToFile(FILE_NAME_OVITO, printParticles());
    }

    private String printParticles() {
        return particles.size() + "\n" +
                particles.size() + "\n" +
                particles.stream()
                        .map(p -> String.format("%.6f %.6f %.5f %.5f %.5f",
                                p.getX(),p.getY(), getRed(p), getGeen(p), getBlue(p)))
                        .collect(Collectors.joining("\n")).replaceAll(",","\\.");
/*       sb.append(particles.size()).append("\n");
        sb.append(particles.size()).append("\n");
        sb.append(
                particles.stream()
                        .map(p -> String.format("%.6f %.6f %.6f %.5f %.5f %.5f",
                                p.getX(),p.getY(), p.getRadix(), getRed(p), getGeen(p), getBlue(p)))
                        .collect(Collectors.joining("\n")).replaceAll(",","\\.")
        ); */
    }

    private double getBlue(Particle p) {
        return particlesColors.get(p).get(2);
    }

    private double getGeen(Particle p) {
        return particlesColors.get(p).get(1);
    }

    private double getRed(Particle p) {
        return particlesColors.get(p).get(0);
    }


    private void printNeighbours() {
        StringBuilder sb = new StringBuilder();
        calculated.keySet().forEach(
            k -> sb.append(String.format(
                    "%s%n%s%n%n",
                    k.printParticle(),
                    calculated.get(k).stream().map(Particle::printParticle).collect(joining(", "))
                    ))
        );

        printStringToFile(FILE_NAME_NEIGHBOURS, sb.toString());
    }

    private void calculate() {
        CellIndexMethod cim = new CellIndexMethod(M,L, rc, particles, periodicContourCondition);
        calculated = cim.calculate();
        System.out.println("tiempo de procesamiento ( milisegundos ): " +cim.getTimeElapsed().toMillis());
    }
}
