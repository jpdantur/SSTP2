package ar.edu.itba.ss.domain;

import java.util.List;
import java.util.stream.Collectors;

public class Particle {

    private Double x;
    private Double y;
    private Double radix;
    private Cell cell;

    public Particle(Double x, Double y, Double radix) {
        this.x = x;
        this.y = y;
        this.radix = radix;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    public Double getRadix() {
        return radix;
    }

    public Double distanceCenterToCenter(Particle particle){
        Double difx = getX() - particle.getX();
        Double dify = getY() - particle.getY();
        return Math.sqrt(Math.pow(difx,2)+ Math.pow(dify,2));
    }

    public Double distanceBorderToBorder(Particle particle){
        return distanceCenterToCenter(particle) - (getRadix() + particle.getRadix());
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isCloseEnough(Particle particle, Double maxDistance) {
        return distanceBorderToBorder(particle) <= maxDistance;
    }

    public List<Particle> getOtherParticlesInCell(){
        return getCell().getParticles().stream().filter(p -> !p.equals(this)).collect(Collectors.toList());
    }

    public String printParticle(){
        return String.format("(%f,%f)", x, y);
    }

    @Override
    public String toString() {
        return String.format("(%f,%f) r= %f", x, y, radix);
    }
}
