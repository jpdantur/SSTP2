package ar.edu.itba.ss.domain;

import java.util.Objects;

/**
 * Es un intetvalo cerra-abierto
 * [,)
 */
public class Range {
    private double lowest;
    private double highest;

    public Range(double lowest, double highest) {
        this.lowest = lowest;
        this.highest = highest;
    }

    public double getLowest() {
        return lowest;
    }

    public double getHighest() {
        return highest;
    }

    public boolean isInRange(double value){
        return lowest <= value && value < highest;
    }

    @Override
    public String toString() {
        return String.format("[%f,%f)",lowest, highest);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Range)) return false;
        Range range = (Range) o;
        return Objects.equals(lowest, range.lowest) &&
                Objects.equals(highest, range.highest);
    }

    @Override
    public int hashCode() {

        return Objects.hash(lowest, highest);
    }
}