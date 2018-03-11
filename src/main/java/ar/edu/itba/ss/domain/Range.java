package ar.edu.itba.ss.domain;

import java.util.Objects;

/**
 * Es un intetvalo cerra-abierto
 * [,)
 */
public class Range {
    private Double lowest;
    private Double highest;

    public Range(Double lowest, Double highest) {
        this.lowest = lowest;
        this.highest = highest;
    }

    public Double getLowest() {
        return lowest;
    }

    public Double getHighest() {
        return highest;
    }

    public boolean isInRange(Double value){
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