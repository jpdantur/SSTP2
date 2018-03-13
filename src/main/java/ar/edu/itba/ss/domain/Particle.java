package ar.edu.itba.ss.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Particle {

    private double x;
    private double y;
    private double radix;
    private Cell cell;
    private double speed;
    private double angle;
    private double nextAngle;
    private List<Particle> neighbours = new ArrayList<>();

    public Particle(double x, double y, double radix, double speed, double angle) {
        this.x = x;
        this.y = y;
        this.radix = radix;
        this.speed = speed;
        this.angle = angle;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getRadix() {
        return radix;
    }

    public void updateLocation() {
    }

    public double distanceCenterToCenter(Particle particle) {
        double difx = getX() - particle.getX();
        double dify = getY() - particle.getY();
        return Math.sqrt(Math.pow(difx, 2) + Math.pow(dify, 2));
    }

    public double distanceBorderToBorder(Particle particle) {
        return distanceCenterToCenter(particle) - (getRadix() + particle.getRadix());
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isCloseEnough(Particle particle, double maxDistance) {
        return distanceBorderToBorder(particle) <= maxDistance;
    }

    public boolean isNeighbourCloseEnough(Particle particle, double maxDistance, boolean periodicContourCondition) {
        if (periodicContourCondition) {
            List<Cell> calculated = getCell().calculateNeighbourCells();
            if (!calculated.contains(particle.getCell())) {
                //debo dar la vuelta

                //defino las direcciones en cada una de las componentes
                double newX, newY;
                newX = particle.getX();
                newY = particle.getY();

                if (!hasRangeX(calculated, particle.getCell().getRangeX())) {
                    if (getX() - particle.getX() > 0) {
                        newX = particle.getX() + getCell().getRangeX().getHighest();
                    } else {
                        newX = particle.getX() - getCell().getRangeX().getHighest();
                    }
                }

                if (!hasRangeY(calculated, particle.getCell().getRangeY())) {
                    if (getY() - particle.getY() > 0) {
                        newY = particle.getY() + getCell().getRangeY().getHighest();
                    } else {
                        newY = particle.getY() - getCell().getRangeY().getHighest();
                    }
                }


                Particle newParticle = new Particle(newX, newY, particle.getRadix(), particle.getSpeed(), particle.getAngle());
                return distanceBorderToBorder(newParticle) <= maxDistance;
            }
        }
        return isCloseEnough(particle, maxDistance);
    }

    private boolean hasRangeX(List<Cell> calculated, Range rangex) {
        for (Cell cell : calculated) {
            if (cell != null && cell.getRangeX().equals(rangex)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasRangeY(List<Cell> calculated, Range rangey) {
        for (Cell cell : calculated) {
            try {
                if (cell != null && cell.getRangeY().equals(rangey)) {
                    return true;
                }
            } catch (Exception e) {
                System.out.print(1);
            }

        }
        return false;
    }

    public List<Particle> getOtherParticlesInCell() {
        return getCell().getParticles().stream().filter(p -> !p.equals(this)).collect(Collectors.toList());
    }

    public String printParticle() {
        return String.format("(%f,%f)", x, y);
    }

    @Override
    public String toString() {
        return String.format("(%f,%f) r= %f", x, y, radix);
    }

    public void addNeighbour(Particle particle) {
        if (particle == null) {
            throw new RuntimeException("La particula no puede ser nula");
        }
        neighbours.add(particle);
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }
}