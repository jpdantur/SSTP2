package ar.edu.itba.ss.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Cell {

    private Range rangeX;
    private Range rangeY;
    private Set<Cell> neigbours = new HashSet<>();
    private List<Particle> particles = new ArrayList<>();

    public Cell(Range rangeX, Range rangeY) {
        this.rangeX = rangeX;
        this.rangeY = rangeY;
    }

   /*todo: eliminar esto despues del test.xyz del main
   public Cell(List<Particle> particles) {
        setParticles(particles);
    }*/

    public void addParticle(Particle particle){
        this.particles.add(particle);
        particle.setCell(this);
    }

    public List<Particle> getParticles() {
        return particles;
    }

    public Set<Cell> getNeighbours() {
        return neigbours;
    }

    public void setNeigbours(Set<Cell> neigbours) {
        this.neigbours = neigbours;
    }

    public boolean isInside(Particle particle){
        return rangeX.isInRange(particle.getX()) && rangeY.isInRange(particle.getY());
    }

    @Override
    public String toString() {
        return String.format("x:%s y:%s particles:%d",rangeX.toString(), rangeY.toString(), particles.size());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cell cell = (Cell) o;

        if (!rangeX.equals(cell.rangeX)) return false;
        return rangeY.equals(cell.rangeY);
    }

    @Override
    public int hashCode() {
        int result = rangeX.hashCode();
        result = 31 * result + rangeY.hashCode();
        return result;
    }
}
